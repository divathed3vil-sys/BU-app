package com.bu.app.platform

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import com.bu.app.R
import com.bu.core.ledger.ContractDao
import com.bu.core.ledger.EventDao
import com.bu.core.ledger.ObjectDao
import com.bu.core.ledger.TaskDao
import com.bu.core.platform.WidgetRefresher
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * The NOW widget (locked plan §3): today at a glance on the home screen.
 * It is a projection — rebuilt from the ledger on every refresh; it never
 * stores state of its own (I2). The kernel marks WIDGET_DIRTY on commits and
 * the outbox repaints it at-least-once.
 */
@AndroidEntryPoint
class BuWidgetProvider : AppWidgetProvider() {

    @Inject lateinit var renderer: BuWidgetRenderer

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val result = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val views = renderer.build(context)
                appWidgetManager.updateAppWidget(appWidgetIds, views)
            } catch (t: Throwable) {
                android.util.Log.w(TAG, "widget render failed: ${t.message}")
            } finally {
                result.finish()
            }
        }
    }

    private companion object {
        const val TAG = "BuWidget"
    }
}

/** Reads today's field from the ledger and paints the widget surface. */
@Singleton
class BuWidgetRenderer @Inject constructor(
    private val taskDao: TaskDao,
    private val contractDao: ContractDao,
    private val objectDao: ObjectDao,
    private val eventDao: EventDao,
) {

    suspend fun build(context: Context): RemoteViews {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        val todayIso = today.toString()

        val bigs = taskDao.observeDayTasks("BIG", todayIso).first()
        val mainFrontId = contractDao.byDate(todayIso)?.mainFrontTaskId
        val mainTitle = mainFrontId?.let { objectDao.findById(it)?.title }

        val dayStartMs = today.atStartOfDay(zone).toInstant().toEpochMilli()
        val captures = eventDao.captureCountSince(dayStartMs).first()

        val views = RemoteViews(context.packageName, R.layout.bu_widget_now)
        views.setTextViewText(
            R.id.w_date,
            today.format(DateTimeFormatter.ofPattern("EEE · d MMM")),
        )

        val head = mainTitle
            ?: bigs.firstOrNull()?.title
            ?: context.getString(R.string.widget_empty_main)
        views.setTextViewText(R.id.w_main, glyphFor(bigs.firstOrNull()?.status, head == mainTitle) + head)

        val rest = bigs.filter { it.title != head }.take(2)
        views.setTextViewText(R.id.w_b2, rest.getOrNull(0)?.let { glyphFor(it.status, false) + it.title } ?: "")
        views.setTextViewText(R.id.w_b3, rest.getOrNull(1)?.let { glyphFor(it.status, false) + it.title } ?: "")
        views.setTextViewText(R.id.w_footer, "captures today · $captures")
        return views
    }

    /** Zero-guilt glyphs: no red, no crosses, no shame — state symbols only. */
    private fun glyphFor(status: String?, isMainFront: Boolean): String = when (status) {
        "DONE" -> "✓ "
        "PARTIAL" -> "◐ "
        else -> if (isMainFront) "▲ " else "○ "
    }
}

/** Repaints every hosted widget — called by the outbox WIDGET_DIRTY handler. */
@Singleton
class AndroidWidgetRefresher @Inject constructor(
    @ApplicationContext private val context: Context,
    private val renderer: BuWidgetRenderer,
) : WidgetRefresher {

    override suspend fun refresh() {
        val manager = context.getSystemService(AppWidgetManager::class.java) ?: return
        val ids = manager.getAppWidgetIds(ComponentName(context, BuWidgetProvider::class.java))
        if (ids.isEmpty()) return
        runCatching {
            manager.updateAppWidget(ids, renderer.build(context))
        }.onFailure {
            android.util.Log.w(TAG, "widget refresh failed: ${it.message}")
        }
    }

    private companion object {
        const val TAG = "BuWidget"
    }
}

/** Outbox effect: the kernel says a commit changed today's field. */
@Singleton
class WidgetDirtyHandler @Inject constructor(
    private val refresher: WidgetRefresher,
) : com.bu.core.outbox.OutboxEffectHandler {

    override val effectType: String = "WIDGET_DIRTY"

    override suspend fun apply(effect: com.bu.core.command.OutboxEffect) {
        refresher.refresh()
    }
}

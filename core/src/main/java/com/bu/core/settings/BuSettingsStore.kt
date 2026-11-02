package com.bu.core.settings

import android.content.Context
import com.bu.core.model.BuSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * P0 settings store — tiny JSON file in app storage, write-through on every change.
 * P1 replaces the backing implementation with the Room `user_settings` table;
 * the API (StateFlow + save) is designed to survive that swap untouched.
 */
@Singleton
class BuSettingsStore @Inject constructor(
    @param:ApplicationContext private val context: Context,
    @BuSettingsScope private val scope: CoroutineScope,
) {
    private val file: File get() = File(context.filesDir, "bu_settings.json")
    private val _settings = MutableStateFlow(load())
    val settings: StateFlow<BuSettings> = _settings.asStateFlow()

    fun save(update: (BuSettings) -> BuSettings) {
        val next = update(_settings.value)
        _settings.value = next
        scope.launch(Dispatchers.IO) {
            runCatching {
                val json = JSONObject().apply {
                    put("username", next.username)
                    put("dayStartMin", next.dayStartMin)
                    put("dayEndMin", next.dayEndMin)
                    put("dawnMin", next.dawnMin)
                    put("duskMin", next.duskMin)
                    put("sundayReviewMin", next.sundayReviewMin)
                    put("examName", next.examName)
                    put("examDateEpochDay", next.examDateEpochDay ?: JSONObject.NULL)
                    put("motionLevel", next.motionLevel)
                    put("setupDone", next.setupDone)
                }
                file.writeText(json.toString())
            }
        }
    }

    private fun load(): BuSettings = runCatching {
        if (!file.exists()) return BuSettings()
        val j = JSONObject(file.readText())
        BuSettings(
            username = j.optString("username", ""),
            dayStartMin = j.optInt("dayStartMin", 300),
            dayEndMin = j.optInt("dayEndMin", 1380),
            dawnMin = j.optInt("dawnMin", 360),
            duskMin = j.optInt("duskMin", 1290),
            sundayReviewMin = j.optInt("sundayReviewMin", 600),
            examName = j.optString("examName", ""),
            examDateEpochDay = if (j.isNull("examDateEpochDay")) null else j.getLong("examDateEpochDay"),
            motionLevel = j.optInt("motionLevel", 2),
            setupDone = j.optBoolean("setupDone", false),
        )
    }.getOrDefault(BuSettings())
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuSettingsScope

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    @BuSettingsScope
    fun provideAppScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}

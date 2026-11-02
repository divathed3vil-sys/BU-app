package com.bu.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bu.designsystem.theme.BuLayer
import com.bu.designsystem.theme.BuOutline
import com.bu.designsystem.theme.BuTextDim

/** BU card surface — subtle layer, quiet border, generous padding. */
@Composable
fun BuCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(20.dp)
    if (onClick != null) {
        Surface(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            color = BuLayer,
            border = BorderStroke(1.dp, BuOutline),
            content = { Column(Modifier.padding(16.dp), content = content) },
        )
    } else {
        Surface(
            modifier = modifier,
            shape = shape,
            color = BuLayer,
            border = BorderStroke(1.dp, BuOutline),
            content = { Column(Modifier.padding(16.dp), content = content) },
        )
    }
}

/** Instructive empty state — empty never scolds, it teaches (design law). */
@Composable
fun BuEmptyState(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    BuCard(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(
            body,
            style = MaterialTheme.typography.bodyMedium,
            color = BuTextDim,
        )
    }
}

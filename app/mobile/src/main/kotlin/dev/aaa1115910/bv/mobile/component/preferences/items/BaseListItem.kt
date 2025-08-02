package dev.aaa1115910.bv.mobile.component.preferences.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BaseListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors().copy(
        containerColor = MaterialTheme.colorScheme.surfaceBright,
        supportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
    ),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    selected: Boolean = false,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier
            .clip(shape)
            .heightIn(min = 72.dp)
            .clickable(
                enabled = enabled,
                onClick = { onClick?.invoke() }
            ),
        headlineContent = headlineContent,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = if (!enabled) colors.copy(
            //containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.38f),
            headlineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            leadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            overlineColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            supportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
        ) else if (selected) colors.copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ) else colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation
    )
}


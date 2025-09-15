package dev.aaa1115910.bv.mobile.component.preferences.items

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.mobile.component.preferences.PreferenceGroupScope
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme

@Composable
private fun ListItemPreference(
    modifier: Modifier = Modifier,
    headlineContent: @Composable () -> Unit,
    overlineContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    shape: Shape = RoundedCornerShape(0.dp),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        modifier = modifier
            .clip(shape)
            .clickable { onClick?.invoke() },
        headlineContent = headlineContent,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation
    )
}

fun PreferenceGroupScope.listItemPreference(
    headlineContent: @Composable () -> Unit,
    overlineContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    colors: ListItemColors? = null,
    onClick: (() -> Unit)? = null,
) {
    preferences += { shape, modifier ->
        ListItemPreference(
            modifier = modifier,
            headlineContent = headlineContent,
            overlineContent = overlineContent,
            supportingContent = supportingContent,
            leadingContent = leadingContent,
            trailingContent = trailingContent,
            colors = colors ?: ListItemDefaults.colors(),
            shape = shape,
            onClick = onClick
        )
    }
}

fun PreferenceGroupScope.listItemPreference(
    title: String,
    summary: String? = null,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
) = listItemPreference(
    headlineContent = @Composable { Text(text = title) },
    supportingContent = if (summary != null) (@Composable { Text(text = summary) }) else null,
    leadingContent = if (icon != null) (@Composable {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }) else null,
    onClick = onClick,
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListItemPreferencePreview() {
    BVMobileTheme {
        ListItemPreference(
            headlineContent = { Text("Text Preference") },
            //supportingContent = { Text("This is a summary") },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.PlayCircleOutline,
                    contentDescription = null
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            },
            onClick = { /* Handle click */ },
            shape = RoundedCornerShape(8.dp),
        )
    }
}

package dev.aaa1115910.bv.mobile.component.preferences.items

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.mobile.component.preferences.PreferenceGroupScope
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme

@Composable
private fun TextPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String? = null,
    onClick: (() -> Unit)? = null,
    selected: Boolean = false,
    shape: Shape = RoundedCornerShape(0.dp),
    enabled: Boolean = true,
    leadingContent: @Composable() (() -> Unit)? = null,
    trailingContent: @Composable() (() -> Unit)? = null,
) {
    BaseListItem(
        modifier = modifier,
        headlineContent = { Text(text = title) },
        supportingContent = summary?.let { { Text(text = it) } },
        selected = selected,
        enabled = enabled,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClick = onClick,
        shape = shape
    )
}

fun PreferenceGroupScope.textPreference(
    title: String,
    summary: String? = null,
    leadingContent: @Composable() (() -> Unit)? = null,
    trailingContent: @Composable() (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onSelected: Boolean = false,
    enabled: Boolean = true
) {
    preferences += { shape, modifier ->
        TextPreference(
            modifier = modifier,
            title = title,
            summary = summary,
            leadingContent = leadingContent,
            trailingContent = trailingContent,
            onClick = onClick,
            selected = onSelected,
            shape = shape,
            enabled = enabled
        )
    }
}

fun PreferenceGroupScope.textPreference(
    title: String,
    summary: String? = null,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    selected: Boolean = false,
    enabled: Boolean = true
) = textPreference(
    title = title,
    summary = summary,
    leadingContent = if (icon != null) (@Composable {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }) else null,
    onClick = onClick,
    onSelected = selected,
    enabled = enabled
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TextPreferencePreview() {
    BVMobileTheme {
        TextPreference(
            title = "Text Preference",
            summary = "This is a summary",
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
            selected = false,
            shape = RoundedCornerShape(8.dp),
            enabled = true
        )
    }
}

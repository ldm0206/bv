package dev.aaa1115910.bv.mobile.component.preferences.items

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.schnettler.datastore.manager.DataStoreManager
import de.schnettler.datastore.manager.PreferenceRequest
import dev.aaa1115910.bv.dataStore
import dev.aaa1115910.bv.mobile.component.preferences.PreferenceGroupScope
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
private fun SwitchPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String? = null,
    onClick: (() -> Unit)? = null,
    selected: Boolean = false,
    shape: Shape = RoundedCornerShape(0.dp),
    enabled: Boolean = true,
    leadingContent: @Composable() (() -> Unit)? = null,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    BaseListItem(
        modifier = modifier,
        headlineContent = { Text(text = title) },
        supportingContent = summary?.let { { Text(text = it) } },
        selected = selected,
        enabled = enabled,
        leadingContent = leadingContent,
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        },
        onClick = onClick,
        shape = shape
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SwitchPreferencePreview() {
    BVMobileTheme {
        SwitchPreference(
            title = "Switch Preference",
            summary = "This is a summary",
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.PlayCircleOutline,
                    contentDescription = null
                )
            },
            onClick = { /* Handle click */ },
            selected = false,
            shape = RoundedCornerShape(8.dp),
            enabled = true,
            checked = false,
            onCheckedChange = { /* Handle checked change */ }
        )
    }
}

fun PreferenceGroupScope.switchPreference(
    title: String,
    summary: String? = null,
    leadingContent: @Composable() (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onSelected: Boolean = false,
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    preferences += { shape, modifier ->
        SwitchPreference(
            modifier = modifier,
            title = title,
            summary = summary,
            leadingContent = leadingContent,
            onClick = onClick,
            selected = onSelected,
            shape = shape,
            enabled = enabled,
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

fun PreferenceGroupScope.switchPreference(
    title: String,
    summary: String? = null,
    leadingContent: @Composable() (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onSelected: Boolean = false,
    enabled: Boolean = true,
    prefReq: PreferenceRequest<Boolean>,
    onCheckedChange: (Boolean) -> Boolean
) {
    preferences += { shape, modifier ->
        val scope = rememberCoroutineScope()
        val dataStoreManager = DataStoreManager(LocalContext.current.dataStore)

        val checked by dataStoreManager.getPreferenceState(prefReq)
        val setChecked = { newValue: Boolean ->
            scope.launch(Dispatchers.IO) {
                dataStoreManager.editPreference(prefReq.key, newValue)
            }
            onCheckedChange(newValue)
        }

        SwitchPreference(
            modifier = modifier,
            title = title,
            summary = summary,
            leadingContent = leadingContent,
            onClick = onClick,
            selected = onSelected,
            shape = shape,
            enabled = enabled,
            checked = checked,
            onCheckedChange = {
                if (onCheckedChange(it)) setChecked(it)
            }
        )
    }
}


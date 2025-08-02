package dev.aaa1115910.bv.mobile.component.preferences

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.mobile.component.preferences.items.switchPreference
import dev.aaa1115910.bv.mobile.component.preferences.items.textPreference
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreferencesPreview() {
    BVMobileTheme {
        var showHiddenPreference by remember { mutableStateOf(false) }
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            LazyColumn(
                modifier = Modifier.padding(12.dp),
            ) {
                item {
                    Text(
                        modifier = Modifier.animateItem(),
                        text = "Preferences Preview",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                preferenceGroups(
                    "Group 1" to {
                        textPreference(
                            title = "Text Preference",
                            summary = "This is a summary",
                        )
                        textPreference(
                            title = "Selected Text Preference",
                            summary = "This is another summary",
                            selected = true
                        )
                        textPreference(
                            title = "No Summary",
                        )
                        textPreference(
                            title = "Clickable Text Preference",
                            summary = "This preference is clickable",
                            onClick = { /* Handle click */ }
                        )
                        textPreference(
                            title = "Disabled Text Preference",
                            summary = "This preference is disabled",
                            enabled = false
                        )
                        textPreference(
                            title = "Icon Text Preference",
                            summary = "This preference has an icon",
                            icon = Icons.Default.PlayCircleOutline,
                        )
                    },
                    "Group 2" to {
                        textPreference(
                            title = "Text Preference",
                            summary = "This is a summary",
                        )
                        switchPreference(
                            title = "Switch Preference",
                            summary = "This is a summary",
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Default.PlayCircleOutline,
                                    contentDescription = null
                                )
                            },
                            onClick = { showHiddenPreference = !showHiddenPreference },
                            checked = showHiddenPreference,
                            onCheckedChange = { showHiddenPreference = !showHiddenPreference }
                        )
                        if (showHiddenPreference) {
                            textPreference(title = "Hidden Preference")
                        }
                    },
                    null to {
                        textPreference(
                            title = "Text Preference",
                            summary = "This is a summary",
                        )
                    }
                )
            }
        }
    }
}
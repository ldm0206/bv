package dev.aaa1115910.bv.mobile.screen.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.mobile.component.preferences.items.textPreference
import dev.aaa1115910.bv.mobile.component.preferences.preferenceGroups
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsCategories(
    modifier: Modifier = Modifier,
    selectedSettings: MobileSettings?,
    onSelectedSettings: (MobileSettings) -> Unit,
    showNavBack: Boolean,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }.takeIf { showNavBack }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 18.dp)
        ) {
            preferenceGroups(
                null to {
                    listOf(
                        MobileSettings.Play,
                        MobileSettings.Advance
                    ).forEach { item ->
                        textPreference(
                            title = item.title,
                            summary = item.summary,
                            onClick = { onSelectedSettings(item) },
                            selected = selectedSettings == item
                        )
                    }
                },
                null to {
                    textPreference(
                        title = MobileSettings.About.title,
                        summary = MobileSettings.About.summary,
                        onClick = { onSelectedSettings(MobileSettings.About) },
                        selected = selectedSettings == MobileSettings.About
                    )
                },
                null to {
                    textPreference(
                        title = MobileSettings.Debug.title,
                        summary = MobileSettings.Debug.summary,
                        onClick = { onSelectedSettings(MobileSettings.Debug) },
                        selected = selectedSettings == MobileSettings.Debug
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun SettingsCategoriesPreview() {
    BVMobileTheme {
        Surface {
            SettingsCategories(
                selectedSettings = MobileSettings.Play,
                onSelectedSettings = {},
                showNavBack = false,
                onBack = {},
            )
        }
    }
}

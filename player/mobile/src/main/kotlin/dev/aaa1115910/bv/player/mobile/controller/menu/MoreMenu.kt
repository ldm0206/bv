package dev.aaa1115910.bv.player.mobile.controller.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.entity.PlayMode
import dev.aaa1115910.bv.player.mobile.MaterialDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreMenu(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onPlayModeChange: (PlayMode) -> Unit
) {
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "更多设置") },
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                PlayModeContent(
                    modifier = Modifier,
                    playMode = videoPlayerConfigData.currentPlayMode,
                    onPlayModeChange = onPlayModeChange
                )
            }
        }
    }
}

@Composable
fun PlayModeContent(
    modifier: Modifier = Modifier,
    playMode: PlayMode,
    onPlayModeChange: (PlayMode) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {

        Text(
            text = "播放模式",
            style = MaterialTheme.typography.titleSmall
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlayMode.entries.forEach {
                FilterChip(
                    modifier = modifier,
                    label = { Text(text = it.getDisplayName(context)) },
                    selected = playMode == it,
                    onClick = { onPlayModeChange(it) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MoreMenuPreview() {
    MaterialDarkTheme {
        MoreMenu(
            onClose = {},
            onPlayModeChange = {}
        )
    }
}

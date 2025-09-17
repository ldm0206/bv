package dev.aaa1115910.bv.player.mobile.controller.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.Audio
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.entity.Resolution
import dev.aaa1115910.bv.player.entity.VideoCodec
import dev.aaa1115910.bv.player.entity.VideoPlayerConfigData
import dev.aaa1115910.bv.player.mobile.MaterialDarkTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashMenu(
    modifier: Modifier = Modifier,
    onChangeResolution: (Resolution) -> Unit,
    onChangeVideoCodec: (VideoCodec) -> Unit,
    onChangeAudio: (Audio) -> Unit,
    onClose: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "音频画质") },
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
                .padding(horizontal = 18.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                ResolutionContent(
                    onClickResolution = onChangeResolution,
                    onClickCodec = onChangeVideoCodec
                )
            }
            item {
                AudioContent(
                    onClickAudio = onChangeAudio
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ResolutionContent(
    modifier: Modifier = Modifier,
    onClickResolution: (Resolution) -> Unit,
    onClickCodec: (VideoCodec) -> Unit
) {
    val context = LocalContext.current
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current

    Column(
        modifier = modifier
    ) {
        Text(text = "视频清晰度")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            videoPlayerConfigData.availableResolutions
                .sortedByDescending { it.code }
                .forEach { resolution ->
                    ChipItem(
                        text = resolution.getDisplayName(context),
                        selected = videoPlayerConfigData.currentResolution == resolution,
                        onClick = {
                            println("click resolution item: $resolution")
                            onClickResolution(resolution)
                        }
                    )
                }
        }

        Text(text = "视频编码")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            videoPlayerConfigData.availableVideoCodec.forEach { codec ->
                ChipItem(
                    text = codec.getDisplayName(context),
                    selected = videoPlayerConfigData.currentVideoCodec == codec,
                    onClick = {
                        println("click codec item: $codec")
                        onClickCodec(codec)
                    }
                )
            }
        }

        /*
        Text(text = "画面线路")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            listOf("线路1", "线路2", "线路3").forEach { name ->
                ChipItem(
                    text = name,
                    selected = false,
                    onClick = { "NOT IMPLEMENTED".toast(context) }
                )
            }
        }*/
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AudioContent(
    modifier: Modifier = Modifier,
    onClickAudio: (Audio) -> Unit
) {
    val context = LocalContext.current
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current

    Column(
        modifier = modifier
    ) {
        Text(text = "音频采样率")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            videoPlayerConfigData.availableAudio
                .sortedByDescending { it.ordinal }
                .forEach { audio ->
                    ChipItem(
                        text = audio.getDisplayName(context),
                        selected = videoPlayerConfigData.currentAudio == audio,
                        onClick = {
                            println("click audio item: $audio")
                            onClickAudio(audio)
                        }
                    )
                }
        }

        /* Text(text = "音频线路")
         FlowRow(
             horizontalArrangement = Arrangement.spacedBy(8.dp),
         ) {
             listOf("线路1", "线路2", "线路3").forEach { name ->
                 ChipItem(
                     text = name,
                     selected = false,
                     onClick = { "NOT IMPLEMENTED".toast(context) }
                 )
             }
         }*/
    }
}

@Composable
private fun ChipItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        label = {
            Text(
                text = text,
                maxLines = 1
            )
        },
        leadingIcon = (@Composable {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        }).takeIf { selected },
        onClick = onClick
    )
}

@Preview
@Composable
private fun ResolutionListItemSelectedPreview() {
    MaterialTheme {
        ChipItem(
            text = "1080P 60FPS",
            selected = true
        )
    }
}

@Preview
@Composable
private fun ResolutionListItemUnselectedPreview() {
    MaterialTheme {
        ChipItem(
            text = "1080P 60FPS",
            selected = false
        )
    }
}

@Preview(device = "spec:width=300dp,height=400dp,dpi=440")
@Composable
private fun ResolutionMenuPreview() {
    MaterialDarkTheme {
        CompositionLocalProvider(
            LocalVideoPlayerConfigData provides VideoPlayerConfigData(
                currentResolution = Resolution.R720P,
                currentAudio = Audio.A132K,
                availableResolutions = Resolution.entries,
                availableAudio = Audio.entries,
                availableVideoCodec = VideoCodec.entries
            )
        ) {
            DashMenu(
                onChangeResolution = {},
                onChangeVideoCodec = {},
                onChangeAudio = {},
                onClose = {}
            )
        }
    }
}

@Preview
@Composable
private fun ResolutionContentPreview() {
    var currentResolution by remember { mutableStateOf(Resolution.R1080P) }
    var currentVideoCodec by remember { mutableStateOf(VideoCodec.HEVC) }

    MaterialDarkTheme {
        CompositionLocalProvider(
            LocalVideoPlayerConfigData provides VideoPlayerConfigData(
                currentResolution = currentResolution,
                availableResolutions = Resolution.entries,
                availableVideoCodec = VideoCodec.entries
            )
        ) {
            Surface {
                ResolutionContent(
                    onClickResolution = { currentResolution = it },
                    onClickCodec = { currentVideoCodec = it },
                )
            }
        }
    }
}
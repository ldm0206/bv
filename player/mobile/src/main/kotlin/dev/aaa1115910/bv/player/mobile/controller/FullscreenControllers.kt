package dev.aaa1115910.bv.player.mobile.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ClosedCaption
import androidx.compose.material.icons.rounded.FullscreenExit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerSeekData
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerStateData
import dev.aaa1115910.bv.player.entity.Resolution
import dev.aaa1115910.bv.player.entity.VideoPlayerConfigData
import dev.aaa1115910.bv.player.entity.VideoPlayerSeekData
import dev.aaa1115910.bv.player.entity.VideoPlayerStateData
import dev.aaa1115910.bv.player.mobile.VideoSeekBar
import dev.aaa1115910.bv.player.mobile.noRippleClickable
import dev.aaa1115910.bv.util.formatHourMinSec
import dev.aaa1115910.symbols.Subtitles
import dev.aaa1115910.symbols.SubtitlesGear
import dev.aaa1115910.symbols.SubtitlesOff
import me.ks.chan.material.symbols.MaterialSymbols

@Composable
fun FullscreenControllers(
    modifier: Modifier = Modifier,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onExitFullScreen: () -> Unit,
    onSeekToPosition: (Long) -> Unit,
    onShowResolutionController: () -> Unit,
    onShowSpeedController: () -> Unit,
    onToggleDanmaku: (Boolean) -> Unit,
    onShowDanmakuController: () -> Unit,
    onShowVideoListController: () -> Unit,
    onOpenMoreMenu: () -> Unit
) {
    val context = LocalContext.current
    val videoPlayerSeekData = LocalVideoPlayerSeekData.current
    val videoPlayerStateData = LocalVideoPlayerStateData.current
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopControllers(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .noRippleClickable { },
            onOpenMoreMenu = onOpenMoreMenu,
            onExitFullScreen = onExitFullScreen
        )
        BottomControllers(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .noRippleClickable { },
            isPlaying = videoPlayerStateData.isPlaying,
            currentTime = videoPlayerSeekData.position,
            totalTime = videoPlayerSeekData.duration,
            bufferedSeekPosition = videoPlayerSeekData.bufferedPercentage,
            currentResolutionName = videoPlayerConfigData.currentResolution.getDisplayName(context),
            enabledDanmaku = videoPlayerConfigData.currentDanmakuEnabled,
            showPartButton = videoPlayerConfigData.availableVideoList.size > 1,
            onPlay = onPlay,
            onPause = onPause,
            onExitFullScreen = onExitFullScreen,
            onSeekToPosition = onSeekToPosition,
            onShowResolutionController = onShowResolutionController,
            onShowSpeedController = onShowSpeedController,
            onToggleDanmaku = onToggleDanmaku,
            onShowDanmakuController = onShowDanmakuController,
            onShowVideoListController = onShowVideoListController
        )
    }
}

@Composable
private fun TopControllers(
    modifier: Modifier = Modifier,
    onOpenMoreMenu: () -> Unit,
    onExitFullScreen: () -> Unit,
) {
    Box(
        modifier = modifier
        //.background(Color.Black.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ControllerButtonGroup {
                IconButton(
                    onClick = onExitFullScreen,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                }
                Text(
                    modifier = Modifier.padding(end = 12.dp),
                    text = "这是一个标题",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
            ControllerButtonGroup {
                IconButton(
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Rounded.ClosedCaption, contentDescription = null)
                }
                IconButton(
                    onClick = onOpenMoreMenu,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun BottomControllers(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    currentTime: Long,
    totalTime: Long,
    bufferedSeekPosition: Int,
    currentResolutionName: String,
    enabledDanmaku: Boolean,
    showPartButton: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onExitFullScreen: () -> Unit,
    onSeekToPosition: (Long) -> Unit,
    onShowResolutionController: () -> Unit,
    onShowSpeedController: () -> Unit,
    onToggleDanmaku: (Boolean) -> Unit,
    onShowDanmakuController: () -> Unit,
    onShowVideoListController: () -> Unit
) {
    Box(
        modifier = modifier
        //.background(Color.Black.copy(alpha = 0.6f))
    ) {
        Column {
            VideoSeekBar(
                modifier = Modifier.padding(bottom = 8.dp),
                duration = totalTime,
                position = currentTime,
                bufferedPercentage = bufferedSeekPosition,
                playing = isPlaying,
                onPositionChange = { newPosition, isPressing ->
                    if (!isPressing) onSeekToPosition(newPosition)
                }
            )

            ProvideTextStyle(TextStyle(color = Color.White)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        Modifier.height(48.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .width(80.dp)
                                .height(48.dp),
                            onClick = if (isPlaying) onPause else onPlay,
                            shape = if (isPlaying) MaterialTheme.shapes.medium else MaterialTheme.shapes.extraLarge,
                            colors = if (isPlaying) {
                                iconButtonColors(
                                    containerColor = Color.Black.copy(0.6f),
                                    contentColor = Color.White
                                )
                            } else {
                                iconButtonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black
                                )
                            }
                        ) {
                            if (isPlaying) {
                                Icon(
                                    imageVector = Icons.Rounded.Pause,
                                    contentDescription = null,
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = null,
                                )
                            }
                        }
                        ControllerButtonGroup {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp),
                                text = "${currentTime.formatHourMinSec()}/${totalTime.formatHourMinSec()}",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                        ControllerButtonGroup {
                            IconButton(
                                onClick = { onToggleDanmaku(!enabledDanmaku) },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                if (enabledDanmaku) {
                                    Icon(
                                        imageVector = MaterialSymbols.Subtitles.Rounded,
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        imageVector = MaterialSymbols.SubtitlesOff.Rounded,
                                        contentDescription = null
                                    )
                                }
                            }
                            IconButton(
                                onClick = onShowDanmakuController,
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = MaterialSymbols.SubtitlesGear.Rounded,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    Row(
                        Modifier.height(48.dp)
                    ) {
                        ControllerButtonGroup {
                            if (showPartButton) {
                                TextButton(onClick = onShowVideoListController) {
                                    Text(text = "选集")
                                }
                            }
                            TextButton(onClick = onShowSpeedController) {
                                Text(text = "倍速")
                            }
                            TextButton(onClick = onShowResolutionController) {
                                Text(text = currentResolutionName)
                            }
                            IconButton(onClick = onExitFullScreen) {
                                Icon(
                                    imageVector = Icons.Rounded.FullscreenExit,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ControllerButtonGroup(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Color.Black.copy(0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Preview(device = "spec:width=1920px,height=1080px")
@Composable
fun FullscreenControllerLightBackgroundPreview() {
    CompositionLocalProvider(
        LocalVideoPlayerSeekData provides VideoPlayerSeekData(
            duration = 123456,
            position = 12345,
            bufferedPercentage = 60
        ),
        LocalVideoPlayerStateData provides VideoPlayerStateData(
            isPlaying = true,
        ),
        LocalVideoPlayerConfigData provides VideoPlayerConfigData(
            currentResolution = Resolution.R1080P,
            currentDanmakuEnabled = false
        )
    ) {
        FullscreenControllers(
            modifier = Modifier.background(lightColorScheme().surfaceContainer),
            onPlay = {},
            onPause = {},
            onExitFullScreen = {},
            onSeekToPosition = {},
            onShowResolutionController = {},
            onShowSpeedController = {},
            onToggleDanmaku = {},
            onShowDanmakuController = {},
            onShowVideoListController = {},
            onOpenMoreMenu = {}
        )
    }
}

@Preview(device = "spec:width=1920px,height=1080px", backgroundColor = 0xFFFFFFFF)
@Composable
fun FullscreenControllerDarkBackgroundPreview() {
    CompositionLocalProvider(
        LocalVideoPlayerSeekData provides VideoPlayerSeekData(
            duration = 123456,
            position = 12345,
            bufferedPercentage = 60
        ),
        LocalVideoPlayerStateData provides VideoPlayerStateData(
            isPlaying = true,
        ),
        LocalVideoPlayerConfigData provides VideoPlayerConfigData(
            currentResolution = Resolution.R1080P,
            currentDanmakuEnabled = false
        )
    ) {
        FullscreenControllers(
            modifier = Modifier.background(darkColorScheme().surfaceContainerHighest),
            onPlay = {},
            onPause = {},
            onExitFullScreen = {},
            onSeekToPosition = {},
            onShowResolutionController = {},
            onShowSpeedController = {},
            onToggleDanmaku = {},
            onShowDanmakuController = {},
            onShowVideoListController = {},
            onOpenMoreMenu = {}
        )
    }
}

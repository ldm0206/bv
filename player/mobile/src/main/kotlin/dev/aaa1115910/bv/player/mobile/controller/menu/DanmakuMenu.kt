package dev.aaa1115910.bv.player.mobile.controller.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.DanmakuType
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.mobile.MaterialDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DanmakuMenu(
    modifier: Modifier = Modifier,
    onEnabledDanmakuTypeChange: (List<DanmakuType>) -> Unit,
    onDanmakuScaleChange: (Float) -> Unit,
    onDanmakuOpacityChange: (Float) -> Unit,
    onDanmakuAreaChange: (Float) -> Unit,
    onClose: () -> Unit
) {
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "弹幕设置") },
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                EnabledDanmakuType(
                    enabledDanmakuTypes = videoPlayerConfigData.currentDanmakuEnabledList,
                    onEnabledDanmakuTypeChange = onEnabledDanmakuTypeChange
                )
            }
            item {
                DanmakuOpacity(
                    danmakuOpacity = videoPlayerConfigData.currentDanmakuOpacity,
                    onDanmakuOpacityChange = onDanmakuOpacityChange
                )
            }
            item {
                DanmakuArea(
                    danmakuArea = videoPlayerConfigData.currentDanmakuArea,
                    onDanmakuAreaChange = onDanmakuAreaChange
                )
            }
            item {
                DanmakuScale(
                    danmakuScale = videoPlayerConfigData.currentDanmakuScale,
                    onDanmakuScaleChange = onDanmakuScaleChange
                )
            }
        }
    }
}

@Composable
private fun EnabledDanmakuType(
    modifier: Modifier = Modifier,
    enabledDanmakuTypes: List<DanmakuType>,
    onEnabledDanmakuTypeChange: (List<DanmakuType>) -> Unit
) {
    val onClickEnabledDanmakuTypeButton: (DanmakuType, Boolean) -> Unit = { danmakuType, blocked ->
        val newEnabledDanmakuTypes = enabledDanmakuTypes.toMutableList()
        newEnabledDanmakuTypes.remove(DanmakuType.All)
        if (!blocked) {
            newEnabledDanmakuTypes.add(danmakuType)
        } else {
            newEnabledDanmakuTypes.remove(danmakuType)
        }
        if (newEnabledDanmakuTypes.size == 3) {
            newEnabledDanmakuTypes.add(DanmakuType.All)
        }
        onEnabledDanmakuTypeChange(newEnabledDanmakuTypes)
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = "屏蔽类型",
            style = MaterialTheme.typography.titleSmall
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            EnabledDanmakuTypeButton(
                danmakuType = DanmakuType.Top,
                selected = !enabledDanmakuTypes.contains(DanmakuType.Top),
                onEnabledStateChange = { onClickEnabledDanmakuTypeButton(DanmakuType.Top, it) }
            )
            EnabledDanmakuTypeButton(
                danmakuType = DanmakuType.Bottom,
                selected = !enabledDanmakuTypes.contains(DanmakuType.Bottom),
                onEnabledStateChange = { onClickEnabledDanmakuTypeButton(DanmakuType.Bottom, it) }
            )
            EnabledDanmakuTypeButton(
                danmakuType = DanmakuType.Rolling,
                selected = !enabledDanmakuTypes.contains(DanmakuType.Rolling),
                onEnabledStateChange = { onClickEnabledDanmakuTypeButton(DanmakuType.Rolling, it) }
            )
        }
    }
}

@Composable
private fun EnabledDanmakuTypeButton(
    modifier: Modifier = Modifier,
    danmakuType: DanmakuType,
    selected: Boolean,
    onEnabledStateChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    FilterChip(
        modifier = modifier,
        label = { Text(text = danmakuType.getDisplayName(context).replace("弹幕","")) },
        selected = selected,
        onClick = { onEnabledStateChange(!selected) }
    )
}

@Composable
private fun DanmakuOpacity(
    modifier: Modifier = Modifier,
    danmakuOpacity: Float,
    onDanmakuOpacityChange: (Float) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "不透明度",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "${(danmakuOpacity * 100).toInt()}%",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Slider(value = danmakuOpacity, onValueChange = onDanmakuOpacityChange)
    }
}

@Composable
private fun DanmakuArea(
    modifier: Modifier = Modifier,
    danmakuArea: Float,
    onDanmakuAreaChange: (Float) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "显示区域",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "${(danmakuArea * 100).toInt()}%",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Slider(value = danmakuArea, onValueChange = onDanmakuAreaChange)
    }
}

@Composable
private fun DanmakuScale(
    modifier: Modifier = Modifier,
    danmakuScale: Float,
    onDanmakuScaleChange: (Float) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "字体缩放",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "${(danmakuScale * 100).toInt()}%",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Slider(
            value = danmakuScale,
            onValueChange = onDanmakuScaleChange,
            valueRange = 0.5f..2f,
        )
    }
}

@Preview(device = "spec:width=300dp,height=400dp,dpi=440")
@Composable
private fun ResolutionMenuPreview() {
    MaterialDarkTheme {
        DanmakuMenu(
            onEnabledDanmakuTypeChange = {},
            onDanmakuScaleChange = {},
            onDanmakuOpacityChange = {},
            onDanmakuAreaChange = {},
            onClose = {}
        )
    }
}

@Preview
@Composable
private fun EnabledDanmakuTypePreview() {
    MaterialDarkTheme {
        Surface {
            EnabledDanmakuType(
                enabledDanmakuTypes = listOf(DanmakuType.Bottom),
                onEnabledDanmakuTypeChange = {}
            )
        }
    }
}

@Preview
@Composable
private fun DanmakuOpacityPreview() {
    MaterialDarkTheme {
        Surface {
            DanmakuOpacity(
                danmakuOpacity = 0.6f,
                onDanmakuOpacityChange = {}
            )
        }
    }
}

@Preview
@Composable
private fun DanmakuAreaPreview() {
    MaterialDarkTheme {
        Surface {
            DanmakuArea(
                danmakuArea = 0.6f,
                onDanmakuAreaChange = {}
            )
        }
    }
}

@Preview
@Composable
private fun DanmakuScalePreview() {
    MaterialDarkTheme {
        Surface {
            DanmakuScale(
                danmakuScale = 1f,
                onDanmakuScaleChange = {}
            )
        }
    }
}
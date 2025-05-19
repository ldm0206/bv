package dev.aaa1115910.bv.player.mobile.controller.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.mobile.MaterialDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedMenu(
    modifier: Modifier = Modifier,
    onClickSpeed: (Float) -> Unit,
    onClose: () -> Unit
) {
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current
    val currentSpeed = videoPlayerConfigData.currentVideoSpeed

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "播放速度") },
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
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = availableSpeedList
                    .toList()
                    .sortedByDescending { it.first }
            ) { (speedNum, speedName) ->
                SpeedListItem(
                    text = speedName,
                    selected = currentSpeed == speedNum,
                    onClick = {
                        println("click speed menu: $speedName($speedNum)")
                        onClickSpeed(speedNum)
                    }
                )
            }
        }
    }
}

@Composable
private fun SpeedListItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    val textColor = if (selected) MaterialTheme.colorScheme.primary else Color.White

    Surface(
        modifier = modifier
            .size(120.dp, 48.dp),
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 32.dp),
                text = text,
                color = textColor
            )
        }
    }
}

private val availableSpeedList = mapOf(
    2.0f to "2x",
    1.75f to "1.75x",
    1.5f to "1.5x",
    1.25f to "1.25x",
    1.0f to "1.0x",
    0.75f to "0.75x",
    0.5f to "0.5x"
)

@Preview
@Composable
private fun SpeedListItemSelectedPreview() {
    MaterialTheme {
        SpeedListItem(
            text = "1.0x",
            selected = true
        )
    }
}

@Preview
@Composable
private fun SpeedListItemUnselectedPreview() {
    MaterialTheme {
        SpeedListItem(
            text = "1.0x",
            selected = false
        )
    }
}

@Preview(device = "spec:width=300dp,height=400dp,dpi=440")
@Composable
private fun ResolutionMenuPreview() {
    MaterialDarkTheme {
        SpeedMenu(
            onClickSpeed = {},
            onClose = {}
        )
    }
}

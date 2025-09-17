package dev.aaa1115910.bv.player.mobile.controller.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.entity.VideoListItem
import dev.aaa1115910.bv.player.entity.VideoListPart
import dev.aaa1115910.bv.player.entity.VideoListPgcEpisode
import dev.aaa1115910.bv.player.entity.VideoListUgcEpisode
import dev.aaa1115910.bv.player.entity.VideoListUgcEpisodeTitle
import dev.aaa1115910.bv.player.entity.VideoPlayerConfigData
import dev.aaa1115910.bv.player.mobile.MaterialDarkTheme
import dev.aaa1115910.bv.util.ifElse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListMenu(
    modifier: Modifier = Modifier,
    onClickVideoListItem: (VideoListItem) -> Unit,
    onClose: () -> Unit
) {
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current
    val list = videoPlayerConfigData.availableVideoList
    val selectedVideoListItem by remember(videoPlayerConfigData.currentVideoCid) {
        derivedStateOf {
            list.first {
                when (it) {
                    is VideoListPart -> it.cid == videoPlayerConfigData.currentVideoCid
                    is VideoListUgcEpisode -> it.cid == videoPlayerConfigData.currentVideoCid
                    is VideoListPgcEpisode -> it.cid == videoPlayerConfigData.currentVideoCid
                    else -> false
                }
            }
        }
    }
    val isUgcSeason by remember {
        derivedStateOf {
            videoPlayerConfigData.availableVideoList.any { it is VideoListUgcEpisode }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "播放列表") },
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
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = list) { item ->
                VideoListItem(
                    item = item,
                    selected = item == selectedVideoListItem,
                    inUgcEpisode = isUgcSeason,
                    onClick = onClickVideoListItem
                )
            }
        }
    }
}

@Composable
private fun VideoListItem(
    modifier: Modifier = Modifier,
    item: VideoListItem,
    selected: Boolean,
    inUgcEpisode: Boolean,
    onClick: (VideoListItem) -> Unit
) {
    val textPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .ifElse({ item !is VideoListUgcEpisodeTitle }, Modifier.clickable { onClick(item) }),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        contentColor = if (selected) contentColorFor(MaterialTheme.colorScheme.primaryContainer)
        else Color.White.copy(alpha = 0.9f)
    ) {
        when (item) {
            is VideoListPart -> {
                Text(
                    text = (" - ".takeIf { inUgcEpisode }
                        ?: "") + "P${item.index + 1} ${item.title}",
                    modifier = modifier
                        .padding(textPadding),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            is VideoListUgcEpisode -> {
                Text(
                    text = "EP${item.index + 1} ${item.title}",
                    modifier = modifier
                        .padding(textPadding),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            is VideoListPgcEpisode -> {
                Text(
                    text = item.title,
                    modifier = modifier
                        .padding(textPadding),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            is VideoListUgcEpisodeTitle -> {
                Text(
                    text = "EP${item.index + 1} ${item.title}",
                    modifier = modifier
                        .padding(textPadding),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview
@Composable
private fun VideoListItemPreview() {
    MaterialDarkTheme {
        VideoListItem(
            item = VideoListPart(
                aid = 0,
                cid = 0,
                title = "This is title",
                index = 2
            ),
            selected = false,
            inUgcEpisode = false,
            onClick = {}
        )
    }
}

@Preview(device = "spec:width=300dp,height=400dp,dpi=440")
@Composable
private fun VideoListMenuContentNormalPartPreview() {
    CompositionLocalProvider(
        LocalVideoPlayerConfigData provides VideoPlayerConfigData(
            availableVideoList = List(20) {
                VideoListPart(
                    aid = it.toLong(),
                    cid = it.toLong(),
                    title = "This is title $it",
                    index = it
                )
            },
            currentVideoCid = 3
        )
    ) {
        MaterialDarkTheme {
            VideoListMenu(
                onClickVideoListItem = {},
                onClose = {}
            )
        }
    }
}

@Preview(device = "spec:width=300dp,height=400dp,dpi=440")
@Composable
private fun VideoListMenuContentPgcSeasonPreview() {
    CompositionLocalProvider(
        LocalVideoPlayerConfigData provides VideoPlayerConfigData(
            availableVideoList = List(20) {
                VideoListPgcEpisode(
                    aid = it.toLong(),
                    cid = it.toLong(),
                    title = "This is title $it",
                    index = it
                )
            },
            currentVideoCid = 3
        )
    ) {
        MaterialDarkTheme {
            VideoListMenu(
                onClickVideoListItem = {},
                onClose = {}
            )
        }
    }
}

@Preview(device = "spec:width=300dp,height=400dp,dpi=440")
@Composable
private fun VideoListMenuContentUgcSeasonPreview() {
    CompositionLocalProvider(
        LocalVideoPlayerConfigData provides VideoPlayerConfigData(
            availableVideoList = listOf(
                *(0..1).map {
                    VideoListUgcEpisode(
                        aid = it.toLong(),
                        cid = it.toLong(),
                        title = "This is title for ep ${it + 1}",
                        index = it
                    )
                }.toTypedArray(),
                VideoListUgcEpisodeTitle(
                    title = "This is title for ep 3",
                    index = 2
                ),
                *(0..4).map {
                    VideoListPart(
                        aid = it.toLong(),
                        cid = it.toLong(),
                        title = "part $it in ep3",
                        index = it
                    )
                }.toTypedArray(),
                *(3..5).map {
                    VideoListUgcEpisode(
                        aid = it.toLong(),
                        cid = it.toLong(),
                        title = "This is title for ep ${it + 1}",
                        index = it
                    )
                }.toTypedArray()
            ),
            currentVideoCid = 3
        )
    ) {
        MaterialDarkTheme {
            VideoListMenu(
                onClickVideoListItem = {},
                onClose = {}
            )
        }
    }
}

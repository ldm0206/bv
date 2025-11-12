package dev.aaa1115910.bv.player.tv.controller.playermenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.player.entity.LocalVideoPlayerConfigData
import dev.aaa1115910.bv.player.entity.PlayMode
import dev.aaa1115910.bv.player.entity.VideoPlayerOthersMenuItem
import dev.aaa1115910.bv.player.tv.controller.LocalMenuFocusStateData
import dev.aaa1115910.bv.player.tv.controller.MenuFocusState
import dev.aaa1115910.bv.player.tv.controller.playermenu.component.MenuListItem
import dev.aaa1115910.bv.player.tv.controller.playermenu.component.RadioMenuList
import dev.aaa1115910.bv.util.ifElse

@Composable
fun OthersMenuList(
    modifier: Modifier = Modifier,
    onPlayModeChange: (PlayMode) -> Unit,
    onFocusStateChange: (MenuFocusState) -> Unit
) {
    val context = LocalContext.current
    val videoPlayerConfigData = LocalVideoPlayerConfigData.current
    val focusState = LocalMenuFocusStateData.current
    val parentMenuFocusRequester = remember { FocusRequester() }
    val parentMenuPositionFocusRequester = remember { FocusRequester() }
    var selectedOthersMenuItem by remember { mutableStateOf(VideoPlayerOthersMenuItem.PlayMode) }

    Row(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val menuItemsModifier = Modifier
            .width(216.dp)
            .padding(horizontal = 8.dp)
        AnimatedVisibility(visible = focusState.focusState != MenuFocusState.MenuNav) {
            when (selectedOthersMenuItem) {
                VideoPlayerOthersMenuItem.PlayMode -> RadioMenuList(
                    modifier = menuItemsModifier,
                    items = PlayMode.entries.map { it.getDisplayName(context) },
                    selected = PlayMode.entries
                        .indexOfFirst { it.ordinal == videoPlayerConfigData.currentPlayMode.ordinal },
                    onSelectedChanged = { onPlayModeChange(PlayMode.entries[it]) },
                    onFocusBackToParent = {
                        onFocusStateChange(MenuFocusState.Menu)
                        parentMenuFocusRequester.requestFocus()
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .focusRequester(parentMenuFocusRequester)
                .padding(horizontal = 8.dp)
                .onPreviewKeyEvent {
                    if (it.type == KeyEventType.KeyUp) {
                        if (listOf(Key.Enter, Key.DirectionCenter).contains(it.key)) {
                            return@onPreviewKeyEvent false
                        }
                        return@onPreviewKeyEvent true
                    }
                    when (it.key) {
                        Key.DirectionRight -> onFocusStateChange(MenuFocusState.MenuNav)
                        Key.DirectionLeft -> onFocusStateChange(MenuFocusState.Items)
                        else -> {}
                    }
                    false
                }
                .focusRestorer(parentMenuPositionFocusRequester),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(VideoPlayerOthersMenuItem.entries) { index, item ->
                MenuListItem(
                    modifier = Modifier
                        .ifElse(
                            index == 0,
                            Modifier.focusRequester(parentMenuPositionFocusRequester)
                        ),
                    text = item.getDisplayName(context),
                    selected = selectedOthersMenuItem == item,
                    onClick = {},
                    onFocus = { selectedOthersMenuItem = item },
                )
            }
        }
    }
}
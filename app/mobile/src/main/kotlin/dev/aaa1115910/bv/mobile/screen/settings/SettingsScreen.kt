package dev.aaa1115910.bv.mobile.screen.settings

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDragHandle
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneExpansionAnchor
import androidx.compose.material3.adaptive.layout.PaneExpansionState
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.layout.rememberPaneExpansionState
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator()

    var selectedSettings by rememberSaveable { mutableStateOf<MobileSettings?>(null) }
    val singlePart = listOf(WindowWidthSizeClass.COMPACT, WindowWidthSizeClass.MEDIUM)
        .contains(currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass)

    BackHandler(scaffoldNavigator.canNavigateBack()) {
        scope.launch { scaffoldNavigator.navigateBack() }
    }

    ListDetailPaneScaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane(
                modifier = Modifier.preferredWidth(360.dp),
                enterTransition = fadeIn() + slideInHorizontally(),
                exitTransition = fadeOut() + slideOutHorizontally()
            ) {
                SettingsCategories(
                    selectedSettings = if (singlePart) null else selectedSettings
                        ?: MobileSettings.Play,
                    onSelectedSettings = {
                        selectedSettings = it
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                        }
                    },
                    showNavBack = !scaffoldNavigator.canNavigateBack(),
                    onBack = { (context as Activity).finish() },
                )
            }
        },
        detailPane = {
            AnimatedPane(
                modifier = Modifier,
                enterTransition = fadeIn() + slideInHorizontally { it / 2 },
                exitTransition = fadeOut() + slideOutHorizontally { it / 2 }
            ) {
                SettingsDetails(
                    selectedSettings = selectedSettings ?: MobileSettings.Play,
                    showNavBack = scaffoldNavigator.canNavigateBack(),
                    onBack = { scope.launch { scaffoldNavigator.navigateBack() } }
                )
            }
        },
        paneExpansionDragHandle = { state -> PaneExpansionDragHandle(state) },
        paneExpansionState = rememberPaneExpansionState(
            keyProvider = scaffoldNavigator.scaffoldValue,
            anchors = PaneExpansionAnchors,
        )
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.PaneExpansionDragHandle(
    state: PaneExpansionState = rememberPaneExpansionState()
) {
    val interactionSource = remember { MutableInteractionSource() }
    VerticalDragHandle(
        modifier = Modifier
            .paneExpansionDraggable(
                state,
                LocalMinimumInteractiveComponentSize.current,
                interactionSource,
            ),
        interactionSource = interactionSource
    )
}

enum class MobileSettings(
    val title: String,
    val summary: String? = null
) {
    Play(title = "播放设置", summary = "画质编码、音频、循环模式"),
    About(title = "关于", summary = "一般不会有人点"),
    Advance(title = "更多设置", summary = "接口"),
    Debug(title = "调试", "瞅啥瞅");
}

private val PaneExpansionAnchors = listOf(
    PaneExpansionAnchor.Offset.fromStart(360.dp),
    PaneExpansionAnchor.Proportion(0.5f),
    PaneExpansionAnchor.Offset.fromEnd(360.dp),
)

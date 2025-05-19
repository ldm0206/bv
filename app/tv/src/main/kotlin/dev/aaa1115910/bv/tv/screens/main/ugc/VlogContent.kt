package dev.aaa1115910.bv.tv.screens.main.ugc

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.aaa1115910.biliapi.entity.ugc.UgcTypeV2

@Composable
fun VlogContent(
    modifier: Modifier = Modifier,
    state: UgcScaffoldState
) {
    UgcRegionScaffold(
        modifier = modifier,
        state = state,
        childRegionButtons = { VlogChildRegionButtons() }
    )
}

@Composable
fun VlogChildRegionButtons(modifier: Modifier = Modifier) {
    UgcChildRegionButtons(
        modifier = modifier.fillMaxWidth(),
        childUgcTypes = UgcTypeV2.vlogList
    )
}
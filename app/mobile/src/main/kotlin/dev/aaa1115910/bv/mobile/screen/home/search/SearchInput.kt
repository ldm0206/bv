package dev.aaa1115910.bv.mobile.screen.home.search

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.mobile.screen.home.SearchBarResultContent


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SearchInputContent(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    keywordSuggestions: List<String> = emptyList(),
    keywordHistories: List<String> = emptyList(),
    matchedKeyworkHistories: List<String> = emptyList(),
    searchBarState: SearchBarState = rememberSearchBarState(),
    textFieldState: TextFieldState = rememberTextFieldState(),
    searchBarExpanded: Boolean,
    onSearchBarExpandedChange: (Boolean) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    inputField: @Composable () -> Unit = {},
    onSearch: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            when (windowSize) {
                WindowWidthSizeClass.Compact -> {

                }

                else -> {
                    TopAppBar(
                        title = {},
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        )
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
            shape = if (windowSize == WindowWidthSizeClass.Compact) RoundedCornerShape(0.dp) else MaterialTheme.shapes.large,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text(
                        text = "搜索",
                        style = MaterialTheme.typography.displaySmall
                    )

                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            with(sharedTransitionScope) {
                                SearchBar(
                                    modifier = Modifier
                                        .sharedElement(
                                            sharedContentState = rememberSharedContentState("searchBar"),
                                            animatedVisibilityScope = animatedVisibilityScope
                                        ),
                                    state = searchBarState,
                                    inputField = inputField
                                )
                            }
                        }

                        else -> {
                            with(sharedTransitionScope) {
                                DockedSearchBar(
                                    modifier = Modifier
                                        .imePadding()
                                        .sharedElement(
                                            sharedContentState = rememberSharedContentState("dockedSearchBar"),
                                            animatedVisibilityScope = animatedVisibilityScope
                                        ),
                                    inputField = inputField,
                                    expanded = searchBarExpanded,
                                    onExpandedChange = onSearchBarExpandedChange,
                                ) {
                                    SearchBarResultContent(
                                        keyword = textFieldState.text.toString(),
                                        recentHistory = keywordHistories,
                                        matchedHistory = matchedKeyworkHistories,
                                        suggestions = keywordSuggestions,
                                        onSearch = onSearch,
                                        onDeleteHistory = {}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
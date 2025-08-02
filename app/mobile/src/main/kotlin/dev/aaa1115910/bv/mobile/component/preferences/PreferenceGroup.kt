package dev.aaa1115910.bv.mobile.component.preferences

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun LazyListScope.preferenceGroups(
    vararg groupContents: Pair<String?, PreferenceGroupScope.() -> Unit>,
    groupSpacing: Dp = 12.dp
) {
    var isFirstGroup = true
    groupContents.forEachIndexed { index, (title, content) ->
        val scope = PreferenceGroupScope(title, index)
        scope.content()
        if (scope.preferences.isNotEmpty()) {
            if (!isFirstGroup) {
                item(
                    key = "preference_group_spacing_$index"
                ) { Spacer(modifier = Modifier.height(groupSpacing)) }
            }
            scope.build(this)
            isFirstGroup = false
        }
    }
}

fun LazyListScope.preferenceGroup(
    title: String? = null,
    index: Int = 0,
    content: PreferenceGroupScope.() -> Unit
) {
    val scope = PreferenceGroupScope(title, index)
    scope.content()
    if (scope.preferences.isEmpty()) return
    scope.build(this)
}

@DslMarker
annotation class PreferenceGroupScopeMarker

@PreferenceGroupScopeMarker
class PreferenceGroupScope internal constructor(
    private val title: String? = null,
    private val index: Int
) {
    val preferences = mutableListOf<@Composable (shape: Shape, modifier: Modifier) -> Unit>()

    companion object {
        private val LARGE_CORNER_RADIUS = 16.dp
        private val SMALL_CORNER_RADIUS = 4.dp
    }

    internal fun build(listScope: LazyListScope) {
        if (title != null) {
            listScope.item(
                key = "preference_group_title_${this.index}_${title}"
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                        .animateItem()
                )
            }
        }

        preferences.forEachIndexed { index, itemContent ->
            val isFirst = index == 0
            val isLast = index == preferences.lastIndex
            val shape = when {
                isFirst && isLast -> RoundedCornerShape(LARGE_CORNER_RADIUS)
                isFirst -> RoundedCornerShape(
                    topStart = LARGE_CORNER_RADIUS,
                    topEnd = LARGE_CORNER_RADIUS,
                    bottomStart = SMALL_CORNER_RADIUS,
                    bottomEnd = SMALL_CORNER_RADIUS
                )

                isLast -> RoundedCornerShape(
                    bottomStart = LARGE_CORNER_RADIUS,
                    bottomEnd = LARGE_CORNER_RADIUS,
                    topStart = SMALL_CORNER_RADIUS,
                    topEnd = SMALL_CORNER_RADIUS
                )

                else -> RoundedCornerShape(SMALL_CORNER_RADIUS)
            }
            val modifier = if (!isFirst) Modifier.padding(top = 2.dp) else Modifier

            listScope.item(
                key = "preference_group_${this.index}_${title}_${index}"
            ) {
                itemContent(shape, modifier.animateItem())
            }
        }
    }
}

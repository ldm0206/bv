package dev.aaa1115910.bv.tv.component.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.material3.Button
import androidx.tv.material3.OutlinedButton
import androidx.tv.material3.Text
import dev.aaa1115910.bv.component.settings.UpdateDialog

@Composable
fun UpdateDialog(
    modifier: Modifier = Modifier,
    show: Boolean,
    onHideDialog: () -> Unit
) {
    UpdateDialog(
        modifier = modifier,
        show = show,
        onHideDialog = onHideDialog,
        text = { text ->
            Text(text = text)
        },
        button = { enabled, onClick, content ->
            Button(
                enabled = enabled,
                onClick = onClick,
                content = content
            )
        },
        outlinedButton = { enabled, onClick, content ->
            OutlinedButton(
                enabled = enabled,
                onClick = onClick,
                content = content
            )
        }
    )
}
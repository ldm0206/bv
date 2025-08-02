package dev.aaa1115910.bv.mobile.screen.settings.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme

@Composable
fun DebugContent(
    modifier: Modifier = Modifier
) {

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DebugContentPreview() {
    BVMobileTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            DebugContent(
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

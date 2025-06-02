package dev.aaa1115910.bv.mobile.screen.settings.details

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.tv.material3.Surface
import dev.aaa1115910.bv.BuildConfig
import dev.aaa1115910.bv.mobile.component.settings.UpdateDialog
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme

@Composable
fun AboutContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showUpdateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppIcon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
            ListItem(
                modifier = Modifier
                    .clickable { showUpdateDialog = true },
                headlineContent = {
                    Text("当前版本")
                },
                supportingContent = {
                    Text(text = "${BuildConfig.VERSION_NAME}.${BuildConfig.BUILD_TYPE}")
                }
            )
            ListItem(
                modifier = Modifier
                    .clickable {
                        val url = "https://github.com/aaa1115910/bv"
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    },
                headlineContent = {
                    Text("项目地址")
                },
                supportingContent = {
                    Text(text = "https://github.com/aaa1115910/bv")
                },
            )
        }
    }

    UpdateDialog(
        show = showUpdateDialog,
        onHideDialog = { showUpdateDialog = false }
    )
}

@Composable
fun AppIcon(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .width(256.dp)
                .height(128.dp),
            painter = painterResource(id = dev.aaa1115910.bv.R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = "Bug Video",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
private fun AppIconPreview() {
    BVMobileTheme {
        AppIcon()
    }
}

@Preview
@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun AboutContentPreview() {
    BVMobileTheme {
        Surface {
            AboutContent(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
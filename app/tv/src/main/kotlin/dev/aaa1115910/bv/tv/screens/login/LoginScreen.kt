package dev.aaa1115910.bv.tv.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    /*when (Prefs.apiType) {
        ApiType.Http -> {
            WebQRLoginContent(modifier)
        }

        ApiType.GRPC -> {
            SmsLoginContent(modifier)
        }
    }*/
    AppQRLoginContent(modifier)
}
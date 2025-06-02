package dev.aaa1115910.bv.mobile.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aaa1115910.bv.entity.AuthData
import dev.aaa1115910.bv.entity.BvScheme
import dev.aaa1115910.bv.mobile.R
import dev.aaa1115910.bv.mobile.component.user.UserAvatar
import dev.aaa1115910.bv.mobile.theme.BVMobileTheme
import dev.aaa1115910.bv.repository.UserRepository
import dev.aaa1115910.bv.util.toast
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
fun QrTokenResultScreen(
    modifier: Modifier = Modifier,
    userRepository: UserRepository = koinInject()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val logger = KotlinLogging.logger { }

    var parsing by remember { mutableStateOf(true) }
    var authData by remember { mutableStateOf<AuthData?>(null) }
    var error by remember { mutableStateOf<Throwable?>(null) }
    var uid by remember { mutableLongStateOf(-1L) }
    var username by remember { mutableStateOf<String>("") }
    var avatar by remember { mutableStateOf<String>("") }
    var addingUser by remember { mutableStateOf(false) }

    val onBack: () -> Unit = {
        (context as Activity).finish()
    }

    val onConfirm: () -> Unit = {
        if (!addingUser && authData != null) {
            addingUser = true
            scope.launch(Dispatchers.IO) {
                runCatching {
                    userRepository.addUser(authData!!)
                }.onFailure {
                    logger.error(it) { "Failed to save auth data to prefs" }
                    withContext(Dispatchers.Main) {
                        it.message?.toast(context)
                    }
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        R.string.qr_token_result_toast_add_success.toast(context)
                    }
                    (context as Activity).finish()
                    context.startActivity(
                        context.packageManager.getLaunchIntentForPackage(context.packageName)
                            ?.apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        runCatching {
            val uri = (context as Activity).intent.getParcelableExtra<Uri>("uri")
                ?: throw IllegalArgumentException("Uri not found in intent extras")
            val data = BvScheme.QrToken.fromUri(uri)
                ?: throw IllegalArgumentException("Invalid QR token URI: $uri")
            val qrToken = data as BvScheme.QrToken

            authData = AuthData.fromJson(qrToken.auth)
            uid = qrToken.uid
            username = qrToken.username
            avatar = qrToken.avatar
        }.onFailure {
            logger.warn(it) { "Failed to parse QR token result" }
            error = it
        }
        parsing = false
    }

    QrTokenResultContent(
        modifier = modifier,
        authData = authData,
        uid = uid,
        username = username,
        avatar = avatar,
        parsing = parsing,
        error = error,
        onBack = onBack,
        onConfirm = onConfirm
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun QrTokenResultContent(
    modifier: Modifier = Modifier,
    authData: AuthData?,
    uid: Long,
    username: String,
    avatar: String,
    parsing: Boolean,
    error: Throwable?,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_mobile_activity_qr_token_result))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (parsing) {
                LoadingIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                )
            } else if (error != null) {
                Box {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(text = error.stackTraceToString())
                    }
                }
            } else if (authData != null) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserAvatar(
                            modifier = Modifier.padding(vertical = 24.dp),
                            avatar = avatar
                        )
                        Text(
                            text = username,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "$uid",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    FilledTonalButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(24.dp)
                            .fillMaxWidth(),
                        onClick = onConfirm
                    ) {
                        Text(text = stringResource(R.string.qr_token_result_button_add_user))
                    }
                }
            } else {
                Text("unknown error")
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QrTokenResultContentParsingPreview() {
    BVMobileTheme {
        QrTokenResultContent(
            authData = null,
            uid = -1L,
            username = "",
            avatar = "",
            parsing = true,
            error = null,
            onBack = {},
            onConfirm = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QrTokenResultContentPreview() {
    BVMobileTheme {
        QrTokenResultContent(
            authData = AuthData(
                uid = 123456789L,
                uidCkMd5 = "exampleUidCkMd5",
                sid = "exampleSid",
                sessData = "exampleSessData",
                biliJct = "exampleBiliJct",
                tokenExpiredData = 1728000000L, // Example timestamp
                accessToken = "exampleAccessToken",
                refreshToken = "exampleRefreshToken"
            ),
            uid = 3252351L,
            username = "bishi",
            avatar = "",
            parsing = false,
            error = null,
            onBack = {},
            onConfirm = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QrTokenResultContentErrorPreview() {
    BVMobileTheme {
        QrTokenResultContent(
            authData = AuthData(
                uid = 123456789L,
                uidCkMd5 = "exampleUidCkMd5",
                sid = "exampleSid",
                sessData = "exampleSessData",
                biliJct = "exampleBiliJct",
                tokenExpiredData = 1728000000L, // Example timestamp
                accessToken = "exampleAccessToken",
                refreshToken = "exampleRefreshToken"
            ),
            uid = -1L,
            username = "",
            avatar = "",
            parsing = false,
            error = IllegalStateException("An error occurred while parsing the QR token result"),
            onBack = {},
            onConfirm = {}
        )
    }
}
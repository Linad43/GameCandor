package com.example.gamecandor.data

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

//@Composable
//fun HtmlFromAssetsScreen() {
//    val textSize = remember { GameRepository.getSizeText() }
//
//    AndroidView(
//        factory = { ctx ->
//            WebView(ctx).apply {
//                settings.javaScriptEnabled = true
//
//                webViewClient = object : WebViewClient(){
//                    override fun onPageFinished(view: WebView?, url: String?) {
//                        applyTextSize(this@apply, textSize)
//                    }
//                }
//                loadUrl("file:///android_asset/rules_v2.html")
//            }
//        }
//    )
//}

@Composable
fun HtmlFromAssetsScreen(textSize: TextSize) {

    val webViewRef = remember { mutableStateOf<WebView?>(null) }

    AndroidView(
//        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            WebView(ctx).apply {

                webViewRef.value = this

                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        applyTextSize(this@apply, textSize)
                    }
                }

                loadUrl("file:///android_asset/rules_v2.html")
            }
        }
    )

    // 🔥 реагируем на изменение размера
    LaunchedEffect(textSize) {
        webViewRef.value?.let {
            applyTextSize(it, textSize)
        }
    }
}
fun applyTextSize(webView: WebView, textSize: TextSize){
    val size = when(textSize){
        TextSize.SMALL -> "14px"
        TextSize.MEDIUM -> "16px"
        TextSize.LARGE -> "20px"
    }
    webView.evaluateJavascript(
        "document.documentElement.style.setProperty('--base-font-size', '$size');",
        null
    )
}
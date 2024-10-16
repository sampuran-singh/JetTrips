package com.example.jettrips.utils

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun OpenWebView(url: String) {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}


@Composable
fun TermsAndPrivacyText() {
    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        append("By logging in or registering, you agree to our ")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Terms of Service")
        }
        append(" and ")
        pushStringAnnotation(tag = "privacy_policy", annotation = "https://opensource.org/privacy")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        modifier = Modifier.padding(16.dp),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "privacy_policy", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
        }
    )
}


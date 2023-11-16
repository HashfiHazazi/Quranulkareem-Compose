@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quranappph1.screens

import android.app.Activity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.quranappph1.R
import com.just.agentweb.AgentWeb

@Preview
@Composable
fun QiblaFinderScreenPreview() {
    QiblaFinderScreen()
}

@Composable
fun QiblaFinderScreen(
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    val url = "https://qiblafinder.withgoogle.com/intl/id/"


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                title = {
                    Text(
                        text = "Arah kiblat",
                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_quranulkarem),
                        contentDescription = "Logo Quranul-Kareem",
                        modifier = Modifier.size(48.dp)
                    )
                }
            )
        }
    ) {
        val padding = it
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    LinearLayout(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        AgentWeb.with(activity)
                            .setAgentWebParent(this, this.layoutParams)
                            .useDefaultIndicator()
                            .createAgentWeb()
                            .ready()
                            .go(url)
                    }
                }
            )
        }
    }
}
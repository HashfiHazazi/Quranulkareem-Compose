package com.ergophile_project.quranulkareem.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.kotpref.SettingsPrefrences
import com.ergophile_project.quranulkareem.utils.GlobalState

@Composable
fun OnBoardingScreen4(
    goToOnBoarding3: () -> Unit,
    goToHome: () -> Unit
) {
    val context = LocalContext.current
    val compositionOnBoarding4 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.switch_theme_animation))
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(280.dp),
                composition = compositionOnBoarding4,
                iterations = LottieConstants.IterateForever
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp),
                text = "Fitur switch theme light/dark yang bisa anda pilih sesuai keinginan untuk kenyamanan saat menggunakan aplikasi.",
                fontFamily = FontFamily(Font(R.font.monda_regular)),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            Switch(
                modifier = Modifier.padding(start = 16.dp),
                checked = GlobalState.isDarkMode,
                onCheckedChange = { isChecked ->
                    GlobalState.isDarkMode = isChecked
                    SettingsPrefrences.isDarkMode = isChecked
                    Toast.makeText(
                        context,
                        if (SettingsPrefrences.isDarkMode && GlobalState.isDarkMode == false) {
                            ""
                        } else if (SettingsPrefrences.isDarkMode && GlobalState.isDarkMode == true) {
                            "Switched to Dark mode"
                        } else {
                               "Switched to Light mode"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        goToOnBoarding3()
                    }
                ) {
                    Text(
                        text = "Back",
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                TextButton(
                    onClick = {
                        SettingsPrefrences.isOnBoarding = false
                        GlobalState.isOnBoarding = false
                        goToHome()
                    }
                ) {
                    Text(
                        text = "Open",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
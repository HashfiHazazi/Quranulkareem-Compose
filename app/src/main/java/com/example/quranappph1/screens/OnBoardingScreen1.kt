package com.example.quranappph1.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quranappph1.R

@Composable
fun OnBoardingScreen1(
    goToOnBoarding2: () -> Unit,
    goToOnBoarding4: () -> Unit
) {
    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.splash_screen1),
                contentDescription = null,
                modifier = Modifier.size(280.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp),
                text = "Selamat datang di Quranulkareem, Aplikasi Qur'an digital indonesia yang dilengkapi fitur untuk memudahkan anda dalam membaca & memahami Al-Quran.",
                fontFamily = FontFamily(Font(R.font.monda_regular)),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                TextButton(
                    onClick = {
                        goToOnBoarding4()
                    }
                ) {
                    Text(
                        text = "Skip",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row (
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
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
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
                TextButton(
                    onClick = {
                        goToOnBoarding2()
                    }
                ) {
                    Text(
                        text = "Next",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quranappph1.screens.IndexListScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranappph1.R
import com.example.quranappph1.data.database.QoranDatabase

@Composable
fun SurahScreen(
    modifier: Modifier = Modifier,
    goToRead: (surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, index: Int?) -> Unit,
) {
    val context = LocalContext.current
    val dao = QoranDatabase.getInstance(context).dao()
    val listReadBySurah = dao.surahListIndex()

    listReadBySurah.collectAsState(initial = emptyList()).let { state ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(state.value) { surah ->

                Spacer(modifier = Modifier.size(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            goToRead.invoke(surah.surahNumber, null, null, null)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {

                    val dataTurunSurah = if (surah.turunSurah == "Meccan") {
                        "Makkah"
                    } else {
                        "Madinah"
                    }

                    val dataIconDescendPlace = if (surah.turunSurah == "Meccan") {
                        R.drawable.kaba_icon
                    } else {
                        R.drawable.madina_icon
                    }

                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.border_num),
                                contentDescription = "Border Num Surah",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fillMaxSize()
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${surah.surahNumber}",
                                    fontFamily = FontFamily(Font(R.font.monda_regular)),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary

                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(8.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${surah.surahNameEN}",
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "$dataTurunSurah • ${surah.numberOfAyah} Ayat",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 14.sp
                            )
                        }

                        Column(
                            modifier = Modifier.width(56.dp),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = dataIconDescendPlace),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "${surah.surahNameArabic}",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

        }
    }
}
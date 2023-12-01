package com.ergophile_project.quranulkareem.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.kotpref.LastReadState
import com.ergophile_project.quranulkareem.screens.IndexListScreens.HalamanScreen
import com.ergophile_project.quranulkareem.screens.IndexListScreens.JuzScreen
import com.ergophile_project.quranulkareem.screens.IndexListScreens.SurahScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goToRead: (surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, index: Int?) -> Unit,
    goToBookmarks: () -> Unit,
    goToSearch: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                title = {
                    Text(
                        text = "Qur'anul-Kareem",
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
                },
                actions = {
                    IconButton(onClick = {
                        goToSearch()
                    }) {
                        Card(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }

                    }
                }
            )
        }

    ) {
        val padding = it
        val tabList = listOf("Surah", "Juz", "Halaman")
        val scope = rememberCoroutineScope()
        val pageState = rememberPagerState(
            initialPage = 0,
            pageCount = { tabList.size }
        )
        Column(
            modifier = Modifier.padding(paddingValues = padding)
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(Color.Transparent),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable {
                                goToRead.invoke(
                                    LastReadState.surahNum,
                                    null,
                                    null,
                                    LastReadState.index
                                )
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Last Read",
                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Surah: ${if (LastReadState.surahName == "") "-" else LastReadState.surahName}",
                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Surah ke: ${if (LastReadState.surahNum == -1) "-" else LastReadState.surahNum}",
                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Ayat ke: ${if (LastReadState.ayahNumber == -1) "-" else LastReadState.ayahNumber}",
                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Box {
                        Divider(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                        )

                    }
                    Column(
                        modifier = Modifier
                            .height(132.dp)
                            .weight(0.5f)
                            .clickable { goToBookmarks() },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkAdded,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(
                            text = "Bookmarks ayat",
                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            }

            TabRow(
                selectedTabIndex = pageState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                tabList.forEachIndexed { index, text ->
                    Tab(
                        modifier = Modifier.height(56.dp),
                        selected = index == pageState.currentPage,
                        onClick = {
                            scope.launch {
                                pageState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(text = text)
                    }
                }
            }
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pageState
            ) { page ->
                when (page) {
                    0 -> SurahScreen(
                        modifier = Modifier.fillMaxSize(), goToRead = goToRead
                    )

                    1 -> JuzScreen(
                        modifier = Modifier.fillMaxSize(), goToRead = goToRead

                    )

                    2 -> HalamanScreen(
                        modifier = Modifier.fillMaxSize(), goToRead = goToRead

                    )
                }
            }

        }
    }
}
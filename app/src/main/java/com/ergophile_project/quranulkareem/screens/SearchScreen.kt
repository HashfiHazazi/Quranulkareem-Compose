package com.ergophile_project.quranulkareem.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.database.QoranDatabase
import com.ergophile_project.quranulkareem.data.database.entities.SeachSurah
import com.ergophile_project.quranulkareem.data.database.entities.SearchAyah
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    goToRead: (surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, index: Int?) -> Unit,
    goToHome: () -> Unit
) {
    val searchSurahResult = remember {
        mutableStateListOf<SeachSurah>()
    }

    val searchAyahResult = remember {
        mutableStateListOf<SearchAyah>()
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val qoranDatabase = QoranDatabase.getInstance(context)

    var isSearchActive by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
            },
            onSearch = { query ->
                val getSurahBySearchDao = qoranDatabase.dao().getSurahBySearch(searchQuery)
                searchSurahResult.clear()
                scope.launch {
                    getSurahBySearchDao.collect {
                        searchSurahResult.addAll(it)
                    }
                }
                val getAyahBySearchDao = qoranDatabase.dao().getAyahBySearch(searchQuery)
                searchAyahResult.clear()
                scope.launch {
                    getAyahBySearchDao.collect {
                        searchAyahResult.addAll(it)
                    }
                }
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        goToHome()
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            active = isSearchActive,
            onActiveChange = {
                isSearchActive = it
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
            ),
            tonalElevation = SearchBarDefaults.Elevation,
            shape = RoundedCornerShape(0.dp),
            placeholder = {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    Text(text = "Cari surah & terjemah ayat di sini...", fontFamily = FontFamily(Font(R.font.monda_regular)), color = Color.Gray, fontSize = 14.sp)
                }
            }
        ) {
            LazyColumn {
                items(searchAyahResult) { ayah ->
                    var isVisible by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            MaterialTheme.colorScheme.background
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    ) {
                        Row(
                            Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.size(8.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${ayah.surahNameEmlaey} • ${ayah.ayahNumber}",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    text = "${ayah.ayahText}",
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Spacer(modifier = Modifier.size(2.dp))
                                Text(
                                    text = ayah.translateId,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AnimatedVisibility(
                                            visible = isVisible,
                                            enter = fadeIn() + slideInHorizontally(),
                                            exit = fadeOut() + slideOutHorizontally()
                                        ) {
                                            Row {
                                                IconButton(onClick = {
                                                    val text = listOf(
                                                        ayah.ayahText,
                                                        ayah.translateId
                                                    ).toString()
                                                    val clipboardManager =
                                                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                    var clipData: ClipData =
                                                        ClipData.newPlainText("text", text)
                                                    clipboardManager.setPrimaryClip(clipData)
                                                    Toast.makeText(
                                                        context,
                                                        "Copied to Clipboard",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Default.ContentCopy,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onBackground
                                                    )
                                                }
                                                Spacer(modifier = Modifier.size(8.dp))
                                                IconButton(onClick = {
                                                    val intent = Intent(Intent.ACTION_SEND)
                                                    val text = listOf(
                                                        ayah.ayahText,
                                                        ayah.translateId
                                                    ).toString()
                                                    intent.type = "text/plain"
                                                    intent.putExtra(Intent.EXTRA_TEXT, text)
                                                    ContextCompat.startActivity(
                                                        context,
                                                        Intent.createChooser(intent, "Share with"),
                                                        null
                                                    )
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Default.Share,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onBackground
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        IconButton(onClick = {
                                            isVisible = !isVisible
                                        }
                                        ) {
                                            Icon(
                                                imageVector = if (isVisible == false) Icons.Default.ArrowLeft else Icons.Default.Close,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onBackground
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
            LazyColumn {
                items(searchSurahResult) { surah ->
                    Spacer(modifier = Modifier.size(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(56.dp),
                        colors = CardDefaults.cardColors(
                            MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        onClick = {
                            goToRead.invoke(surah.surahNumber!!, null, null, null)
                        }
                    ) {
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
                                    text = "${surah.surahNameEmlaey}",
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "${if(surah.surahDescendPlace == "Meccan") "Makkah" else "Madinah"} • ${surah.totalAyah} Ayat",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
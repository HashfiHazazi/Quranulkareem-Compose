@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ergophile_project.quranulkareem.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.database.BookmarkDatabase
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookMarksScreen(
    goToHomeScreen: () -> Unit,
    goToRead: (surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, index: Int?) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bookmarkDao = BookmarkDatabase.getInstance(context).bookmarkdao()
    var alertDialogDeleteAll by remember {
        mutableStateOf(false)
    }

    var isBookmarksEmpty by remember {
        mutableStateOf(false)
    }

    var lazyColumnState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                title = {
                    Text(
                        text = "Bookmarks",
                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                navigationIcon = {
                    Card(
                        modifier = Modifier
                            .size(48.dp),
                        colors = CardDefaults.cardColors(Color.Transparent),
                        border = BorderStroke(3.dp, MaterialTheme.colorScheme.onBackground),
                    ) {
                        IconButton(
                            onClick = {
                                goToHomeScreen()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }
                    }
                },
                actions = {
                    if (isBookmarksEmpty) {

                    } else {
                        TextButton(onClick = {
                            alertDialogDeleteAll = true
                        }) {
                            Text(text = "Delete All")
                        }

                        if (alertDialogDeleteAll) {
                            AlertDialog(
                                onDismissRequest = {
                                    alertDialogDeleteAll = false
                                },
                                title = {
                                    Text(text = "Hapus semua bookmarks?")
                                },
                                text = {
                                    Text("Dengan menekan tombol 'Ya' anda akan menghapus semua bookmarks yang telah anda simpan!")
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            scope.launch {
                                                bookmarkDao.deleteAllFromBookmark()
                                            }
                                            alertDialogDeleteAll = false
                                        }) {
                                        Text("Ya")
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            alertDialogDeleteAll = false
                                        }) {
                                        Text("Tidak")
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    ) {
        val padding = it
        bookmarkDao.getAllBookmarks().collectAsState(initial = emptyList()).let { state ->
            isBookmarksEmpty = state.value.isEmpty()
            if (isBookmarksEmpty) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Tidak ada bookmarks ayat yang tersimpan",
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(
                            Font(R.font.monda_regular)
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }

            } else {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues = padding),
                    contentPadding = PaddingValues(16.dp),
                    state = lazyColumnState
                ) {
                    itemsIndexed(state.value) { index, itemBookmarks ->
                        val formattedTime = formatInstant(itemBookmarks.createdAt)
                        Spacer(modifier = Modifier.size(16.dp))
                        Card(
                            onClick = {
                                goToRead.invoke(
                                    itemBookmarks.surahNumber,
                                    null,
                                    null,
                                    itemBookmarks.ayahNumber
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    Modifier
                                        .size(48.dp)
                                        .padding(end = 8.dp)
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
                                            text = "${index + 1}",
                                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "${itemBookmarks.surahName} : ${itemBookmarks.ayahNumber}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = "$formattedTime",
                                        fontSize = 14.sp
                                    )
                                }
                                IconButton(onClick = {
                                    scope.launch {
                                        bookmarkDao.deleteBookmark(
                                            bookmark = itemBookmarks
                                        )
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_close_24),
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun formatInstant(instantMillis: Long): String {
    val instant = Instant.ofEpochMilli(instantMillis)
    val formatter = DateTimeFormatter
        .ofPattern("dd MMM yyyy", Locale.getDefault())
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}
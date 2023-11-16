@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.example.quranappph1.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.createChooser
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.core.content.ContextCompat.startActivity
import com.example.quranappph1.R
import com.example.quranappph1.data.database.BookmarkDatabase
import com.example.quranappph1.data.database.QoranDatabase
import com.example.quranappph1.data.database.entities.Bookmark
import com.example.quranappph1.data.kotpref.LastReadState
import com.example.quranappph1.data.kotpref.SettingsPrefrences
import com.example.quranappph1.screens.read.components.FootNotesBottomSheet
import com.example.quranappph1.screens.read.components.SpannableText
import com.example.quranappph1.service.player.MyPlayerService
import com.example.quranappph1.utils.TajweedHelper
import com.example.quranappph1.utils.toAnnotatedString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ReadQuranScreen(
    goBack: () -> Unit,
    surahNumber: Int,
    juzNumber: Int,
    pageNumber: Int,
    index: Int
) {
    val context = LocalContext.current

    val dao = QoranDatabase.getInstance(context).dao()

    val bookmarkDao = BookmarkDatabase.getInstance(context).bookmarkdao()

    val scope = rememberCoroutineScope()

    val footNotesState = remember {
        mutableStateOf("")
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    val lazyColumnState = rememberLazyListState()

    val readSurah = when {
        surahNumber != -1 -> {
            dao.getReadWithSurah(surahNumber)
        }

        juzNumber != -1 -> {
            dao.getReadWithJuz(juzNumber)
        }

        pageNumber != -1 -> {
            dao.getReadWithPage(pageNumber)
        }

        else -> throw Exception("Uknown")
    }


    var surahNameEn by remember {
        mutableStateOf("")
    }

    var descendPlace by remember {
        mutableStateOf("")
    }

    var surahNameId by remember {
        mutableStateOf("")
    }

    var bismillahOrAudzu by remember {
        mutableStateOf("")
    }

    var surahNameArabic by remember {
        mutableStateOf("")
    }

    var titleTextTopBar by remember {
        mutableStateOf("")
    }
    var isBottomSheet by remember {
        mutableStateOf(false)
    }

    var bottomSheetQoriState = rememberModalBottomSheetState()

    var currentPlayedAyah by remember {
        mutableStateOf("")
    }

    var playerClientState by remember {
        mutableStateOf(true)
    }

    val qariPlaylist = mutableListOf<MusicItem>()

    LaunchedEffect(key1 = true) {
        readSurah.collectLatest {
            val descendPlaceText = it[0].surahDescendPlace
            if (descendPlaceText == "Meccan") {
                descendPlace = "Makkah"
            } else {
                descendPlace = "Madinah"
            }
        }
    }

    LaunchedEffect(key1 = true) {
        val bismillahText = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ"
        val audzhuText = "أَعُوْذُ بِاللّٰهِ مِنَ الشَّيْطٰانِ الرَّجِيْمِ"
        readSurah.collectLatest {
            val surahNameEn = it[0].surahNameEnglish
            if (surahNameEn == "At-Taubah" || surahNameEn == "Al-Fātiḥah") {
                bismillahOrAudzu = audzhuText
            } else {
                bismillahOrAudzu = bismillahText
            }
        }
    }

    LaunchedEffect(key1 = true) {
        readSurah.collectLatest {
            val nameSurah = it[0].surahNameEnglish
            surahNameEn = "$nameSurah"
        }
    }

    LaunchedEffect(key1 = true) {
        readSurah.collectLatest {
            val nameSurah = it[0].surahNameArabic
            surahNameArabic = "$nameSurah"
        }
    }

    LaunchedEffect(key1 = true) {
        readSurah.collectLatest {
            val nameSurah = it[0].surahNameBahasa
            surahNameId = "$nameSurah"
        }
    }

    LaunchedEffect(key1 = true) {
        readSurah.collectLatest {
            val indexSurah = it[0].surahNameEnglish
            val indexJuz = it[0].juzNumber
            val indexPage = it[0].pageNumber

            titleTextTopBar = when {
                surahNumber != -1 -> "$indexSurah"
                juzNumber != -1 -> "Juz $indexJuz"
                pageNumber != -1 -> "Halaman $indexPage"
                else -> "Nothing"
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(350)
        if (index == -1) {
            return@LaunchedEffect
        }
        if (index >= 1){
            lazyColumnState.scrollToItem(
                index
            )
        }
    }

    val playerClient = remember {
        PlayerClient.newInstance(context, MyPlayerService::class.java)
    }

    var isPlayerPlaying by remember {
        mutableStateOf(false)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            FootNotesBottomSheet(
                footNotesContent = footNotesState.value,
                hideBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.show()
                    }
                })
        },
    ) { innerPadding ->
        Scaffold(
            modifier = Modifier.padding(paddingValues = innerPadding),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    title = {
                        Text(
                            text = titleTextTopBar,
                            fontFamily = FontFamily(Font(R.font.monda_regular)),
                            fontWeight = FontWeight.SemiBold
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
                                    goBack()
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
                    }
                )
            }
        ) {
            val padding = it
            readSurah.collectAsState(initial = emptyList()).let { state ->
                LazyColumn(
                    state = lazyColumnState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = padding),
                    horizontalAlignment = CenterHorizontally,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    itemsIndexed(state.value) { index, qoran ->
                        LastReadState.apply {
                            ayahNumber = qoran.ayahNumber!!
                            surahName = qoran.surahNameEmlaey!!
                            surahNum = qoran.surahNumber!!
                            this.index = index
//                            readQoranType = readType
                        }
                        when (qoran.ayahNumber == 1) {
                            true -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = CenterHorizontally
                                ) {
                                    Text(
                                        text = "${qoran.surahNameEnglish}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(
                                            Font(R.font.monda_regular)
                                        )
                                    )
                                    Row {
                                        Text(
                                            text = "${qoran.surahNameBahasa}",
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(R.font.monda_regular)
                                            ),
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = "•",
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(R.font.monda_regular)
                                            ),
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = "${qoran.surahNameArabic}",
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(R.font.monda_regular)
                                            ),
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(16.dp))
                                    val iconDescendPlace =
                                        if (qoran.surahDescendPlace == "Meccan") {
                                            R.drawable.kaba_icon
                                        } else {
                                            R.drawable.madina_icon
                                        }
                                    Icon(
                                        painter = painterResource(id = iconDescendPlace),
                                        contentDescription = null,
                                        modifier = Modifier.size(56.dp),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = descendPlace,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(
                                            Font(R.font.monda_regular)
                                        )
                                    )
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Card(
                                        onClick = {
                                            playerClient.stop()
                                            state.value.forEach { singleAyat ->
                                                val formatSurahNumber =
                                                    convertNumberToThreeDigits(singleAyat.surahNumber!!)
                                                val formatAyahNumber =
                                                    convertNumberToThreeDigits(singleAyat.ayahNumber!!)
                                                val musicItem = createMusicItem(
                                                    title = "${singleAyat.surahNameEnglish}",
                                                    surahNumber = formatSurahNumber,
                                                    ayahNumber = formatAyahNumber
                                                )
                                                qariPlaylist.add(musicItem)
                                            }

                                            playerClient.connect {
                                                val qoriPlaylist =
                                                    createSurahPlaylist(playQari = qariPlaylist)
                                                playerClient.setPlaylist(qoriPlaylist!!, true)
                                                playerClient.playMode = PlayMode.PLAYLIST_LOOP
                                                currentPlayedAyah =
                                                    "${qoran.surahNameEnglish}"
                                                isBottomSheet = true
                                            }
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(16.dp),
                                            verticalAlignment = CenterVertically
                                        ) {
                                            Text(
                                                text = "Putar per surah", fontFamily = FontFamily(
                                                    Font(R.font.monda_regular)
                                                ), fontWeight = FontWeight.Medium
                                            )

                                            Spacer(modifier = Modifier.size(4.dp))

                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_volume_up_24),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Text(
                                        text = bismillahOrAudzu,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 24.sp
                                    )
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        thickness = 2.dp
                                    )
                                }
                            }

                            else -> {

                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = CenterVertically
                        ) {
                            Box(
                                Modifier.size(32.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.border_num),
                                    contentDescription = "Border Num",
                                    modifier = Modifier.fillMaxSize(),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(2.dp),
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "${qoran.ayahNumber}",
                                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                text = TajweedHelper.getTajweed(
                                    context = context,
                                    s = Regex("\\d+\$").replace(qoran.ayahText!!, "")
                                ).toAnnotatedString(MaterialTheme.colorScheme.primary),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.End,
                                lineHeight = 32.sp,

                                )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        when (SettingsPrefrences.isVisibleTranslate) {
                            SettingsPrefrences.FOCUS_READ -> {
                                when (SettingsPrefrences.selectedLanguage) {
                                    SettingsPrefrences.INDONESIA -> {
                                        SpannableText(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "${qoran.translateToBahasa}",
                                            onClick = { footNoteNumber ->
                                                footNotesState.value = qoran.footNotesBahasa ?: ""
                                                scope.launch {
                                                    scaffoldState.bottomSheetState.expand()
                                                }
                                            }
                                        )
                                    }

                                    else -> {
                                        SpannableText(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "${qoran.translateToEnglish}",
                                            onClick = { footNoteNumber ->
                                                footNotesState.value = qoran.footNotesEnglish ?: ""
                                                scope.launch {
                                                    scaffoldState.bottomSheetState.expand()
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            else -> {
                            }
                        }


                        Spacer(modifier = Modifier.size(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    val text = listOf(
                                        qoran.ayahText,
                                        qoran.translateToBahasa
                                    ).toString()
                                    val clipboardManager =
                                        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                    var clipData: ClipData = ClipData.newPlainText("text", text)
                                    clipboardManager.setPrimaryClip(clipData)
                                    Toast.makeText(
                                        context,
                                        "Copied to Clipboard",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_content_copy_24),
                                    contentDescription = null
                                )
                            }
                            IconButton(
                                onClick = {
                                    val intent = Intent(ACTION_SEND)
                                    val text = listOf(
                                        qoran.ayahText,
                                        qoran.translateToBahasa
                                    ).toString()
                                    intent.type = "text/plain"
                                    intent.putExtra(Intent.EXTRA_TEXT, text)
                                    startActivity(
                                        context,
                                        createChooser(intent, "Share with"),
                                        null
                                    )

                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_share_24),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        bookmarkDao.insertBookmark(
                                            Bookmark(
                                                surahName = qoran.surahNameEnglish,
                                                ayahNumber = qoran.ayahNumber,
                                                surahNumber = qoran.surahNumber
                                            )
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.bookmarks_icon),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            IconButton(
                                onClick = {
                                    playerClient.stop()
                                    val formatSurahNumber =
                                        convertNumberToThreeDigits(qoran.surahNumber!!)
                                    val formatAyahNumber =
                                        convertNumberToThreeDigits(qoran.ayahNumber!!)
                                    val musicItem = createMusicItem(
                                        title = "${qoran.surahNameEnglish} : ${qoran.ayahNumber}",
                                        surahNumber = formatSurahNumber,
                                        ayahNumber = formatAyahNumber
                                    )
                                    playerClient.connect {
                                        playerClient.playMode = PlayMode.SINGLE_ONCE
                                        val qoriPlaylist =
                                            createSinglePlaylist(musicItem = musicItem)
                                        playerClient.setPlaylist(qoriPlaylist!!, true)
                                        currentPlayedAyah =
                                            "${qoran.surahNameEnglish} : ${qoran.ayahNumber}"
                                        isBottomSheet = true
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_volume_up_24),
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 2.dp
                        )
                    }
                }
            }
        }
        if (isBottomSheet == true) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(164.dp),
                onDismissRequest = {
                    isBottomSheet = false
                    playerClient.stop()
                    scope.launch {
                        bottomSheetQoriState.hide()
                    }
                },
                sheetState = bottomSheetQoriState

            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = currentPlayedAyah,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    IconButton(
                        onClick = {
                            playerClient.playPause(); isPlayerPlaying = playerClient.isPlaying
                        }) {
                        Icon(
                            imageVector = if (!isPlayerPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    IconButton(
                        onClick = {
                            playerClient.stop()
                            isPlayerPlaying = playerClient.isPlaying
                            isBottomSheet = false
                            scope.launch {
                                bottomSheetQoriState.hide()
                            }
                        }) {
                        Icon(imageVector = Icons.Default.Stop, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    IconButton(
                        onClick = {
                            when (playerClient.playMode) {
                                PlayMode.PLAYLIST_LOOP -> {
                                    playerClientState = true
                                    playerClient.playMode = PlayMode.SINGLE_ONCE
                                }

                                else -> {
                                    playerClientState = false
                                    playerClient.playMode = PlayMode.PLAYLIST_LOOP
                                }
                            }
                        }) {
                        Icon(
                            imageVector = if (playerClientState == false) Icons.Default.Repeat else Icons.Default.RepeatOne,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

private fun createMusicItem(
    title: String, ayahNumber: String, surahNumber: String
): MusicItem {
    return MusicItem.Builder()
        .setMusicId("$ayahNumber$surahNumber")
        .autoDuration()
        .setTitle(title)
        .setIconUri(SettingsPrefrences.selectedQori.qoriPhoto)
        .setUri(
            "https://everyayah.com/data/${SettingsPrefrences.selectedQori.id}/${surahNumber}${ayahNumber}.mp3"
        )
        .setArtist(SettingsPrefrences.selectedQori.qoriName)
        .build()
}

private fun createSinglePlaylist(
    musicItem: MusicItem
): Playlist? {
    return Playlist.Builder()
        .append(musicItem)
        .build()
}

private fun createSurahPlaylist(
    playQari: List<MusicItem>
): Playlist {
    return Playlist.Builder()
        .appendAll(playQari)
        .build()
}

fun convertNumberToThreeDigits(
    number: Int
): String {
    return String.format("%03d", number)
}
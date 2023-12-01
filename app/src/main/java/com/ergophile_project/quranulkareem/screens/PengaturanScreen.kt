package com.ergophile_project.quranulkareem.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.kotpref.SettingsPrefrences
import com.ergophile_project.quranulkareem.screens.read.components.QoriBottomSheet
import com.ergophile_project.quranulkareem.utils.GlobalState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengaturanScreen(
) {
    var scaffoldStates = rememberBottomSheetScaffoldState()

    var changeLanguageState by remember {
        mutableStateOf(SettingsPrefrences.selectedLanguage)
    }

    var openDialogChangeLanguage by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldStates,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            QoriBottomSheet(
                hideBottomSheet = {
                    scope.launch {
                        scaffoldStates.bottomSheetState.show()
                    }
                }
            )
        },
    ) { innerPadding ->
        Scaffold(
            modifier = Modifier.padding(paddingValues = innerPadding),
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                    title = {
                        Text(
                            text = "Pengaturan",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.change_theme),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Dark/Light Mode",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.monda_regular))
                    )
                    Switch(
                        modifier = Modifier.padding(start = 16.dp),
                        checked = GlobalState.isDarkMode,
                        onCheckedChange = { isChecked ->
                            GlobalState.isDarkMode = isChecked
                            SettingsPrefrences.isDarkMode = isChecked
                        },
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    thickness = 2.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.glasses_icon),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Mode fokus baca",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.monda_regular))
                    )
                    Switch(
                        modifier = Modifier.padding(start = 16.dp),
                        checked = GlobalState.isVisibleTranslate,
                        onCheckedChange = { isChecked ->
                            GlobalState.isVisibleTranslate = isChecked
                            SettingsPrefrences.isVisibleTranslate = isChecked
                        },
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    thickness = 2.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.change_language),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Ganti translate bahasa",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                    )
                    IconButton(
                        onClick = {
                            openDialogChangeLanguage = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.open_icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    thickness = 2.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.change_qari),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Ganti Qari' Tilawah",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.monda_regular))
                    )
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldStates.bottomSheetState.expand()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_change_circle_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            if (openDialogChangeLanguage) {
                AlertDialog(
                    onDismissRequest = {
                        openDialogChangeLanguage = false
                    },
                    title = {
                        Text(
                            text = "Ganti translate bahasa?",
                            fontFamily = FontFamily(Font(R.font.monda_regular))
                        )
                    },
                    confirmButton = {

                    },
                    text = {
                        Column {
                            if (changeLanguageState == SettingsPrefrences.INDONESIA) {
                                Text(
                                    text = "Terjemah ayat: Bahasa indonesia",
                                    fontFamily = FontFamily(Font(R.font.monda_regular))
                                )
                            } else {
                                Text(
                                    text = "Terjemah ayat: English",
                                    fontFamily = FontFamily(Font(R.font.monda_regular))
                                )
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = changeLanguageState == SettingsPrefrences.INDONESIA,
                                    onClick = {
                                        changeLanguageState = SettingsPrefrences.INDONESIA
                                        SettingsPrefrences.selectedLanguage =
                                            SettingsPrefrences.INDONESIA
                                    })
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Bahasa indonesia",
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.monda_regular))
                                )
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = changeLanguageState == SettingsPrefrences.ENGLISH,
                                    onClick = {
                                        changeLanguageState = SettingsPrefrences.ENGLISH
                                        SettingsPrefrences.selectedLanguage =
                                            SettingsPrefrences.ENGLISH
                                    })
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "English",
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.monda_regular))
                                )
                            }

                        }
                    }
                )
            }
        }
    }
}
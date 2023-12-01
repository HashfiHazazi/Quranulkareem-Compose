package com.ergophile_project.quranulkareem.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ergophile_project.quranulkareem.data.kotpref.SettingsPrefrences

object GlobalState {
    var isDarkMode by mutableStateOf(SettingsPrefrences.isDarkMode)

    var isVisibleTranslate by mutableStateOf(SettingsPrefrences.isVisibleTranslate)

    var  SelectedQori by mutableStateOf(SettingsPrefrences.selectedQori)

    var isOnBoarding by mutableStateOf(SettingsPrefrences.isOnBoarding)
}
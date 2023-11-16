package com.example.quranappph1.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.quranappph1.data.kotpref.SettingsPrefrences

object GlobalState {
    var isDarkMode by mutableStateOf(SettingsPrefrences.isDarkMode)

    var isVisibleTranslate by mutableStateOf(SettingsPrefrences.isVisibleTranslate)

    var  SelectedQori by mutableStateOf(SettingsPrefrences.selectedQori)

    var isOnBoarding by mutableStateOf(SettingsPrefrences.isOnBoarding)
}
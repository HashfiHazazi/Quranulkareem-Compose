package com.ergophile_project.quranulkareem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemSurahScreen(
    val noUrutSurah: String,
    val noSurah: String,
    val awalAkhirSurah: String
) : Parcelable

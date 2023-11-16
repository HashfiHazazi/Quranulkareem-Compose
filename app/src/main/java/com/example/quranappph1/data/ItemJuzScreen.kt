package com.example.quranappph1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemJuzScreen(
    val noUrutJuz: String,
    val noJuz: String,
    val awalAkhirAyat: String
) : Parcelable

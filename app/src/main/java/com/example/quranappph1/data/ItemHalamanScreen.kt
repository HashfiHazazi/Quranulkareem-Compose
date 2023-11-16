package com.example.quranappph1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemHalamanScreen(
    val noUrutHalaman: String,
    val noTextHalaman: String
) : Parcelable

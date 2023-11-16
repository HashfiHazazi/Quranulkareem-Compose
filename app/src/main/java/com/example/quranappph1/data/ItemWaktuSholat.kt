package com.example.quranappph1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemWaktuSholat(
    val sholat: String,
    val waktuSholat: String,
    val backgroundSholat: Int
) : Parcelable

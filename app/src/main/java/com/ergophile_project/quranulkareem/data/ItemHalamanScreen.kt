package com.ergophile_project.quranulkareem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemHalamanScreen(
    val noUrutHalaman: String,
    val noTextHalaman: String
) : Parcelable

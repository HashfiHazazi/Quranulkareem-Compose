package com.ergophile_project.quranulkareem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemBookmarks(
    val bookmarksIndonesia: String,
    val bookmarksArab: String
) : Parcelable

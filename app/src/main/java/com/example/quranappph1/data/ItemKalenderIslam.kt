package com.example.quranappph1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemKalenderIslam(
    val namaHari: String,
    val tanggalHari: String,
    val gambarTanggal: Int
) : Parcelable

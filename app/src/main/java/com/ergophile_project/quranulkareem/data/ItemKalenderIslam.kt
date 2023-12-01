package com.ergophile_project.quranulkareem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemKalenderIslam(
    val namaHari: String,
    val tanggalHari: String,
    val gambarTanggal: Int
) : Parcelable

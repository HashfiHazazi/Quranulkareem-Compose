package com.ergophile_project.quranulkareem.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT id, sora, sora_name_ar, sora_name_en, sora_name_id, COUNT(id) as ayah_total,page FROM quran GROUP by page")
data class Page(
    @PrimaryKey val id: Int? = 0,
    @ColumnInfo(name = "page") val pageNumber: Int? = 0,
    @ColumnInfo(name = "sora_name_ar") val surahNameArabic: String? = "",
    @ColumnInfo(name = "sora_name_en") val surahNameEN: String? = "",
    @ColumnInfo(name = "ayah_total") val numberOfAyah: Int? = 0,
    @ColumnInfo(name = "sora_name_id") val surahNameID: String? = ""
)

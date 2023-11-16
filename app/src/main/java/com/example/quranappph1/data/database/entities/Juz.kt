package com.example.quranappph1.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT id, sora, sora_name_ar, sora_name_en, sora_name_id, COUNT(id) as ayah_total, jozz, aya_no, aya_text_emlaey FROM quran GROUP by jozz")
data class Juz(
    @PrimaryKey val id: Int? = 0,
    @ColumnInfo(name = "jozz") val juzNumber: Int? = 0,
    @ColumnInfo(name = "sora_name_ar") val surahNameArabic: String? = "",
    @ColumnInfo(name = "sora_name_en") val surahNameEN: String? = "",
    @ColumnInfo(name = "ayah_total") val numberOfAyah: Int? = 0,
    @ColumnInfo(name = "sora_name_id") val surahNameID: String? = "",
    @ColumnInfo(name = "aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo(name = "aya_text_emlaey") val ayahEmlaey: String? = ""
)
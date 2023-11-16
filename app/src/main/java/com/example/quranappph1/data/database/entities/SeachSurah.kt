package com.example.quranappph1.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView("SELECT id, sora, sora_name_ar, sora_name_en, sora_name_id, COUNT(id) as ayah_total, jozz, page, aya_no, aya_text_emlaey, sora_name_emlaey, sora_descend_place FROM quran GROUP By sora")
data class SeachSurah(
    @ColumnInfo(name = "jozz") val juzNumber: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0,
    @ColumnInfo(name = "page") val pageNumber: Int? = 0,
    @ColumnInfo(name = "aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo(name = "sora_name_ar") val surahNameArabic: String? = "",
    @ColumnInfo(name = "sora_name_en") val surahNameEnglish: String? = "",
    @ColumnInfo(name = "sora_name_id") val surahNameIndo: String? = "",
    @ColumnInfo(name = "sora_name_emlaey") val surahNameEmlaey: String? = "",
    @ColumnInfo(name = "ayah_total") val totalAyah: Int? = 0,
    @ColumnInfo(name = "sora_descend_place") val  surahDescendPlace: String? = ""
)

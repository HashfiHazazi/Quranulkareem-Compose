package com.example.quranappph1.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView("SELECT aya_no, id, aya_text, aya_text_emlaey, sora, sora_descend_place, sora_name_emlaey, sora_name_ar, aya_text, translation_id FROM quran GROUP By aya_text_emlaey")
data class SearchAyah(
    @ColumnInfo(name = "sora_name_emlaey") val surahNameEmlaey: String? = "",
    @ColumnInfo(name = "aya_text_emlaey") val ayahTextEmlaey: String? = "",
    @ColumnInfo(name = "sora_name_ar") val surahNameArabic: String? = "",
    @ColumnInfo(name = "aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0,
    @ColumnInfo(name = "sora_descend_place") val surahDescendPlace: String? = "",
    @ColumnInfo(name = "aya_text") val ayahText: String? = "",
    @ColumnInfo(name = "translation_id") val translateId: String = ""
)

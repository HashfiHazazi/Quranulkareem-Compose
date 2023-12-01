package com.ergophile_project.quranulkareem.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran")
data class Qoran(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "jozz") val juzNumber: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0,
    @ColumnInfo(name = "sora_name_en") val surahNameEnglish: String? = "",
    @ColumnInfo(name = "sora_name_ar") val surahNameArabic: String? = "",
    @ColumnInfo(name = "page") val pageNumber: Int? = 0,
    @ColumnInfo(name = "aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo(name = "aya_text") val ayahText: String? = "",
    @ColumnInfo(name = "aya_text_emlaey") val ayaTextEmlaey: String? = "",
    @ColumnInfo(name = "translation_id") val translateToBahasa: String? = "",
    @ColumnInfo(name = "footnotes_id") val footNotesBahasa: String? = "",
    @ColumnInfo(name = "sora_name_id") val surahNameBahasa: String? = "",
    @ColumnInfo(name = "sora_descend_place") val surahDescendPlace: String? = "",
    @ColumnInfo(name = "sora_name_emlaey") val surahNameEmlaey: String? = "",
    @ColumnInfo(name = "translation_en") val translateToEnglish: String? = "",
    @ColumnInfo(name = "footnotes_en") val footNotesEnglish: String? = ""
    )

package com.example.quranappph1.data.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val surahName: String? = "",
    val ayahNumber: Int? = 0,
    val surahNumber: Int? = 0,
    val createdAt: Long = System.currentTimeMillis(),
//    val index: Int? = -1
)

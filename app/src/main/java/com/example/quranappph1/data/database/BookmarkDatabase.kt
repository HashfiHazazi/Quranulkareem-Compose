package com.example.quranappph1.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quranappph1.R
import com.example.quranappph1.data.database.entities.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 1,
)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun bookmarkdao(): BookmarkDao

    companion object{
        @Volatile private var INSTANCE: BookmarkDatabase? = null

        fun getInstance(context: Context): BookmarkDatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): BookmarkDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                BookmarkDatabase::class.java,
                "bookmark.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
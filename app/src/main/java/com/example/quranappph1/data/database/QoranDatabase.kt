package com.example.quranappph1.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quranappph1.R
import com.example.quranappph1.data.database.entities.Juz
import com.example.quranappph1.data.database.entities.Page
import com.example.quranappph1.data.database.entities.Qoran
import com.example.quranappph1.data.database.entities.SeachSurah
import com.example.quranappph1.data.database.entities.SearchAyah
import com.example.quranappph1.data.database.entities.Surah

@Database(
    entities = [Qoran::class],
    views = [SeachSurah::class ,Surah::class, Juz::class, Page::class, SearchAyah::class],
    version = 1,
    exportSchema = false
)

abstract class QoranDatabase: RoomDatabase() {
    abstract fun dao(): QoranDao

    companion object{
        @Volatile private var INSTANCE: QoranDatabase? = null

        fun getInstance(context: Context): QoranDatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): QoranDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                QoranDatabase::class.java,
                "qoran.db"
            ).createFromInputStream{
                context.resources.openRawResource(R.raw.qoran)
            }.fallbackToDestructiveMigration().build()
        }
    }
}



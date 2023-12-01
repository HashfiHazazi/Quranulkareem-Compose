package com.ergophile_project.quranulkareem.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.database.entities.Juz
import com.ergophile_project.quranulkareem.data.database.entities.Page
import com.ergophile_project.quranulkareem.data.database.entities.Qoran
import com.ergophile_project.quranulkareem.data.database.entities.SeachSurah
import com.ergophile_project.quranulkareem.data.database.entities.SearchAyah
import com.ergophile_project.quranulkareem.data.database.entities.Surah

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



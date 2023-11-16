package com.example.quranappph1.data.database
import androidx.room.Dao
import androidx.room.Query
import com.example.quranappph1.data.database.entities.Juz
import com.example.quranappph1.data.database.entities.Page
import com.example.quranappph1.data.database.entities.Qoran
import com.example.quranappph1.data.database.entities.SeachSurah
import com.example.quranappph1.data.database.entities.SearchAyah
import com.example.quranappph1.data.database.entities.Surah
import kotlinx.coroutines.flow.Flow

@Dao
interface   QoranDao {
    @Query("SELECT * FROM quran")
    fun getAllQoranAyah(): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE sora = :surahNumber")
    fun getReadWithSurah(surahNumber: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE jozz = :juzNumber")
    fun getReadWithJuz(juzNumber: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE page = :pageNumber")
    fun getReadWithPage(pageNumber: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM seachsurah WHERE sora_name_emlaey like '%' || :surahNameEmlaey || '%'")
    fun getSurahBySearch(surahNameEmlaey: String): Flow<List<SeachSurah>>

    @Query("SELECT * FROM searchayah WHERE aya_text_emlaey like '%' || :ayahTextEmlaey || '%' OR translation_id like '%' || :ayahTextEmlaey || '%'")
    fun getAyahBySearch(ayahTextEmlaey: String): Flow<List<SearchAyah>>

    @Query("SELECT * FROM Surah")
    fun surahListIndex(): Flow<List<Surah>>

    @Query("SELECT * FROM Juz")
    fun juzListIndex(): Flow<List<Juz>>

    @Query("SELECT * FROM Page")
    fun pageListIndex(): Flow<List<Page>>
}
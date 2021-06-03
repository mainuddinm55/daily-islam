package info.learncoding.dailyislam.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.learncoding.dailyislam.data.model.Hadith

@Dao
interface HadithDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHadith(hadiths: List<Hadith>)

    @Query("SELECT * from hadith WHERE hadithNumber = :hadithNumber LIMIT 1")
    fun getHadithByNumber(hadithNumber: Int): LiveData<Hadith>

    @Query("SELECT * from hadith WHERE collection = :collection AND bookNumber =:bookNumber")
    fun getHadiths(collection: String, bookNumber: String): LiveData<List<Hadith>>
}

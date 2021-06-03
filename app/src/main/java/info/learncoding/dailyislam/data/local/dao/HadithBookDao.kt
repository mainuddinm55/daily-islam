package info.learncoding.dailyislam.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.learncoding.dailyislam.data.model.HadithBook

@Dao
interface HadithBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHadithBooks(hadithBooks: List<HadithBook>)

    @Query("SELECT * FROM hadith_book WHERE collection =:collection")
    fun getHadithBooksByCollection(collection: String):LiveData<List<HadithBook>>
}
package info.learncoding.dailyislam.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.learncoding.dailyislam.data.model.HadithCollection

@Dao
interface HadithCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHadithCollection(hadithCollections: List<HadithCollection>)

    @Query("SELECT * FROM hadith_collection")
    fun getHadithCollections(): LiveData<List<HadithCollection>>
}
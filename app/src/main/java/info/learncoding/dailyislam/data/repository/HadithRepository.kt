package info.learncoding.dailyislam.data.repository

import androidx.lifecycle.LiveData
import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.response.ApiResponse
import info.learncoding.dailyislam.data.network.response.BasePaginationResponse

interface HadithRepository {
    suspend fun fetchHadithCollections(): ApiResponse<List<HadithCollection>>
    suspend fun fetchHadithBooks(collectionName: String): ApiResponse<List<HadithBook>>
    suspend fun fetchHadiths(collectionName: String, bookNumber: String): ApiResponse<List<Hadith>>
    suspend fun fetchHadithDetails(collectionName: String, hadithNumber: Int): ApiResponse<Hadith>

    suspend fun saveHadiths(hadiths: List<Hadith>)
    suspend fun saveHadithBooks(hadithBookss: List<HadithBook>)
    suspend fun saveHadithCollections(hadithCollections: List<HadithCollection>)
    fun getHadithCollections(): LiveData<List<HadithCollection>>
    fun getHadithBooks(collectionName: String): LiveData<List<HadithBook>>
    fun getHadith(hadithNumber: Int): LiveData<Hadith>
    fun getHadiths(collection: String, bookNumber: String): LiveData<List<Hadith>>

}
package info.learncoding.dailyislam.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import info.learncoding.dailyislam.data.local.AppDatabase
import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.ApiClient
import info.learncoding.dailyislam.data.network.BaseRepositoryImp
import info.learncoding.dailyislam.data.network.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HadithRepositoryImp(private val apiClient: ApiClient, private val database: AppDatabase) :
    BaseRepositoryImp(), HadithRepository {
    companion object {
        private const val TAG = "HadithRepositoryImp"
    }

    override suspend fun fetchHadithCollections(): ApiResponse<List<HadithCollection>> {
        val response = safeApiCall { apiClient.fetchCollections() }
        Log.d(TAG, "fetchBooks: $response")
        return when (response) {
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data.data)
            }
            is ApiResponse.Error -> {
                response
            }
        }
    }

    override suspend fun fetchHadithBooks(collectionName: String): ApiResponse<List<HadithBook>> {
        val response = safeApiCall { apiClient.fetchCollectionBooks(collectionName) }
        Log.d(TAG, "fetchBooks: $response")
        return when (response) {
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data.data)
            }
            is ApiResponse.Error -> {
                response
            }
        }
    }

    override suspend fun fetchHadiths(
        collectionName: String,
        bookNumber: String
    ): ApiResponse<List<Hadith>> {
        val response = safeApiCall { apiClient.fetchHadiths(collectionName, bookNumber) }
        Log.d(TAG, "fetchBooks: $response")
        return when (response) {
            is ApiResponse.Success -> {
                Log.d(TAG, "fetchHadiths: ${response.data.data.size}")
                ApiResponse.Success(response.data.data)
            }
            is ApiResponse.Error -> {
                response
            }
        }
    }

    override suspend fun fetchHadithDetails(
        collectionName: String,
        hadithNumber: Int
    ): ApiResponse<Hadith> {
        val response = safeApiCall { apiClient.fetchHadithDetails(collectionName, hadithNumber) }
        Log.d(TAG, "fetchBooks: $response")
        return when (response) {
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data)
            }
            is ApiResponse.Error -> {
                response
            }
        }
    }

    override suspend fun saveHadiths(hadiths: List<Hadith>) {
        withContext(Dispatchers.IO) {
            database.hadithDao().saveHadith(hadiths)
        }
    }

    override suspend fun saveHadithBooks(hadithBookss: List<HadithBook>) {
        withContext(Dispatchers.IO) {
            database.hadithBookDao().saveHadithBooks(hadithBookss)
        }
    }

    override suspend fun saveHadithCollections(hadithCollections: List<HadithCollection>) {
        withContext(Dispatchers.IO) {
            database.hadithCollectionDao().saveHadithCollection(hadithCollections)
        }
    }

    override fun getHadithCollections(): LiveData<List<HadithCollection>> {
        return database.hadithCollectionDao().getHadithCollections()
    }

    override fun getHadithBooks(collectionName: String): LiveData<List<HadithBook>> {
        return database.hadithBookDao().getHadithBooksByCollection(collectionName)
    }

    override fun getHadith(hadithNumber: Int): LiveData<Hadith> {
        return database.hadithDao().getHadithByNumber(hadithNumber)
    }

    override fun getHadiths(collection: String, bookNumber: String): LiveData<List<Hadith>> {
        return database.hadithDao().getHadiths(collection, bookNumber)
    }
}
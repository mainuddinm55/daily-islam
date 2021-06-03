package info.learncoding.dailyislam.ui.hadith

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.data.network.DataState
import info.learncoding.dailyislam.data.network.NetworkResourceBounce
import info.learncoding.dailyislam.data.network.response.ApiResponse
import info.learncoding.dailyislam.data.repository.HadithRepositoryImp
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(private val hadithRepository: HadithRepositoryImp) :
    ViewModel() {
    companion object {
        private const val TAG = "HadithViewModel"
    }

    private val _hadiths = MediatorLiveData<DataState<List<Hadith>>>()
    val hadith: LiveData<DataState<List<Hadith>>> = _hadiths
    fun fetchHadith(collectionName: String, bookNumber: String){
        val result = object : NetworkResourceBounce<List<Hadith>, List<Hadith>>(viewModelScope) {
            override fun apiCallFailed(error: ApiError) {
                Log.d(TAG, "apiCallFailed: $error")
            }

            override suspend fun shouldFetch(data: List<Hadith>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDb(): LiveData<List<Hadith>> {
                return hadithRepository.getHadiths(collectionName, bookNumber)
            }

            override suspend fun requestApiCall(): ApiResponse<List<Hadith>> {
                return hadithRepository.fetchHadiths(collectionName, bookNumber)
            }

            override suspend fun saveResultResultToDb(data: List<Hadith>) {
                hadithRepository.saveHadiths(data.map {
                    it.collection = collectionName
                    it.bookNumber = bookNumber
                    return@map it
                })
            }
        }.asLiveData()
        _hadiths.addSource(result, _hadiths::postValue)
    }
}
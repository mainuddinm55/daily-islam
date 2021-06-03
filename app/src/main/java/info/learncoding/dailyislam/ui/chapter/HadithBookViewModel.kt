package info.learncoding.dailyislam.ui.chapter

import android.service.autofill.Dataset
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.network.DataState
import info.learncoding.dailyislam.data.network.NetworkResourceBounce
import info.learncoding.dailyislam.data.network.response.ApiResponse
import info.learncoding.dailyislam.data.repository.HadithRepositoryImp
import javax.inject.Inject

@HiltViewModel
class HadithBookViewModel @Inject constructor(private val hadithRepository: HadithRepositoryImp) :
    ViewModel() {
    companion object {
        private const val TAG = "HadithBookViewModel"
    }

    private val _books = MediatorLiveData<DataState<List<HadithBook>>>()
    val books: LiveData<DataState<List<HadithBook>>> get() = _books

    fun fetchHadithBooks(collectionName: String){
        val result =
            object : NetworkResourceBounce<List<HadithBook>, List<HadithBook>>(viewModelScope) {
                override fun apiCallFailed(error: ApiError) {
                    Log.d(TAG, "apiCallFailed: $error")
                }

                override suspend fun shouldFetch(data: List<HadithBook>?): Boolean {
                    return data.isNullOrEmpty()
                }

                override fun loadFromDb(): LiveData<List<HadithBook>> {
                    return hadithRepository.getHadithBooks(collectionName)
                }

                override suspend fun requestApiCall(): ApiResponse<List<HadithBook>> {
                    return hadithRepository.fetchHadithBooks(collectionName)
                }

                override suspend fun saveResultResultToDb(data: List<HadithBook>) {
                    hadithRepository.saveHadithBooks(data.map {
                        it.collection = collectionName
                        return@map it
                    })
                }

            }.asLiveData()
        _books.addSource(result, _books::postValue)
    }
}
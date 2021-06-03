package info.learncoding.dailyislam.ui.books

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.DataState
import info.learncoding.dailyislam.data.network.NetworkResourceBounce
import info.learncoding.dailyislam.data.network.response.ApiResponse
import info.learncoding.dailyislam.data.repository.HadithRepositoryImp
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithCollectionViewModel @Inject constructor(private val hadithRepo: HadithRepositoryImp) :
    ViewModel() {
    companion object {
        private const val TAG = "BookViewModel"
    }

    private val _collections = MediatorLiveData<DataState<List<HadithCollection>>>()
    val collections: LiveData<DataState<List<HadithCollection>>> get() = _collections
    fun fetchCollections() {
        val result = object :
            NetworkResourceBounce<List<HadithCollection>, List<HadithCollection>>(viewModelScope) {
            override fun apiCallFailed(error: ApiError) {
                Log.d(TAG, "apiCallFailed: $error")
            }

            override suspend fun shouldFetch(data: List<HadithCollection>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDb(): LiveData<List<HadithCollection>> {
                return hadithRepo.getHadithCollections()
            }

            override suspend fun requestApiCall(): ApiResponse<List<HadithCollection>> {
                return hadithRepo.fetchHadithCollections()
            }

            override suspend fun saveResultResultToDb(data: List<HadithCollection>) {
                hadithRepo.saveHadithCollections(data)
            }

        }.asLiveData()

        _collections.addSource(result, _collections::postValue)
    }
}
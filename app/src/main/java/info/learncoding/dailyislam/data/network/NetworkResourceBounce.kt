package info.learncoding.dailyislam.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import info.learncoding.dailyislam.data.model.ApiError
import info.learncoding.dailyislam.data.network.response.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class NetworkResourceBounce<ResultType, RequestType>(coroutineScope: CoroutineScope) {
    private val result = MediatorLiveData<DataState<ResultType>>()

    init {
        result.postValue(DataState.Loading())
        val dbSource = loadFromDb()
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            coroutineScope.launch {
                if (shouldFetch(it)) {
                    when (val response = requestApiCall()) {
                        is ApiResponse.Success -> {
                            saveResultResultToDb(response.data)
                            Log.d("TAG", "response data: ${response.data}")
                            result.addSource(loadFromDb()) {
                                result.postValue(DataState.Loaded(it))
                            }
                        }
                        is ApiResponse.Error -> {
                            apiCallFailed(response.error)
                            result.postValue(DataState.Failed(response.error))
                        }
                    }
                } else {
                    result.addSource(dbSource) {
                        result.postValue(DataState.Loaded(it))
                    }
                }
            }
        }

    }

    abstract fun apiCallFailed(error: ApiError)
    abstract suspend fun shouldFetch(data: ResultType?): Boolean
    abstract fun loadFromDb(): LiveData<ResultType>
    abstract suspend fun requestApiCall(): ApiResponse<RequestType>
    abstract suspend fun saveResultResultToDb(data: RequestType)
    fun asLiveData() = result as LiveData<DataState<ResultType>>
}
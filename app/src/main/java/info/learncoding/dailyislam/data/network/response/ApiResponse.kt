package info.learncoding.dailyislam.data.network.response

import info.learncoding.dailyislam.data.model.ApiError

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: ApiError) : ApiResponse<Nothing>()
}

package info.learncoding.dailyislam.data.network.response

data class BasePaginationResponse<out T>(
    val data: T,
    val total: Int?,
    val limit: Int?,
    val previous: Int?,
    val next: Int?
)
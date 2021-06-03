package info.learncoding.dailyislam.data.network

import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.model.HadithCollection
import info.learncoding.dailyislam.data.network.response.BasePaginationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {

    @GET("v1/collections")
    suspend fun fetchCollections() : BasePaginationResponse<List<HadithCollection>>

    @GET("v1/collections/{collectionName}/books")
    suspend fun fetchCollectionBooks(
        @Path("collectionName") collectionName: String
    ) : BasePaginationResponse<List<HadithBook>>

    @GET("v1/collections/{collectionName}/books/{bookNumber}/hadiths")
    suspend fun fetchHadiths(
        @Path("collectionName") collectionName: String,
        @Path("bookNumber") bookNumber: String
    ) : BasePaginationResponse<List<Hadith>>

    @GET("v1/collections/{collectionName}/hadiths/{hadithNumber}")
    suspend fun fetchHadithDetails(
        @Path("collectionName") collectionName: String,
        @Path("hadithNumber") hadithNumber: Int
    ) : Hadith
}
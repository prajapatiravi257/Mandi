package com.planetnoobs.mandi.api

import com.planetnoobs.mandi.main.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {


    @GET("/resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getMandiData(
        @QueryMap params: HashMap<String, Any>
    ): Response<ApiResponse>

}

package com.planetnoobs.mandi.repository

import com.planetnoobs.mandi.api.ApiService

class Repository(private val apiService: ApiService) {
    suspend fun getMandiData(params: HashMap<String, Any>) = apiService.getMandiData(params)
}

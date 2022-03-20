package com.planetnoobs.mandi.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planetnoobs.mandi.main.models.ApiResponse
import com.planetnoobs.mandi.repository.Repository
import com.planetnoobs.mandi.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception
import kotlin.with


@HiltViewModel
class MainViewModel @Inject constructor(private val networkHelper: NetworkHelper,private val repository: Repository) : ViewModel() {

    val mandiList = MutableLiveData<List<ApiResponse.Record>>()
    val progressBarData = MutableLiveData<Boolean>()

    init {
        val params: HashMap<String, Any> = HashMap()
        params["api-key"] = "579b464db66ec23bdd0000013e11d087a89a438f5cad2197f30d6a06"
        params["format"] = "json"
        params["filters"] = ""
        params["limit"] = 20

        getMandiData(params)

    }
     private fun getMandiData(params: HashMap<String, Any>){
        viewModelScope.launch {

            if (networkHelper.isNetworkConnected()) {


            try {
                progressBarData.postValue(true)
                val mandiResponse = repository.getMandiData(params)
                when (mandiResponse.isSuccessful) {
                    true -> {
                        progressBarData.postValue(false)

                        with(mandiResponse.body() != null) {
                            try {
                                Timber.d("Response " + mandiResponse.body())
                                if (mandiResponse.body()?.records != null) {
                                    mandiList.postValue(mandiResponse.body()?.records)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }
                    else -> {
                        progressBarData.postValue(false)
                        mandiList.postValue(emptyArray<ApiResponse.Record>().toList())
                        Timber.e(mandiResponse.message())

                    }
                }
            } catch (e: Exception) {
                progressBarData.postValue(false)
                mandiList.postValue(emptyArray<ApiResponse.Record>().toList())

                e.printStackTrace()
            }

        }
        }
    }
}

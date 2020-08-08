package com.darshan.coroutineexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darshan.coroutineexample.model.Country
import com.darshan.coroutineexample.network.CountryService
import kotlinx.coroutines.*

class ListViewModel : ViewModel() {

    val countries = MutableLiveData<List<Country>>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    val countryService = CountryService.getCountriesService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = countryService.getCountries()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countries.value = response.body()
                    error.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
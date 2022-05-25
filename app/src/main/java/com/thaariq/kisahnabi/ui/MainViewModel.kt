package com.thaariq.kisahnabi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thaariq.kisahnabi.data.KisahResponse
import com.thaariq.kisahnabi.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {

    val kisahResponse = MutableLiveData<List<KisahResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Throwable>()

    private fun getKisahNabi(respondHandler: (List<KisahResponse>)-> Unit, errorHandler: (Throwable) -> Unit){
        ApiClient.getApiService().getKisahNabi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                respondHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getData(){
        isLoading.value = true

        getKisahNabi(
            // Lambda untuk respondHandler
            {
            isLoading.value = true
            kisahResponse.value = it
        }, // Lambda untuk error handler
            {
            isLoading.value = true
            isError.value = it

        })
    }
}
package io.github.alirzaev.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.alirzaev.currencies.utils.CurrencyService
import io.github.alirzaev.currencies.utils.dto.CurrenciesInfo
import io.github.alirzaev.currencies.utils.dto.Currency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrenciesViewModel : ViewModel() {
    private val _currencies = MutableLiveData<List<Currency>>()

    private val _isLoading = MutableLiveData<Boolean>(false)

    private val _toastMessage = MutableLiveData<Int?>()

    val currencies = _currencies as LiveData<List<Currency>>

    val isLoading = _isLoading as LiveData<Boolean>

    val toastMessage = _toastMessage as LiveData<Int?>

    init {
        fetchCurrencies()
    }

    fun toastMessageShown() {
        _toastMessage.value = null
    }

    private fun showToastMessage(message: Int) {
        _toastMessage.value = message
    }

    fun fetchCurrencies() {
        _isLoading.value = true

        CurrencyService.currencyApi.getCurrenciesInfo()
            .enqueue(object : Callback<CurrenciesInfo> {
                override fun onResponse(
                    call: Call<CurrenciesInfo>,
                    response: Response<CurrenciesInfo>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.valute.values.asSequence().sortedWith { a, b ->
                            a.name.compareTo(b.name)
                        }.toList()
                        _currencies.value = data
                    }

                    _isLoading.value = false
                }

                override fun onFailure(call: Call<CurrenciesInfo>, t: Throwable) {
                    _isLoading.value = false
                    showToastMessage(R.string.failed_to_fetch_data)
                }
            })
    }
}
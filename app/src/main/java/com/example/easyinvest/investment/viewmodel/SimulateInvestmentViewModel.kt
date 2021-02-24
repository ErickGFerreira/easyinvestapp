package com.example.easyinvest.investment.viewmodel

import androidx.lifecycle.*
import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.network.model.Error
import com.example.easyinvest.network.reposiories.InvestmentRepository
import com.example.easyinvest.util.SingleLiveEvent
import com.example.easyinvest.util.justDigits
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimulateInvestmentViewModel(
    private val investmentRepository: InvestmentRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) :
    ViewModel() {

    private val DATE_INPUT_SIZE = 10
    private val _simulateInvestmentLiveData = SingleLiveEvent<InvestmentResponse>()
    private val _errorLiveData = SingleLiveEvent<Error>()
    private val _loading = MutableLiveData<Boolean>()
    private val _buttonEnabled = MediatorLiveData<Boolean>()
    private val amountToInvest = MutableLiveData<String>()
    private val dueDate = MutableLiveData<String>()
    private val cdiPercentage = MutableLiveData<String>()


    val simulateInvestmentLiveData: LiveData<InvestmentResponse> = _simulateInvestmentLiveData
    val errorLiveData: LiveData<Error> = _errorLiveData
    val loading: LiveData<Boolean> = _loading
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled


    init {
        _buttonEnabled.addSource(amountToInvest) {
            _buttonEnabled.value = checkInputs()
        }
        _buttonEnabled.addSource(cdiPercentage) {
            _buttonEnabled.value = checkInputs()
        }
        _buttonEnabled.addSource(dueDate) {
            _buttonEnabled.value = checkInputs()
        }

        _buttonEnabled.addSource(loading) {
            _buttonEnabled.value = !it
        }
    }

    fun simulateInvestment() {
        viewModelScope.launch(dispatcher) {
            onLoading(true)
            investmentRepository.simulateInvestiment()
                .onSuccess { investmentResponse ->
                    _simulateInvestmentLiveData.value = investmentResponse
                }
                .onFailure { error ->
                    _errorLiveData.value = error
                }
                .onAny {
                    onLoading(false)
                }
        }

    }

    private fun onLoading(loading: Boolean) {
        _loading.value = loading
    }

    private fun checkInputs(): Boolean {
        return isValidAmountToInvest() && isDateValid()  && isValidCDIPercentage()
    }

    private fun isDateValid(): Boolean {
        dueDate.value?.let { date ->
            return if (date.length == DATE_INPUT_SIZE) {
                val dateArray = date.split("/")
                val day = dateArray[0].toInt()
                val month = dateArray[1].toInt()
                (day in 1..31) && (month in 1..12)
            } else false
        }
        return false
    }

    private fun isValidAmountToInvest(): Boolean{
        amountToInvest.value?.let { amountToInvest ->
            return amountToInvest.justDigits().toInt() > 0
        }
        return false
    }

    private fun isValidCDIPercentage(): Boolean{
        cdiPercentage.value?.let { cdiPercentage ->
            return cdiPercentage.justDigits().toInt() > 0
        }
        return false
    }

    fun updateAmountToInvest(input: String) {
        amountToInvest.value = input
    }

    fun updateDueDate(input: String) {
        dueDate.value = input
    }

    fun updateCDIPercentage(input: String) {
        cdiPercentage.value = input
    }


}


package com.github.flocky

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerAddressVM @Inject constructor() : ViewModel() {

    var uiState = mutableStateOf(UI())
        private set

    init {
        val initialValue = "678507"
        val postalCode = uiState.value.postalCode.copy(postalCode = initialValue)
        uiState.value = uiState.value.copy(postalCode = postalCode)
    }

    fun initValues(cityString: String) {
        val city = uiState.value.city.copy(city = cityString)
        uiState.value = uiState.value.copy(city = city)
    }

    val validationEvent = MutableSharedFlow<UIEvent.ValidationEvent>()

    /**
     * This method will trigger on user interactions and the impact will be state change of the ui.
     */
    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.PostalCodeChanged -> {
                val postalCode =
                    uiState.value.postalCode.copy(postalCode = event.postalCode)
                uiState.value = uiState.value.copy(postalCode = postalCode)
            }

            is UIEvent.PostOfficeChanged -> {
                val postOffice = uiState.value.postOffice.copy(postOffice = event.postOffice)
                uiState.value = uiState.value.copy(postOffice = postOffice)
            }

            is UIEvent.CityChanged -> {
                val city = uiState.value.city.copy(city = event.city)
                uiState.value = uiState.value.copy(city = city)
            }

            is UIEvent.Submit -> {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        val postalCodeResult = Validator.validatePostalCode(uiState.value.postalCode)
        val postalCode =
            uiState.value.postalCode.copy(hasPostalCodeValidationError = !postalCodeResult.status)
        uiState.value = uiState.value.copy(postalCode = postalCode)

        val postOfficeResult = Validator.validatePostOffice(uiState.value.postOffice)

        val postOffice =
            uiState.value.postOffice.copy(hasPostOfficeValidationError = !postOfficeResult.status)
        uiState.value = uiState.value.copy(postOffice = postOffice)

        val cityResult = Validator.validateCity(uiState.value.city)
        val city =
            uiState.value.city.copy(hasCityValidationError = !cityResult.status)
        uiState.value = uiState.value.copy(city = city)

        val hasError = listOf(
            postalCodeResult,
            postOfficeResult,
            cityResult,
        ).any { !it.status }
        viewModelScope.launch {
            if (!hasError) {
                validationEvent.emit(UIEvent.ValidationEvent.Success(msg = SuccessMessageValidation))
            } else {
                validationEvent.emit(UIEvent.ValidationEvent.Failure(msg = ErrorMessageValidation))
            }
        }
    }
}

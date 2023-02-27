package com.github.flocky

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerAddressVM @Inject constructor() : ViewModel() {

    private var _uiState = mutableStateOf(UI())
    val uiState: State<UI> = _uiState

    val validationEvent = MutableSharedFlow<UIEvent.ValidationEvent>()

    /**
     * This method will trigger on user interactions and the impact will be state change of the ui.
     */
    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.PostalCodeChanged -> {
                val postalCode =
                    _uiState.value.postalCode.copy(postalCode = event.postalCode)
                _uiState.value = _uiState.value.copy(postalCode = postalCode)
            }

            is UIEvent.PostOfficeChanged -> {
                val postOffice = _uiState.value.postOffice.copy(postOffice = event.postOffice)
                _uiState.value = _uiState.value.copy(postOffice = postOffice)
            }

            is UIEvent.CityChanged -> {
                val city = _uiState.value.city.copy(city = event.city)
                _uiState.value = _uiState.value.copy(city = city)
            }

            is UIEvent.Submit -> {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        val postalCodeResult = Validator.validateAccountNumber(_uiState.value.postalCode)
        val postalCode =
            _uiState.value.postalCode.copy(hasPostalCodeValidationError = !postalCodeResult.status)
        _uiState.value = _uiState.value.copy(postalCode = postalCode)

        val postOfficeResult = Validator.validatePostOffice(_uiState.value.postOffice)

        val postOffice =
            _uiState.value.postOffice.copy(hasPostOfficeValidationError = !postOfficeResult.status)
        _uiState.value = _uiState.value.copy(postOffice = postOffice)

        val cityResult = Validator.validateCity(_uiState.value.city)
        val city =
            _uiState.value.city.copy(hasCityValidationError = !cityResult.status)
        _uiState.value = _uiState.value.copy(city = city)

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

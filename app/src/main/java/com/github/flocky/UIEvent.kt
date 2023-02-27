package com.github.flocky

sealed class UIEvent {
    data class PostalCodeChanged(val postalCode: String) : UIEvent()
    data class PostOfficeChanged(val postOffice: String) : UIEvent()
    data class CityChanged(val city: String) : UIEvent()
    object Submit : UIEvent()
    sealed class ValidationEvent {
        class Success(val msg: String) : ValidationEvent()
        class Failure(val code: Int = 0, val msg: String) : ValidationEvent()
    }
}

package com.github.flocky

object Validator {

    fun validateAccountNumber(postalCode: PostalCode): ValidationResult {
        return ValidationResult(
            postalCode.value.isNotEmpty() &&
                postalCode.value.length == postalCode.length,
        )
    }

    fun validateCity(city: City): ValidationResult {
        return ValidationResult(city.value.isNotEmpty())
//                && city.value.length >= city.length)
    }

    fun validatePostOffice(postOffice: PostOffice): ValidationResult {
        return ValidationResult(postOffice.value.isNotEmpty())
//                && postOffice.value.length >= postOffice.length)
    }
}

data class ValidationResult(
    val status: Boolean = false,
)

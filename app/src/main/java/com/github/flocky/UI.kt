package com.github.flocky

import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

data class UI(
    val postalCode: PostalCode = PostalCode(),
    val postOffice: PostOffice = PostOffice(),
    val city: City = City(),
    val street: Street = Street(),
    val poviat: Poviat = Poviat(),
    val commune: Commune = Commune(),
    val buildingNumber: BuildingNumber = BuildingNumber(),
    val voivodeship: Voivodeship = Voivodeship(),
    val locumNumber: LocumNumber = LocumNumber(),
)

sealed class ComponentProps(
    var value: String = "",
    val label: String = "",
    val length: Int = 0,
    val keyboardType: KeyboardType,
    val keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    val hasValidationError: Boolean = false,
)

data class PostalCode(
    val postalCode: String = "",
    val postalCodeLabel: String = defaultPostalCodeLabel,
    val postalCodeLength: Int = 6,
    val postalCodeKeyboardType: KeyboardType = KeyboardType.Number,
    val hasPostalCodeValidationError: Boolean = false,
) : ComponentProps(
    postalCode,
    postalCodeLabel,
    postalCodeLength,
    postalCodeKeyboardType,
    hasValidationError = hasPostalCodeValidationError,
)

data class PostOffice(
    val postOffice: String = "",
    val postOfficeLabel: String = defaultPostOfficeLabel,
    val postOfficeLength: Int = 6,
    val postOfficeKeyboardType: KeyboardType = KeyboardType.Text,
    val postOfficeKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    val hasPostOfficeValidationError: Boolean = false,
) : ComponentProps(
    postOffice,
    postOfficeLabel,
    postOfficeLength,
    postOfficeKeyboardType,
    postOfficeKeyboardCapitalization,
    hasValidationError = hasPostOfficeValidationError,
)

data class City(
    val city: String = "",
    val cityLabel: String = defaultCityLabel,
    val cityLength: Int = 5,
    val cityKeyboardType: KeyboardType = KeyboardType.Text,
    val cityKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasCityValidationError: Boolean = false,
) : ComponentProps(
    city,
    cityLabel,
    cityLength,
    cityKeyboardType,
    cityKeyboardCapitalization,
    hasValidationError = hasCityValidationError,
)

data class Street(
    val street: String = "",
    val streetLabel: String = "streetLabel",
    val streetLength: Int = 10,
    val streetKeyboardType: KeyboardType = KeyboardType.Text,
    val streetKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasStreetValidationError: Boolean = false,
) : ComponentProps(
    street,
    streetLabel,
    streetLength,
    streetKeyboardType,
    streetKeyboardCapitalization,
    hasValidationError = hasStreetValidationError,
)

data class BuildingNumber(
    val buildingNumber: String = "",
    val buildingNumberLabel: String = "buildingNumberLabel",
    val buildingNumberLength: Int = 10,
    val buildingNumberKeyboardType: KeyboardType = KeyboardType.Text,
    val buildingNumberKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasbuildingNumberValidationError: Boolean = false,
) : ComponentProps(
    buildingNumber,
    buildingNumberLabel,
    buildingNumberLength,
    buildingNumberKeyboardType,
    buildingNumberKeyboardCapitalization,
    hasValidationError = hasbuildingNumberValidationError,
)

data class LocumNumber(
    val locumNumber: String = "",
    val locumNumberLabel: String = "locumNumberLabel",
    val locumNumberLength: Int = 10,
    val locumNumberKeyboardType: KeyboardType = KeyboardType.Text,
    val locumNumberKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasLocumNumberValidationError: Boolean = false,
) : ComponentProps(
    locumNumber,
    locumNumberLabel,
    locumNumberLength,
    locumNumberKeyboardType,
    locumNumberKeyboardCapitalization,
    hasValidationError = hasLocumNumberValidationError,
)

data class Commune(
    val commune: String = "",
    val communeLabel: String = "communeLabel",
    val communeLength: Int = 10,
    val communeKeyboardType: KeyboardType = KeyboardType.Text,
    val communeKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasCommuneValidationError: Boolean = false,
) : ComponentProps(
    commune,
    communeLabel,
    communeLength,
    communeKeyboardType,
    communeKeyboardCapitalization,
    hasValidationError = hasCommuneValidationError,
)

data class Poviat(
    val poviat: String = "",
    val poviatLabel: String = "poviatLabel",
    val poviatLength: Int = 10,
    val poviatKeyboardType: KeyboardType = KeyboardType.Text,
    val poviatKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasPoviatValidationError: Boolean = false,
) : ComponentProps(
    poviat,
    poviatLabel,
    poviatLength,
    poviatKeyboardType,
    poviatKeyboardCapitalization,
    hasValidationError = hasPoviatValidationError,
)

data class Voivodeship(
    val voivodeship: String = "",
    val voivodeshipLabel: String = "voivodeshipLabel",
    val voivodeshipLength: Int = 10,
    val voivodeshipKeyboardType: KeyboardType = KeyboardType.Text,
    val voivodeshipKeyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
    val hasvoivodeshipValidationError: Boolean = false,
) : ComponentProps(
    voivodeship,
    voivodeshipLabel,
    voivodeshipLength,
    voivodeshipKeyboardType,
    voivodeshipKeyboardCapitalization,
    hasValidationError = hasvoivodeshipValidationError,
)

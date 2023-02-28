package com.github.flocky

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.flocky.ui.theme.FlockyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FlockyTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val c = LocalContext.current
    LaunchedEffect(Unit) {
        Toast.makeText(c, "Welcome!", Toast.LENGTH_LONG).show()
    }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Customer Address") },
                backgroundColor = MaterialTheme.colors.primary,
            )
        },
        scaffoldState = scaffoldState,
        content = {
            UIScreen(scaffoldState)
        },
    )
}

@Composable
fun UIScreen(scaffoldState: ScaffoldState) {
    val customerAddressViewModel = viewModel(modelClass = CustomerAddressVM::class.java)
    customerAddressViewModel.initValues("Jakarta")
    val postalCode by remember {
        mutableStateOf(customerAddressViewModel.uiState.value.postalCode)
    }

    val postOffice by remember {
        mutableStateOf(customerAddressViewModel.uiState.value.postOffice)
    }

    val city by remember {
        mutableStateOf(customerAddressViewModel.uiState.value.city)
    }

    val localFocus = LocalFocusManager.current
    LaunchedEffect(key1 = scaffoldState) {
        customerAddressViewModel.validationEvent.collect { event ->
            when (event) {
                is UIEvent.ValidationEvent.Success -> {
                    showSnackWrapper(scaffoldState, event.msg)
                }

                is UIEvent.ValidationEvent.Failure -> {
                    showSnackWrapper(scaffoldState, event.msg)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AddressUIComponent(
                    postalCode,
                    onTextChanged = {
                        customerAddressViewModel.onEvent(UIEvent.PostalCodeChanged(it))
                    },
                    isError = postalCode.hasPostalCodeValidationError,
                    onNext = {
                        localFocus.moveFocus(FocusDirection.Down)
                    },
                    onDone = {
                    },
                )
                AddressUIComponent(
                    postOffice,
                    onTextChanged = {
                        customerAddressViewModel.onEvent(UIEvent.PostOfficeChanged(it))
                    },
                    isError = postOffice.hasPostOfficeValidationError,
                    onNext = {
                        localFocus.moveFocus(FocusDirection.Down)
                    },
                    onDone = {
                    },
                )
                AddressUIComponent(
                    city,
                    onTextChanged = {
                        customerAddressViewModel.onEvent(UIEvent.CityChanged(it))
                    },
                    isError = city.hasCityValidationError,
                    onNext = {
                        localFocus.moveFocus(FocusDirection.Down)
                    },
                    onDone = {
                    },
                )
                Button(
                    modifier = Modifier.padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .height(45.dp),
                    onClick = {
                        customerAddressViewModel.onEvent(UIEvent.Submit)
                    },
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

suspend fun showSnackWrapper(scaffoldState: ScaffoldState, msg: String) {
    scaffoldState.snackbarHostState.showSnackbar(
        msg,
        "Dismiss Me!",
    )
}

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { },
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2,
                    )
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = actionLabel,
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                },
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom),
    )
}

@Composable
@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
fun AppPreview() {
    FlockyTheme {
        // A surface container using the 'background' color from the theme

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            App()
        }
    }
}

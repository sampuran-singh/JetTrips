package com.example.jettrips.ui.screens.onboarding


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrips.R
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.utils.JetTripsButton
import com.example.jettrips.utils.JetTripsTextField
import com.example.jettrips.utils.JetTripsTopAppBar

@Composable

fun LoginPhoneScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: (String) -> Unit,
    obBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            JetTripsTopAppBar(onBackClick = {
                obBackClicked()
            })
        },
        content = { innerPadding ->
            LoginPhoneScreenContent(Modifier.padding(innerPadding), onContinueClicked)
        }
    )
}


@Composable
fun LoginPhoneScreenContent(modifier: Modifier = Modifier, onContinueClicked: (String) -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var isValidPhone by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCountryCode by remember { mutableStateOf(Pair("+91", R.drawable.flag_india)) }

    val countryCodes =
        listOf<Pair<String, Int>>(
            Pair("+1", R.drawable.flag_us),
            Pair("+44", R.drawable.flag_uk),
            Pair("+91", R.drawable.flag_india),
        )

    fun validatePhone(phone: String): Boolean {
        return phone.length == 10 && phone.all { it.isDigit() }
    }

    LaunchedEffect(phoneNumber) {
        isValidPhone = validatePhone(phoneNumber)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_welcome_title),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.login_welcome_desc),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { expanded = true }
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            border = BorderStroke(1.dp, Color.DarkGray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = selectedCountryCode.second),
                        contentDescription = stringResource(id = R.string.login_language_icon),
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = selectedCountryCode.first,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                DropdownMenu(
                    modifier = Modifier,
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    countryCodes.forEach { code ->
                        DropdownMenuItem(
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = code.second),
                                    contentDescription = stringResource(id = R.string.login_language_icon),
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            },
                            text = { Text(text = code.first, color = Color.Black) },
                            onClick = {
                                selectedCountryCode = code
                                expanded = false
                            },
                            modifier = Modifier.background(Color.White)
                        )
                    }
                }
            }

            JetTripsTextField(
                value = phoneNumber,
                onValueChange = { newValue ->
                    if (newValue.length <= 10) {
                        phoneNumber = newValue
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.login_enter_phone_number),
                        color = Color.Black
                    )
                },
                placeholder = {
                    Text(
                        stringResource(id = R.string.login_enter_phone_number_placeholder),
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = phoneNumber.isNotEmpty() && !isValidPhone
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        JetTripsButton(
            text = stringResource(id = R.string.email_button_continue),
            enabled = isValidPhone
        ) { onContinueClicked(phoneNumber) }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPhoneScreenPreview() {
    JetTripsTheme {
        LoginPhoneScreen(Modifier, {}, {})
    }
}
package com.example.jettrips.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrips.R
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.utils.NotificationUtils

@Composable
fun OtpScreen(
    modifier: Modifier,
    phoneNumber: String,
    onContinueClicked: () -> Unit
) {
    var isButtonEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current.applicationContext

    LaunchedEffect(phoneNumber) {
        val some = NotificationUtils.getNotificationBuilder(context)
        NotificationUtils.notify(context, some.build())
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 50.dp, horizontal = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.otp_sign_in),
            textAlign = TextAlign.Start,
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.otp_sign_in_desc, phoneNumber),
            textAlign = TextAlign.Start,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        OTPView() {
            if (it.isNotBlank()) {
                isButtonEnabled = true
            } else {
                isButtonEnabled = false
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onContinueClicked() },
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white),
                disabledContainerColor = colorResource(id = R.color.grey),
                disabledContentColor = colorResource(id = R.color.grey)
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.email_button_continue),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun OTPView(onOTPComplete: (String) -> Unit) {
    val otpValues = remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = List(6) { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        otpValues.value.forEachIndexed { index, value ->
            OTPTextField(
                modifier = Modifier.focusRequester(focusRequesters[index]),
                value = value,
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        otpValues.value = otpValues.value.toMutableList().also {
                            it[index] = newValue
                        }
                        if (newValue.isNotEmpty() && index < 5) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (otpValues.value.all { it.isNotEmpty() }) {
                            onOTPComplete(otpValues.value.joinToString(""))
                        }
                    }
                    if (newValue.isEmpty() && index > 0) {
                        focusRequesters[index - 1].requestFocus()
                    }
                }
            )
        }
    }
}

@Composable
fun OTPTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .width(50.dp)
            .height(60.dp)
            .border(1.dp, Color.Black, MaterialTheme.shapes.small),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedIndicatorColor = Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None
    )
}

@Preview
@Composable
fun PreviewOTPView() {
    JetTripsTheme {
        OTPTextField(Modifier, "1") {}
    }
}


@Preview()
@Composable
fun OtpScreenPreview() {
    JetTripsTheme(darkTheme = true) {
        OtpScreen(Modifier, "8990909090") {}
    }
}

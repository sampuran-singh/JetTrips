package com.example.jettrips.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettrips.R
import com.example.jettrips.ui.theme.JetTripsTheme

@Composable
fun LoginScreen(
    modifier: Modifier,
    email: String = "sample@gmail.com",
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 50.dp, horizontal = 10.dp)
    ) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }

        Text(
            text = stringResource(id = R.string.login_tell_us_name),
            textAlign = TextAlign.Start,
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.login_tell_us_name_description, email),
            textAlign = TextAlign.Start,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = firstName,
            onValueChange = { newText -> firstName = newText },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text(text = stringResource(id = R.string.login_hint_first_name)) },
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = lastName,
            onValueChange = { newText -> lastName = newText },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text(text = stringResource(id = R.string.login_hint_last_name)) },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onContinueClicked() },
            enabled = firstName.isNotBlank() && lastName.isNotBlank(), // Enable button only if the email is valid
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

@Preview
@Composable
fun LoginScreenPreview() {
    JetTripsTheme {
        LoginScreen(Modifier, "sample@gmail.com") {}
    }
}
package com.example.jettrips.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettrips.R
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.ui.theme.sanComicsFontFamily
import com.example.jettrips.utils.isValidEmail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(modifier: Modifier, onContinueClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 50.dp, horizontal = 10.dp)
    ) {
        var text by remember { mutableStateOf("") }
        var isFocused by remember { mutableStateOf(false) }
        var isEmailValid by remember { mutableStateOf(false) }

        // Update email validity whenever the text changes
        isEmailValid = isValidEmail(text)


        Text(
            text = stringResource(id = R.string.onboarding_enter_email),
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.onboarding_enter_email_description),
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(id = R.string.email),
            textAlign = TextAlign.Start,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                isEmailValid = isValidEmail(newText) // Update validity when text changes
            },
            label = { Text(text = stringResource(id = R.string.email_label)) },
            placeholder = { Text(text = stringResource(id = R.string.email_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = if (isFocused) Color.Blue else Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White, //hide the indicator
                unfocusedIndicatorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onContinueClicked() },
            enabled = isEmailValid, // Enable button only if the email is valid
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
fun OnboardingScreenPreview() {
    JetTripsTheme {
        OnboardingScreen(Modifier) {}
    }
}
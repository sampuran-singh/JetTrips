package com.example.jettrips.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jettrips.R
import com.example.jettrips.utils.TermsAndPrivacyText
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(modifier: Modifier, onLogInClicked: () -> Unit, onSignUpClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 16.dp, horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_jettrips),
                contentDescription = "Category image",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
            Text(
                text = "JetTrips",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_language),
                contentDescription = "Language icon",
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                text = "English",
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(
            modifier = modifier
                .height(40.dp)
                .weight(1f)
        )
        SliderBanner()
        Text(
            text = stringResource(id = R.string.welcome_to_jettrips),
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.welcome_to_jettrips_desc),
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(
            modifier = modifier
                .height(8.dp)
                .weight(1f)
        )
        Button(
            onClick = { onLogInClicked() },
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
                text = stringResource(id = R.string.button_welcome_login),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Button(
            onClick = { onSignUpClicked() },
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
                text = stringResource(id = R.string.button_welcome_signup),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(
            modifier = modifier
                .height(8.dp)
        )
        TermsAndPrivacyText()
    }
}


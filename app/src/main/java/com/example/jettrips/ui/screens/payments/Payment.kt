import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.concurrent.TimeUnit

@Composable
fun PaymentDetailPage(modifier: Modifier, onPaymentComplete: () -> Unit) {
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvcNumber by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

    val isFormValid by remember {
        derivedStateOf {
            cardNumber.length == 16 &&
                    cardHolderName.isNotBlank() &&
                    expiryDate.length == 5 && // expecting MM/YY format
                    cvcNumber.length == 3 &&
                    termsAccepted
        }
    }

    val context = LocalContext.current
    var timeLeft by remember { mutableStateOf(600000L) } // 10 minutes in milliseconds

    // Timer
    LaunchedEffect(key1 = timeLeft) {
        object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                timeLeft = 0L
                // Handle time expiration logic here
            }
        }.start()
    }

    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeft) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeft) % 60

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Timer with "We are holding price"
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "We are holding price for ", fontSize = 14.sp)
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Payment Method", style = MaterialTheme.typography.headlineMedium)

        // Card Number Field with validation
        OutlinedTextField(
            value = cardNumber,
            onValueChange = {
                if (it.length <= 16) cardNumber = it
            },
            label = { Text(text = "Credit/Debit Card Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PasswordVisualTransformation()
        )
        if (cardNumber.length != 16 && cardNumber.isNotEmpty()) {
            Text(
                text = "Invalid card number. Must be 16 digits.",
                color = MaterialTheme.colorScheme.error
            )
        }

        // Card Holder Name
        OutlinedTextField(
            value = cardHolderName,
            onValueChange = { cardHolderName = it },
            label = { Text(text = "Card Holder Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Expiry Date Field (MM/YY)
        OutlinedTextField(
            value = expiryDate,
            onValueChange = {
                if (it.length == 2 && !it.contains("/")) {
                    expiryDate = "$it/"
                } else if (it.length <= 5) {
                    expiryDate = it
                }
            },
            label = { Text(text = "Expiry Date (MM/YY)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (expiryDate.length != 5 && expiryDate.isNotEmpty()) {
            Text(
                text = "Invalid expiry date. Must be MM/YY format.",
                color = MaterialTheme.colorScheme.error
            )
        }

        // CVC Number Field
        OutlinedTextField(
            value = cvcNumber,
            onValueChange = {
                if (it.length <= 3) cvcNumber = it
            },
            label = { Text(text = "CVC Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PasswordVisualTransformation()
        )
        if (cvcNumber.length != 3 && cvcNumber.isNotEmpty()) {
            Text(text = "Invalid CVC. Must be 3 digits.", color = MaterialTheme.colorScheme.error)
        }

        // Terms and Conditions Checkbox
        Row {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it }
            )
            Text(
                text = "I accept the ",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.clickable { /* Handle onClick */ }
            )
            Text(
                text = "Terms and Conditions",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.clickable { /* Open Terms and Conditions */ },
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = " and ",
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = "Privacy Policy",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.clickable { /* Open Privacy Policy */ },
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Book Now Button
        Button(
            onClick = {
                Toast.makeText(context, "Booking Successful!", Toast.LENGTH_LONG).show()
                onPaymentComplete()
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Book Now")
        }
    }
}

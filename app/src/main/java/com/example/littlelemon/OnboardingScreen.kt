package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val context = LocalContext.current

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
        val sharedPrefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putString("first_name", firstName)
            putString("last_name", lastName)
            putString("email", email)
            putBoolean("is_user_onboarded", true)
            apply()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.padding(30.dp)
        )
        Text(
            text = "Let's get to know you",
            modifier = Modifier.padding(16.dp, 0.dp)
        )

        OutlinedTextField(
            value = firstName,
            label = {
                Text("First name")
            },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { firstName = it },
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastName,
            label = {
                Text("Last name")
            },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { lastName = it },
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            label = {
                Text("Email address")
            },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email = it },
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            colors = ButtonDefaults.buttonColors(Color(0xFF495E57)),
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    message = "Registration unsuccessful. Please enter all data."
                } else {
                    message = "Registration successful!"

                    saveUserData(context, firstName, lastName, email)

                    navController.navigate("Home") {

                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.startsWith("Registration successful")) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    OnboardingScreen(navController = NavHostController(LocalContext.current))
}



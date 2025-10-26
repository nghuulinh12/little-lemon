package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current

    val firstName = SharedPrefsHelper.getFirstName(context) ?: "Unknown"
    val lastName = SharedPrefsHelper.getLastName(context) ?: "Unknown"
    val email = SharedPrefsHelper.getEmail(context) ?: "Unknown"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp, 0.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.padding(30.dp)
        )
        Text(
            text = "Profile information:",
            modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = firstName,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 0.dp),
            onValueChange = { },
        )
        OutlinedTextField(
            value = lastName,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 0.dp),
            onValueChange = { },
        )
        OutlinedTextField(
            value = email,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 0.dp),
            onValueChange = { },
        )


        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                SharedPrefsHelper.clearData(context)

                // Điều hướng về Onboarding và clear backstack
                navController.navigate("Onboarding") {
                    popUpTo("Home") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF495E57)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = NavHostController(LocalContext.current))
}
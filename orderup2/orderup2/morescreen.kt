package com.example.orderup2

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MoreScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // My Account
        Button(
            onClick = { navController.navigate("myAccount") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("My Account")
        }

        // FAQ
        Button(
            onClick = { navController.navigate("faq") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("FAQ")
        }

        // Feedback
        Button(
            onClick = { navController.navigate("feedback") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Feedback")
        }

        // Ts & Cs - Fix navigation route to match composable name
        Button(
            onClick = { navController.navigate("tsCs") }, // Changed from "terms" to "tsCs"
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Ts & Cs")
        }

        // Privacy Policy - Fix navigation route to match composable name
        Button(
            onClick = { navController.navigate("privacyPolicy") }, // Changed from "privacy" to "privacyPolicy"
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Privacy Policy")
        }

        // About Order Up
        Button(
            onClick = { navController.navigate("aboutUs") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("About Order Up")
        }

        // UMP Official Website
        WebsiteLinkButton()
    }
}

//1. My Account screen
@Composable
fun MyAccountScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "My Account",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = { /* Handle personal details action */ },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Personal Details")
        }

        Button(
            onClick = { /* Handle password and security action */ },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Password and Security")
        }

        Button(
            onClick = { /* Handle login/logout */ },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Login / Logout")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316))
        ) {
            Text("Back")
        }
    }
}


//2. Feedback screen
@Composable
fun FeedbackScreen(navController: NavHostController) {
    var feedbackText by remember { mutableStateOf("") }
    val isFeedbackSent by remember { mutableStateOf(false) } // For a success message

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Feedback",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Corrected usage of OutlinedTextFieldDefaults.colors
        OutlinedTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            label = { Text("Your Feedback", color = Color.White) },
            modifier = Modifier.fillMaxWidth().height(200.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                unfocusedBorderColor = Color(0xFFF97316),
                focusedBorderColor = Color(0xFFF97316),
                cursorColor = Color(0xFFF97316),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /* Handle sending feedback, e.g., to a backend or email */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Submit Feedback")
        }

        if (isFeedbackSent) {
            Text(
                "Thank you for your feedback!",
                color = Color.Green,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316))
        ) {
            Text("Back")
        }
    }
}


//3. FaQs screen


@Composable
fun FaqScreen(navController: NavHostController) {
    // Implement your FAQ content here
    SimpleTextScreen("FAQ", navController)
}

@Composable
fun TermsScreen(navController: NavHostController) {
    // Implement your Ts&Cs content here
    SimpleTextScreen("Terms & Conditions", navController)
}

@Composable
fun PrivacyScreen(navController: NavHostController) {
    // Implement your privacy policy here
    SimpleTextScreen("Privacy Policy", navController)
}

@Composable
fun AboutUsScreen(navController: NavHostController) {
    // Implement your "About Order Up" content here
    SimpleTextScreen("About Order Up", navController)
}

@Composable
fun SimpleTextScreen(title: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Add your detailed text content here
        Text("Content for $title... and ", color = Color.Gray)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316))
        ) {
            Text("Back")
        }
    }
}

// For the website link
@Composable
fun WebsiteLinkButton() {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ump.ac.za"))
            context.startActivity(intent)
        },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
    ) {
        Text("UMP Official Website")
    }
}


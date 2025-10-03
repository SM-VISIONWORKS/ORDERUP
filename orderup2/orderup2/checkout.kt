package com.example.orderup2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CheckoutPage(navController: NavHostController, cart: MutableList<CartItem>) {
    var selectedPaymentOption by remember { mutableStateOf("Google Pay Demo") } // Default to Google Pay Demo
    val coroutineScope = rememberCoroutineScope()

    // Calculate total price for display
    val totalPrice = remember(cart.size, cart.sumOf { it.quantity }) {
        cart.sumOf { it.price * it.quantity }
    }

    // Simplified demo function for payment simulation
    val processPayment: () -> Unit = {
        coroutineScope.launch {
            // Simulate processing time
            delay(1500)
            // Clear cart upon successful "order"
            cart.clear()
            // Navigate to track order screen
            navController.navigate("track") {
                // Clear the back stack up to home (or menu/cart) but not including track
                popUpTo("home") { inclusive = false }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(16.dp)
    ) {
        Text("Checkout",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text("Select Payment Option:", color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            // Updated options: removed "Cash on Delivery"
            RadioButtonWithLabel("Google Pay Demo", selectedPaymentOption == "Google Pay Demo") {
                selectedPaymentOption = "Google Pay Demo"
            }
            RadioButtonWithLabel("Mobile Payment", selectedPaymentOption == "Mobile Payment") {
                selectedPaymentOption = "Mobile Payment"
            }
        }

        // Display Total Price
        Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Order Total:", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("R$totalPrice", color = Color(0xFFF97316), fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
        Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))


        Spacer(modifier = Modifier.weight(1f)) // Push back button to bottom

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("cart") },
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White)

            ) { Text("Back to Cart") }

            Button(
                onClick = { processPayment() }, // Use the simulation function
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316)),
                enabled = cart.isNotEmpty() // Disable if cart is empty
            ) {
                if (selectedPaymentOption == "Google Pay Demo") {
                    Text("Pay R$totalPrice with Google Pay (Demo)")
                } else {
                    Text("Place Order")
                }
            }
        }
        // Demo message for the user
        Text(
            text = "Note: This is a demo payment. Clicking 'Pay' will simulate a successful order and navigate to 'Track Order'.",
            color = Color.Yellow,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


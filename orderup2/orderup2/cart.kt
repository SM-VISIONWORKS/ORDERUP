package com.example.orderup2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CartPage(navController: NavHostController, cart: MutableList<CartItem>) {
    // Calculate total price
    val totalPrice = remember(cart.size, cart.sumOf { it.quantity }) {
        cart.sumOf { it.price * it.quantity }
    }

    // Function to remove a single item from the cart
    val removeItem: (CartItem) -> Unit = { itemToRemove ->
        cart.remove(itemToRemove)
    }

    // Function to clear the entire cart
    val removeAllItems: () -> Unit = {
        cart.clear()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(16.dp)
    ) {
        Text("Your Cart",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cart.isEmpty()) {
            Text(
                "Your cart is empty. Add some delicious food!",
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cart) { item: CartItem ->
                    CartItemRow(item, removeItem)
                }
            }
        }

        // Horizontal Line
        Divider(color = Color.DarkGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))

        // Total Price Display
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total:", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("R$totalPrice", color = Color(0xFFF97316), fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Footer Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { navController.navigate("menu") },
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Menu")
            }

            Button(
                onClick = { navController.navigate("checkout") },
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF97316),
                    contentColor = Color.White
                ),
                enabled = cart.isNotEmpty(),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Checkout")
            }
        }

        // Remove All Button (as requested 'removing the order')
        if (cart.isNotEmpty()) {
            Button(
                onClick = removeAllItems,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Remove All Items (Cancel Order)")
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onRemove: (CartItem) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.name,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    "R${item.price} x ${item.quantity}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Text(
                "R${item.price * item.quantity}",
                color = Color(0xFFF97316),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Button(
                onClick = { onRemove(item) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(horizontal = 10.dp),
                modifier = Modifier.height(35.dp)
            ) {
                Text("Remove", fontSize = 12.sp)
            }
        }
    }
}
package com.example.orderup2

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MenuPage(navController: NavHostController) {
    val categories = listOf(
        "Main Meals" to R.drawable.mainmeals,
        "Sides" to R.drawable.sides,
        "Snacks" to R.drawable.snacks ,
        "Drinks" to R.drawable.drinks,
        "Bakery" to R.drawable.bakery,
        "Desserts" to R.drawable.dessert)

    // State for the search query
    var searchQuery by remember { mutableStateOf("") }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(16.dp)
    ) {
        // 🔍 Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it
                if (it.isNotBlank()) {
                    // Navigate immediately as text changes (optional) or let the user manually navigate
                    // For now, removing search trigger logic entirely as requested.
                }
            },
            label = { Text("Search Menu Items...", color = Color.White.copy(alpha = 0.7f)) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear Search", tint = Color.White)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            // FIX: Removed keyboardOptions and keyboardActions entirely as requested.
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                unfocusedBorderColor = Color(0xFFF97316),
                focusedBorderColor = Color(0xFFF97316),
                cursorColor = Color(0xFFF97316),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            )
        )

        Text(
            "Select a Category",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(categories) { (category, imageRes) ->
                CategoryCard(category, imageRes) {
                    navController.navigate("category/$category")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("home") },
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black,
                    contentColor = Color.White)
            ) { Text("Back") }


            Button(onClick = { navController.navigate("cart")},
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black,
                    contentColor = Color.White)
            ) { Text("View Cart") }
        }
    }
}


@Composable
fun CategoryCard(name: String, imageRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
    }
}
// -------------------- Category Page --------------------
@Composable
fun CategoryPage(
    navController: NavHostController,
    category: String,
    cart: MutableList<CartItem> // Cart is now MutableList<CartItem>
) {
    val items: List<MenuItem> = getCategoryItems(category)

    // Function to add/update item in cart
    val updateCart: (MenuItem, Int) -> Unit = { item, newQuantity ->
        val existingItem = cart.find { it.name == item.name }
        if (existingItem != null) {
            if (newQuantity > 0) {
                existingItem.quantity = newQuantity
            } else {
                cart.remove(existingItem)
            }
        } else if (newQuantity > 0) {
            cart.add(CartItem(item.name, item.price, newQuantity))
        }
        // Force recomposition for mutableStateListOf
        cart.forEach { }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            "$category Menu",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                // Check if item is already in cart to display current quantity
                val currentQuantity = cart.find { it.name == item.name }?.quantity ?: 0
                FoodItemDetailCard(
                    item = item,
                    initialQuantity = currentQuantity,
                    onQuantityChange = { newQuantity ->
                        updateCart(item, newQuantity)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Footer with styled buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { navController.navigate("menu") },
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }

            Button(
                onClick = { navController.navigate("cart") },
                border = BorderStroke(2.dp, Color(0xFFF97316)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("View Cart")
            }
        }
    }
}


// NEW: Search Results Screen
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchQuery: String,
    cart: MutableList<CartItem>
) {
    // Decode the URL-encoded query
    val decodedQuery = remember { Uri.decode(searchQuery).lowercase() }

    // Filter items based on the search query
    val searchResults = remember(decodedQuery) {
        if (decodedQuery.isBlank()) {
            emptyList()
        } else {
            allMenuItems.filter { item ->
                item.name.lowercase().contains(decodedQuery)
            }.distinctBy { it.name } // Remove duplicates if any were accidentally introduced
        }
    }

    // Function to add/update item in cart (re-used logic from CategoryPage)
    val updateCart: (MenuItem, Int) -> Unit = { item, newQuantity ->
        val existingItem = cart.find { it.name == item.name }
        if (existingItem != null) {
            if (newQuantity > 0) {
                existingItem.quantity = newQuantity
            } else {
                cart.remove(existingItem)
            }
        } else if (newQuantity > 0) {
            cart.add(CartItem(item.name, item.price, newQuantity))
        }
        cart.forEach { }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            "Search Results for: \"$decodedQuery\"",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (searchResults.isEmpty()) {
            Text(
                "No items found matching \"$decodedQuery\".",
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 30.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(searchResults) { item ->
                    val currentQuantity = cart.find { it.name == item.name }?.quantity ?: 0
                    FoodItemDetailCard(
                        item = item,
                        initialQuantity = currentQuantity,
                        onQuantityChange = { newQuantity ->
                            updateCart(item, newQuantity)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.popBackStack() },
            border = BorderStroke(2.dp, Color(0xFFF97316)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Menu")
        }
    }
}


// Updated Card to include quantity selector
@Composable
fun FoodItemDetailCard(item: MenuItem, initialQuantity: Int, onQuantityChange: (Int) -> Unit) {
    var quantity by remember { mutableStateOf(initialQuantity) }

    // Sync external changes (like navigating back from cart)
    LaunchedEffect(initialQuantity) {
        quantity = initialQuantity
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    "R${item.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF97316)
                )
            }
            QuantitySelector(quantity) { newQuantity ->
                quantity = newQuantity
                onQuantityChange(newQuantity)
            }
        }
    }
}


@Composable
fun QuantitySelector(quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = { if (quantity > 0) onQuantityChange(quantity - 1) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.size(35.dp)
        ) { Text("-", color = Color.White) }

        Text(
            quantity.toString(),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Button(
            onClick = { onQuantityChange(quantity + 1) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316)),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.size(35.dp)
        ) { Text("+", color = Color.White) }
    }
}

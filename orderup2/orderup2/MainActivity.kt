package com.example.orderup2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MoreHoriz


// New data class to hold item details and quantity
data class CartItem(val name: String, val price: Int, var quantity: Int)
data class NavItem(val route: String, val label: String, val icon: ImageVector)
data class MenuItem(val name: String, val price: Int) // Used for defining category items

// -------------------- Main Activity --------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeteriaApp()
        }
    }
}

// Global list of all available menu items for search functionality
// This is a simplified, non-reactive way to hold all menu items
val allMenuItems: List<MenuItem> = getCategoryItems("Main Meals") +
        getCategoryItems("Sides") +
        getCategoryItems("Snacks") +
        getCategoryItems("Drinks") +
        getCategoryItems("Bakery") +
        getCategoryItems("Desserts")


// Helper function to get menu items based on category (moved out of composable)
fun getCategoryItems(category: String): List<MenuItem> {
    return when (category) {
        "Main Meals" -> listOf(
            MenuItem("Chicken Burger", 50), MenuItem("Chicken Wrap", 50),
            MenuItem("Beef Wrap", 45), MenuItem("Chicken-Mayo Sandwich", 25),
            MenuItem("Kota", 30), MenuItem("Classic Sandwich", 20),
            MenuItem("Hot-Dog", 25)
        )
        "Sides" -> listOf(
            MenuItem("Small Fries", 25), MenuItem("Medium Fries", 35),
            MenuItem("Large Fries", 55)
        )
        "Snacks" -> listOf(
            MenuItem("Doritos", 22), MenuItem("Lays", 22),
            MenuItem("Simba Chips", 22), MenuItem("Popcorn", 18)
        )
        "Drinks" -> listOf(
            MenuItem("Coca-cola 500ml", 15), MenuItem("Sprite 500ml", 15),
            MenuItem("Fanta-Orange 500ml", 15), MenuItem("Stone-ginger 500ml", 15),
            MenuItem("Coca-cola 2l", 25), MenuItem("Sprite 2l", 25),
            MenuItem("Fanta-Orange 2l", 25), MenuItem("Stone-ginger 2l", 25),
            MenuItem("Juice-box", 12), MenuItem("Water", 12),
            MenuItem("Redbull", 18), MenuItem("Powerade", 18),
            MenuItem("Monster", 18), MenuItem("Coffee", 15),
            MenuItem("Tea", 15)
        )
        "Bakery" -> listOf(
            MenuItem("Muffin", 15), MenuItem("Pie", 30),
            MenuItem("Donut", 10), MenuItem("Chocolate-chip cookie", 10)
        )
        "Desserts" -> listOf(
            MenuItem("Cake slice-chocolate", 25), MenuItem("Cake slice-vanilla", 25),
            MenuItem("Cheese-Cake slice", 27), MenuItem("Chocolate cupcake", 17),
            MenuItem("Vanilla cupcake", 17)
        )
        else -> emptyList()
    }
}


// -------------------- Root App Setup --------------------
@Composable
fun CafeteriaApp() {
    val navController = rememberNavController()
    // The cart state is updated to store CartItem objects
    val cart = remember { mutableStateListOf<CartItem>() }

    // Define bottom nav items with routes, labels and icons
    val bottomNavItems = listOf(
        NavItem("home", "Home", Icons.Filled.Home),
        NavItem("menu", "Order", Icons.Filled.Fastfood),
        NavItem("cart", "Cart", Icons.Filled.ShoppingCart),
        NavItem("track", "Track Order", Icons.Filled.DirectionsRun),
        NavItem("more", "More", Icons.Filled.MoreHoriz)
    )

    // Observe navigation back stack entry to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showScaffold = currentRoute != "splash" // Show scaffold unless on splash

    if (showScaffold) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEach { item->
                        NavigationBarItem(
                            icon = {
                                Box {
                                    Icon(item.icon, contentDescription = item.label)
                                    // Display cart item count badge
                                    if (item.route == "cart" && cart.sumOf { it.quantity } > 0) {
                                        Text(
                                            text = cart.sumOf { it.quantity }.toString(),
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .offset(x = 6.dp, y = (-6).dp)
                                                .background(Color.Red, RoundedCornerShape(50))
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            },
            containerColor = Color.Black
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("splash") { SplashScreen(navController) }
                composable("home") { HomePage(navController) }
                composable("menu") { MenuPage(navController) }
                composable("track") { TrackOrderScreenPlaceholder() }
                composable("more") { MoreScreen(navController) }
                composable("category/{name}") { backStackEntry ->
                    val category = backStackEntry.arguments?.getString("name") ?: ""
                    CategoryPage(
                        navController = navController,
                        category = category,
                        cart = cart
                    )
                }
                composable("cart") { CartPage(navController, cart) }
                // Checkout page now receives the cart to calculate the total
                composable("checkout") { CheckoutPage(navController, cart) }
                composable("myAccount") { MyAccountScreen(navController) }
                composable("faq") { FaqScreen(navController) }
                composable("feedback") { FeedbackScreen(navController) }
                composable("tsCs") { TermsScreen(navController) }
                composable("privacyPolicy") { PrivacyScreen( navController) }
                composable("aboutUs") { AboutUsScreen(navController) }
                // NEW: Search results screen
                composable("search/{query}") { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query") ?: ""
                    SearchScreen(navController = navController, searchQuery = query, cart = cart)
                }
            }
        }
    } else {
        // Just show NavHost without Scaffold for splash or any route where scaffold is hidden
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("splash") { SplashScreen(navController) }
            composable("home") { HomePage(navController) }
            composable("menu") { MenuPage(navController) }
            composable("track") { TrackOrderScreenPlaceholder() }
            composable("more") { MoreScreen(navController) }
            composable("category/{name}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("name") ?: ""
                CategoryPage(
                    navController = navController,
                    category = category,
                    cart = cart
                )
            }
            composable("cart") { CartPage(navController, cart) }
            // Checkout page now receives the cart to calculate the total
            composable("checkout") { CheckoutPage(navController, cart) }
            composable("myAccount") { MyAccountScreen(navController) }
            composable("faq") { FaqScreen(navController) }
            composable("feedback") { FeedbackScreen(navController) }
            composable("tsCs") { TermsScreen( navController) }
            composable("privacyPolicy") { PrivacyScreen(navController) }
            composable("aboutUs") { AboutUsScreen( navController) }
            // NEW: Search results screen
            composable("search/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                SearchScreen(navController = navController, searchQuery = query, cart = cart)
            }
        }
    }
}

// -------------------- Splash Screen --------------------

@Composable
fun TrackOrderScreenPlaceholder() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
        Text("Track Order Screen - Coming Soon!", fontSize = 24.sp, color = Color.White)
    }
}





// -------------------- Helper: Radio Button with Label (Updated) --------------------
@Composable
fun RadioButtonWithLabel(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Make the entire row clickable
            .padding(vertical = 8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFFF97316), // Orange
                unselectedColor = Color.Gray
            )
        )
        Spacer(Modifier.width(8.dp))
        Text(label, color = Color.White, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    HomePage(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuPage() {
    MenuPage(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewCartPage() {
    val sampleCart = remember {
        mutableStateListOf(
            CartItem("Chicken Burger", 50, 2),
            CartItem("Large Fries", 55, 1)
        )
    }
    CartPage(
        navController = rememberNavController(),
        cart = sampleCart
    )
}
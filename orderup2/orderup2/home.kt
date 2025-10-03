package com.example.orderup2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun HomePage(navController: NavHostController) {
    // Use LazyListState so we can programmatically scroll to item indices
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Map sections to indices: 0 = header, 1 = hero, 2 = services, 3 = about
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp) // avoid being covered by bottom nav
    ) {
        // 0 - Header
        item {
            HeaderBar(
                onHomeClick = {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                },
                onServicesClick = {
                    coroutineScope.launch { listState.animateScrollToItem(2) }
                },
                onAboutClick = {
                    coroutineScope.launch { listState.animateScrollToItem(3) }
                }

            )
        }

        // 1 - the one with background image
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)

            ) {
                Image(
                    painter = painterResource(R.drawable.wallpaper),
                    contentDescription = "Header Background",
                    modifier = Modifier
                        .matchParentSize()
                        .blur(2.dp), // apply blur,
                    contentScale = ContentScale.Crop
                )

                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Welcome to Order Up Food Cafeteria!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        fontFamily = FontFamily.Default
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "enjoy our\n" +
                                "delicious meals",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp,
                        fontFamily = FontFamily.Cursive
                    )


                    Spacer(Modifier.height(36.dp))
                    Button(
                        onClick = { navController.navigate("menu") },
                        border = BorderStroke(2.dp, Color(0xFFF97316)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316),
                            contentColor = Color.White)

                    ) { Text("Explore Menu") }
                }
            }
        }

        // 2 - Services
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(vertical = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "Our Services",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = buildAnnotatedString {

                            withStyle(style = SpanStyle(color = Color.White)) {
                                append("Best ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFF97316))) { // Using orange color
                                append("Quality Food")
                            }
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append("\n Order now")
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(40.dp))
                    Column(
                        Modifier.fillMaxSize()
                            .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)


                    ) {
                        ServiceCard("Easy To Order", R.drawable.pic1)
                        ServiceCard("Good Quality", R.drawable.pic2)
                        ServiceCard("Lecturer Delivery", R.drawable.pic3)
                    }
                }
            }
        }

        // 3 - About
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("About Us",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 30.sp,
                    color = Color.White)

                Spacer(Modifier.height(12.dp))



                Text(
                    text = buildAnnotatedString {

                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("What ")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFFF97316))) { // Using orange color
                            append("Our Customers")
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Say About Us")
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Text("Welcome to the UMP Cafeteria!" +
                        " Our mission is to provide students, faculty, and staff with delicious, nutritious, and affordable meals" +
                        " in a comfortable and inviting space. We believe that food is not just about nourishment," +
                        " but also about creating a sense of community and connection on campus.",
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                    fontSize = 18.sp,
                    color = Color.Gray)

                Spacer(Modifier.height(40.dp))



                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CafeteriaCard("Building 5 Cafeteria", R.drawable.caf5)
                    CafeteriaCard("Building 13 Tuck shop", R.drawable.caf5)
                }
            }
        }
    }
}


@Composable
fun HeaderBar(
    onHomeClick: () -> Unit,
    onServicesClick: () -> Unit,
    onAboutClick: () -> Unit,

    ) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "Logo",
                Modifier.size(55.dp)

            )
            Spacer(Modifier.width(8.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onHomeClick) { Text("HOME", color = Color.White) }
            TextButton(onClick = onServicesClick) { Text("SERVICES", color = Color.White) }
            TextButton(onClick = onAboutClick) { Text("ABOUT", color = Color.White) }
            Spacer(Modifier.width(12.dp))

        }
    }
}
@Composable
fun ServiceCard(title: String, iconRes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .height(160.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 9.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)) // dark card background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = when (title) {
                    "Easy To Order" -> "You only need a few steps in ordering food"
                    "Good Quality" -> "Not only fast for us quality is also number one"
                    "Lecturer Delivery" -> "Delivery that is always on-time and even faster"
                    else -> ""
                },
                fontSize = 14.sp,
                color = Color(0xFFBBBBBB),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}


@Composable
fun CafeteriaCard(name: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(200.dp)
            .border(
                width = 6.dp,
                // FIX: Corrected the malformed hex color to match the app's standard orange (0xFFF97316)
                color = Color(0xFFF97316),  // Orange border color
                shape = RectangleShape       // No rounded corners
            )
            .background(Color.Black),
    ) {
        Column {
            Image(
                painter = painterResource(imageRes),
                contentDescription = name,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFF3E0)) // light cream background
                    .padding(12.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    color = Color(0xFF6D4C41), // brownish text color
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
}

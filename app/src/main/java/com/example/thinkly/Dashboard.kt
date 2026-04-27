package com.example.thinkly

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDFBFB),
            Color(0xFFEAF6FF),
            Color(0xFFF8F4FF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Thinkly Dashboard",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF1E3A5F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose an option to continue learning",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(30.dp))

            DashboardCard(
                title = "Start Quiz",
                subtitle = "Begin a new computer science quiz",
                icon = Icons.Default.PlayArrow,
                cardColor = Color(0xFF89CFF0),
                onClick = { navController.navigate("quiz") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DashboardCard(
                title = "Leaderboard",
                subtitle = "See top scores and rankings",
                icon = Icons.Default.EmojiEvents,
                cardColor = Color(0xFFA8D5BA),
                onClick = { navController.navigate("leaderboard") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DashboardCard(
                title = "Progress",
                subtitle = "Track your quiz performance",
                icon = Icons.Default.Assessment,
                cardColor = Color(0xFFF4B6C2),
                onClick = { navController.navigate("progress") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DashboardCard(
                title = "Profile",
                subtitle = "View your account details",
                icon = Icons.Default.Person,
                cardColor = Color(0xFFD8B4F8),
                onClick = { navController.navigate("profile") }
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    cardColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor.copy(alpha = 0.85f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}
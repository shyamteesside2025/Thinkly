package com.example.thinkly

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LeaderboardScreen(navController: NavController) {

    val leaderboardData = listOf(
        "1. Shyam - 95",
        "2. Rahul - 90",
        "3. Priya - 88",
        "4. Anjali - 84"
    )

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {

            // Top bar with back button
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1E3A5F),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Leaderboard",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF1E3A5F)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Top quiz performers",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(24.dp))

            leaderboardData.forEach { player ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.90f)
                    )
                ) {
                    Text(
                        text = player,
                        modifier = Modifier.padding(18.dp),
                        fontSize = 18.sp,
                        color = Color(0xFF1E3A5F)
                    )
                }
            }
        }
    }
}
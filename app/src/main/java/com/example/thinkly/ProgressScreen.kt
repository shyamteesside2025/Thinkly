package com.example.thinkly

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun ProgressScreen(navController: NavController) {

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
                    text = "My Progress",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF1E3A5F)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Track your learning progress",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Quizzes Attempted: 5",
                fontSize = 20.sp,
                color = Color(0xFF1E3A5F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Highest Score: 95",
                fontSize = 20.sp,
                color = Color(0xFF1E3A5F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Average Score: 82",
                fontSize = 20.sp,
                color = Color(0xFF1E3A5F)
            )
        }
    }
}
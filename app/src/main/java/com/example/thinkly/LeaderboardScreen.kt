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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

data class LeaderboardItem(
    val name: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0
)

@Composable
fun LeaderboardScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var leaderboardData by remember { mutableStateOf<List<LeaderboardItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("quiz_results")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { result ->
                leaderboardData = result.documents.map { doc ->
                    LeaderboardItem(
                        name = doc.getString("name") ?: "Unknown User",
                        score = doc.getLong("score")?.toInt() ?: 0,
                        totalQuestions = doc.getLong("totalQuestions")?.toInt() ?: 0
                    )
                }
                isLoading = false
            }
            .addOnFailureListener {
                message = "Failed to load leaderboard"
                isLoading = false
            }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {

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

            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF1E3A5F)
                    )
                }

                message.isNotEmpty() -> {
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = Color.Red
                    )
                }

                leaderboardData.isEmpty() -> {
                    Text(
                        text = "No scores found yet. Complete a quiz to appear here.",
                        fontSize = 16.sp,
                        color = Color(0xFF4E6E81)
                    )
                }

                else -> {
                    leaderboardData.forEachIndexed { index, player ->
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
                                text = "${index + 1}. ${player.name} - ${player.score}/${player.totalQuestions}",
                                modifier = Modifier.padding(18.dp),
                                fontSize = 18.sp,
                                color = Color(0xFF1E3A5F)
                            )
                        }
                    }
                }
            }
        }
    }
}
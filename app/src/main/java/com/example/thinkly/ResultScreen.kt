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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ResultScreen(
    navController: NavController,
    score: Int,
    totalQuestions: Int
) {
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser

    var saveMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (currentUser != null) {
            val resultData = hashMapOf(
                "userId" to currentUser.uid,
                "name" to (currentUser.email ?: "Unknown User"),
                "email" to (currentUser.email ?: ""),
                "score" to score,
                "totalQuestions" to totalQuestions,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("quiz_results")
                .add(resultData)
                .addOnSuccessListener {
                    saveMessage = "Score saved successfully"
                }
                .addOnFailureListener {
                    saveMessage = "Score could not be saved"
                }
        } else {
            saveMessage = "User not logged in"
        }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate("dashboard") }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1E3A5F),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Quiz Result",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF1E3A5F)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Your Score: $score / $totalQuestions",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Good effort! Keep improving with Thinkly.",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (saveMessage.isNotEmpty()) {
                Text(
                    text = saveMessage,
                    fontSize = 14.sp,
                    color = Color(0xFF1E3A5F)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = false }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA8D5BA)
                )
            ) {
                Text(
                    text = "Back to Dashboard",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}
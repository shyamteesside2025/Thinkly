package com.example.thinkly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String
)

@Composable
fun QuizScreen(navController: NavController) {

    val questions = listOf(
        Question(
            "What does CPU stand for?",
            listOf(
                "Central Processing Unit",
                "Computer Personal Unit",
                "Central Program Utility",
                "Control Processing User"
            ),
            "Central Processing Unit"
        ),
        Question(
            "Which data structure uses FIFO?",
            listOf(
                "Stack",
                "Queue",
                "Tree",
                "Graph"
            ),
            "Queue"
        ),
        Question(
            "Which language is mainly used for Android development?",
            listOf(
                "Swift",
                "Kotlin",
                "Python",
                "PHP"
            ),
            "Kotlin"
        )
    )

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }

    val currentQuestion = questions[currentQuestionIndex]

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quiz Time",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF1E3A5F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.90f)
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = currentQuestion.questionText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A5F)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    currentQuestion.options.forEach { option ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEAF6FF)
                            )
                        ) {
                            androidx.compose.foundation.layout.Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedOption == option,
                                    onClick = { selectedOption = option }
                                )

                                Text(
                                    text = option,
                                    fontSize = 16.sp,
                                    color = Color(0xFF1E3A5F)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (selectedOption == currentQuestion.correctAnswer) {
                        score++
                    }

                    if (currentQuestionIndex < questions.lastIndex) {
                        currentQuestionIndex++
                        selectedOption = ""
                    } else {
                        navController.navigate("result/$score/${questions.size}") {
                            popUpTo("quiz") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF89CFF0)
                ),
                enabled = selectedOption.isNotEmpty()
            ) {
                Text(
                    text = if (currentQuestionIndex == questions.lastIndex) "Finish Quiz" else "Next Question",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}
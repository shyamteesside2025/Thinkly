package com.example.thinkly

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

data class Question(
    val questionText: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = ""
)

@Composable
fun QuizScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("questions")
            .get()
            .addOnSuccessListener { result ->
                questions = result.documents.mapNotNull { doc ->
                    val questionText = doc.getString("questionText") ?: ""
                    val options = doc.get("options") as? List<String> ?: emptyList()
                    val correctAnswer = doc.getString("correctAnswer") ?: ""

                    if (questionText.isNotEmpty() && options.isNotEmpty() && correctAnswer.isNotEmpty()) {
                        Question(questionText, options, correctAnswer)
                    } else {
                        null
                    }
                }
                isLoading = false
            }
            .addOnFailureListener {
                message = "Failed to load questions"
                isLoading = false
            }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color(0xFF1E3A5F)
                )
                return@Column
            }

            if (questions.isEmpty()) {
                Text(
                    text = "No questions found in Firestore.",
                    fontSize = 20.sp,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        uploadSampleQuestionsToFirestore(db)
                        message = "Sample questions uploaded. Go back and open quiz again."
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF89CFF0)
                    )
                ) {
                    Text("Upload Sample Questions", color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (message.isNotEmpty()) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = Color(0xFF4E6E81)
                    )
                }

                return@Column
            }

            val currentQuestion = questions[currentQuestionIndex]

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
                            Row(
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

fun uploadSampleQuestionsToFirestore(db: FirebaseFirestore) {

    val sampleQuestions = listOf(
        Question(
            questionText = "What does CPU stand for?",
            options = listOf(
                "Central Processing Unit",
                "Computer Personal Unit",
                "Central Program Utility",
                "Control Processing User"
            ),
            correctAnswer = "Central Processing Unit"
        ),
        Question(
            questionText = "Which data structure uses FIFO?",
            options = listOf(
                "Stack",
                "Queue",
                "Tree",
                "Graph"
            ),
            correctAnswer = "Queue"
        ),
        Question(
            questionText = "Which language is mainly used for Android development?",
            options = listOf(
                "Swift",
                "Kotlin",
                "Python",
                "PHP"
            ),
            correctAnswer = "Kotlin"
        )
    )

    sampleQuestions.forEach { question ->
        db.collection("questions").add(question)
    }
}
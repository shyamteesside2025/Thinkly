package com.example.thinkly

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showGdprDialog by remember { mutableStateOf(true) }

    val auth = FirebaseAuth.getInstance()

    GradientBackground {

        if (showGdprDialog) {
            GDPRPrivacyDialog(
                onAccept = {
                    showGdprDialog = false
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Thinkly",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF1E3A5F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Login to continue",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4E6E81)
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    message = ""
                },
                label = { Text("Email") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    message = ""
                },
                label = { Text("Password") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("success", ignoreCase = true)) {
                        Color(0xFF2E7D32)
                    } else {
                        Color.Red
                    },
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    when {
                        email.isBlank() || password.isBlank() -> {
                            message = "Please enter email and password"
                        }

                        else -> {
                            isLoading = true
                            auth.signInWithEmailAndPassword(email.trim(), password.trim())
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        message = "Login successful"

                                        navController.navigate("dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        message = task.exception?.message ?: "Login failed"
                                    }
                                }
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
                enabled = !isLoading && !showGdprDialog
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.height(22.dp)
                    )
                } else {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1E3A5F),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("Sign Up")
                    }
                },
                fontSize = 14.sp,
                color = Color(0xFF4E6E81),
                modifier = Modifier.clickable {
                    navController.navigate("signup")
                }
            )
        }
    }
}

@Composable
fun GDPRPrivacyDialog(
    onAccept: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = onAccept,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF89CFF0)
                )
            ) {
                Text(
                    text = "I Agree",
                    color = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Privacy & GDPR Notice",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF1E3A5F)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFDFBFB),
                                Color(0xFFEAF6FF),
                                Color(0xFFF8F4FF)
                            )
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Thinkly respects your privacy. This app may collect your email address, quiz scores, progress data, and leaderboard results to provide authentication and learning features.",
                    fontSize = 14.sp,
                    color = Color(0xFF1E3A5F)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Your data is used only for app functionality and is stored securely using Firebase services. By continuing, you agree that your data may be processed for login, progress tracking, and quiz result storage.",
                    fontSize = 14.sp,
                    color = Color(0xFF1E3A5F)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "You may request data removal according to GDPR rights.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4E6E81)
                )
            }
        },
        containerColor = Color(0xFFFDFBFB),
        shape = RoundedCornerShape(24.dp)
    )
}
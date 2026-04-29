package com.example.thinkly

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DashboardScreen(navController: NavController) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var permissionMessage by remember { mutableStateOf("") }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImage = bitmap
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            permissionMessage = "Camera permission was denied"
        }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Thinkly Dashboard",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF1E3A5F)
                        )

                        Text(
                            text = "Choose an option to continue learning",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF4E6E81)
                        )
                    }

                    IconButton(
                        onClick = {
                            val permissionCheck = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(null)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Open Camera",
                            tint = Color(0xFF1E3A5F),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                capturedImage?.let { image ->
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (permissionMessage.isNotEmpty()) {
                    Text(
                        text = permissionMessage,
                        fontSize = 14.sp,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.90f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Welcome back!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A5F)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Test your Computer Science knowledge and improve your skills with quick quizzes.",
                            fontSize = 15.sp,
                            color = Color(0xFF4E6E81)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

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
            }

            Button(
                onClick = {
                    auth.signOut()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(bottom = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E3A5F)
                )
            ) {
                Text(
                    text = "Logout",
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    cardColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor.copy(alpha = 0.90f)
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

            Spacer(modifier = Modifier.size(16.dp))

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
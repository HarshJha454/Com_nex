package com.example.com_nex.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.com_nex.FirebaseAuthHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class AuthMode { LOGIN, REGISTER }

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var showInitialTitle by remember { mutableStateOf(false) }
    var startTitleTransition by remember { mutableStateOf(false) }
    var showLoginBox by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var authMode by remember { mutableStateOf(AuthMode.LOGIN) }
    
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    // Initialize Firebase Auth helper
    val authHelper = remember { FirebaseAuthHelper() }
    
    // Check if user is already logged in
    LaunchedEffect(Unit) {
        if (authHelper.isUserLoggedIn()) {
            onLoginSuccess()
        }
    }

    // Animation values for title transition
    val titleOffset by animateFloatAsState(
        targetValue = if (startTitleTransition) -200f else 0f,
        animationSpec = tween(1000, easing = EaseInOutQuart),
        label = "titleOffset"
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (!showInitialTitle) 0f else if (startTitleTransition) 0f else 1f,
        animationSpec = tween(1000),
        label = "titleAlpha"
    )

    // Launch sequential animations
    LaunchedEffect(Unit) {
        delay(500) // Short initial delay
        showInitialTitle = true // Show title in center
        delay(1500) // Show title for 1.5 seconds
        startTitleTransition = true // Start moving title up
        delay(1000) // Wait for title to move
        showLoginBox = true // Show login box
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        // Main title with animation
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, titleOffset.roundToInt()) }
                .alpha(titleAlpha)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Community Nexus",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 48.sp,
                        letterSpacing = (-1).sp,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(0f, 4f),
                            blurRadius = 8f
                        )
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    "ಸಮುದಾಯ ನೆಕ್ಸಸ್",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    ),
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Auth Box Animation
        AnimatedVisibility(
            visible = showLoginBox,
            enter = fadeIn(tween(1000)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(1000, easing = EaseOutQuart)
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black.copy(alpha = 0.25f))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Auth Mode Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TabButton(
                        text = "LOGIN",
                        isSelected = authMode == AuthMode.LOGIN,
                        onClick = { authMode = AuthMode.LOGIN }
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    TabButton(
                        text = "REGISTER",
                        isSelected = authMode == AuthMode.REGISTER,
                        onClick = { authMode = AuthMode.REGISTER }
                    )
                }
                
                // Common Fields
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    icon = Icons.Default.Email
                )

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    icon = Icons.Default.Lock,
                    isPassword = true
                )
                
                // Register-only fields
                if (authMode == AuthMode.REGISTER) {
                    CustomTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Confirm Password",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )
                    
                    CustomTextField(
                        value = displayName,
                        onValueChange = { displayName = it },
                        placeholder = "Display Name",
                        icon = Icons.Default.Person
                    )
                }
                
                // Display error message if any
                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Action Button (Login or Register)
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Email and password cannot be empty"
                            return@Button
                        }
                        
                        if (authMode == AuthMode.REGISTER) {
                            if (password != confirmPassword) {
                                errorMessage = "Passwords do not match"
                                return@Button
                            }
                            
                            if (displayName.isBlank()) {
                                errorMessage = "Display name cannot be empty"
                                return@Button
                            }
                        }
                        
                        isLoading = true
                        errorMessage = null
                        
                        scope.launch {
                            val result = if (authMode == AuthMode.LOGIN) {
                                authHelper.signInWithEmailAndPassword(email, password)
                            } else {
                                authHelper.createUserWithEmailAndPassword(email, password)
                            }
                            
                            isLoading = false
                            
                            result.fold(
                                onSuccess = {
                                    // If registration successful, update profile with display name
                                    if (authMode == AuthMode.REGISTER && displayName.isNotBlank()) {
                                        authHelper.updateUserProfile(displayName)
                                    }
                                    
                                    val message = if (authMode == AuthMode.LOGIN) "Login successful" else "Registration successful"
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    onLoginSuccess()
                                },
                                onFailure = { exception ->
                                    val action = if (authMode == AuthMode.LOGIN) "Authentication" else "Registration"
                                    errorMessage = "$action failed: ${exception.message ?: "Unknown error"}"
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(if (authMode == AuthMode.LOGIN) "Sign In" else "Register")
                    }
                }

                // Additional options
                if (authMode == AuthMode.LOGIN) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = { 
                                if (email.isBlank()) {
                                    errorMessage = "Email cannot be empty"
                                    return@TextButton
                                }
                                
                                isLoading = true
                                errorMessage = null
                                
                                // Send password reset email
                                scope.launch {
                                    val result = authHelper.sendPasswordResetEmail(email)
                                    isLoading = false
                                    
                                    result.fold(
                                        onSuccess = {
                                            Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                                        },
                                        onFailure = { exception ->
                                            errorMessage = "Failed to send reset email: ${exception.message ?: "Unknown error"}"
                                        }
                                    )
                                }
                            }
                        ) {
                            Text("Forgot Password?", color = Color.White.copy(alpha = 0.7f))
                        }
                        
                        TextButton(onClick = { 
                            Toast.makeText(context, "Continuing as Guest", Toast.LENGTH_SHORT).show()
                            onLoginSuccess() 
                        }) {
                            Text("Skip", color = Color.White.copy(alpha = 0.7f))
                        }
                    }
                }

                Text(
                    "Or continue with",
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SocialLoginButton(Icons.Default.Email, onClick = {
                        // TODO: Implement Google SignIn
                    })
                    SocialLoginButton(Icons.Default.Person, onClick = {
                        // TODO: Implement Facebook SignIn
                    })
                    SocialLoginButton(Icons.Default.Phone, onClick = {
                        // TODO: Implement Phone Auth
                    })
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                else Color.Transparent
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun SocialLoginButton(icon: ImageVector, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.08f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(20.dp)
        )
    }
} 
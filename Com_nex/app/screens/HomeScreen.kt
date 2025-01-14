package com.example.com_nex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        // Welcome Header with gradient background
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Primary, PrimaryDark)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        "नमस्ते! Welcome",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Empowering communities through technology",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Actions Section
        Text(
            "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(quickActionItems) { item ->
                QuickActionCard(item)
            }
        }
    }
}

@Composable
fun QuickActionCard(action: QuickAction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = Primary.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = action.icon,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Primary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = action.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = action.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

data class QuickAction(
    val title: String,
    val description: String,
    val icon: ImageVector
)

val quickActionItems = listOf(
    QuickAction(
        "Government Schemes",
        "Access housing and welfare programs",
        Icons.Default.AccountBalance
    ),
    QuickAction(
        "Voice Assistant",
        "Get instant help in your language",
        Icons.Default.RecordVoiceOver
    ),
    QuickAction(
        "Community Forum",
        "Connect and share with neighbors",
        Icons.Default.Groups
    ),
    QuickAction(
        "Emergency Services",
        "Quick access to emergency contacts",
        Icons.Default.LocalHospital
    )
) 
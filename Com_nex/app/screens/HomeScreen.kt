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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Welcome Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "नमस्ते! Welcome to Community Nexus",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Connecting communities, empowering lives",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Actions
        Text(
            "Quick Actions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = action.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = action.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
        "Find schemes and policies for housing assistance",
        Icons.Default.Home
    ),
    QuickAction(
        "Voice Assistant",
        "Get help through voice-based support",
        Icons.Default.Build
    ),
    QuickAction(
        "Community Discussion",
        "Connect with your community members",
        Icons.Default.Send
    )
) 
package com.example.com_nex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object AppTheme {
    val Primary = Color(0xFF1E88E5)       // Rich blue
    val Secondary = Color(0xFF43A047)     // Forest green
    val Background = Color(0xFFF5F5F5)    // Light gray
    val Surface = Color(0xFFFFFFFF)       // White
    val TextPrimary = Color(0xFF212121)   // Dark gray
    val TextSecondary = Color(0xFF757575) // Medium gray
    val Accent = Color(0xFFFF6D00)        // Orange accent
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = AppTheme.Primary,
                    secondary = AppTheme.Secondary,
                    background = AppTheme.Background,
                    surface = AppTheme.Surface
                )
            ) {
                SlumUpgradeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlumUpgradeApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = AppTheme.Surface,
                contentColor = AppTheme.Primary
            ) {
                val items = listOf(
                    Triple("Home", "Home", Icons.Default.Home),
                    Triple("Search", "Search Policies", Icons.Default.Search),
                    Triple("Help", "AI Help", Icons.Outlined.HelpOutline)
                )
                items.forEach { (label, route, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = false,
                        onClick = { navController.navigate(route) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.padding(padding)
        ) {
            composable("Home") { HomePage(navController) }
            composable("Search Policies") { SearchPoliciesPage() }
            composable("AI Help") { AIHelpPage() }
        }
    }
}

@Composable
fun HomePage(navController: androidx.navigation.NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.Background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Community Development",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.Primary
                        )
                        Text(
                            text = "Empowering Communities Together",
                            style = MaterialTheme.typography.titleMedium,
                            color = AppTheme.TextSecondary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QuickActionButton(
                        title = "Search Policies",
                        description = "Find relevant policies",
                        color = AppTheme.Primary,
                        modifier = Modifier.weight(1f)
                    ) {
                        navController.navigate("Search Policies")
                    }
                    QuickActionButton(
                        title = "AI Assistant",
                        description = "Get instant help",
                        color = AppTheme.Secondary,
                        modifier = Modifier.weight(1f)
                    ) {
                        navController.navigate("AI Help")
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Latest Updates",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        UpdateItem("New healthcare initiative launched", "2 hours ago")
                        UpdateItem("Community meeting scheduled", "1 day ago")
                        UpdateItem("Housing scheme registration open", "2 days ago")
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    title: String,
    description: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun UpdateItem(title: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.TextPrimary
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.TextSecondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPoliciesPage() {
    var searchQuery by remember { mutableStateOf("") }
    val policies = listOf(
        PolicyItem("Free Health Checkup Scheme", "Healthcare", "Provides free health checkups for all community members"),
        PolicyItem("Subsidized Housing", "Housing", "Affordable housing options for low-income families"),
        PolicyItem("Free School Supplies Program", "Education", "Distribution of school supplies to students"),
        PolicyItem("Skill Development Program", "Education", "Professional training and skill development courses"),
        PolicyItem("Affordable Housing Scheme", "Housing", "Housing assistance for eligible residents")
    )

    val filteredPolicies = policies.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.Background)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search policies...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = AppTheme.Surface,
                    focusedIndicatorColor = AppTheme.Primary,
                    unfocusedIndicatorColor = AppTheme.TextSecondary
                )
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredPolicies) { policy ->
                PolicyCard(policy)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIHelpPage() {
    var userMessage by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf(
        ChatMessage("AI", "Hello! How can I assist you today?")
    )) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.Background)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "AI Assistant",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.Primary
                )
                Text(
                    text = "Get instant help with your questions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(chatMessages) { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type your message...") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = AppTheme.Surface,
                        focusedIndicatorColor = AppTheme.Primary,
                        unfocusedIndicatorColor = AppTheme.TextSecondary
                    )
                )
                IconButton(
                    onClick = {
                        if (userMessage.isNotBlank()) {
                            chatMessages = chatMessages + ChatMessage("User", userMessage)
                            chatMessages = chatMessages + ChatMessage("AI", "I understand your question about: $userMessage")
                            userMessage = ""
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = AppTheme.Primary
                    )
                }
            }
        }
    }
}

data class PolicyItem(
    val title: String,
    val category: String,
    val description: String
)

@Composable
fun PolicyCard(policy: PolicyItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = policy.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
            Text(
                text = policy.category,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.Primary,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = policy.description,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary
            )
        }
    }
}

data class ChatMessage(
    val sender: String,
    val content: String
)

@Composable
fun ChatBubble(message: ChatMessage) {
    val isAI = message.sender == "AI"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isAI) Arrangement.Start else Arrangement.End
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isAI) AppTheme.Surface else AppTheme.Primary
            )
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isAI) AppTheme.TextPrimary else Color.White,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
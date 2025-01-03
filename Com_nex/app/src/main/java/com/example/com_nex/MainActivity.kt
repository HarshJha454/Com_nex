package com.example.com_nex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlumUpgradeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlumUpgradeApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(
                    "Explore" to Icons.Default.Home,
                    "Info" to Icons.Default.Info
                ).forEachIndexed { index, (label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = false,
                        onClick = { navController.navigate(label) }
                    )
                }
            }
        }
    ) { padding ->



        NavHost(
                    navController = navController,
                    startDestination = "Explore",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("Explore") { ExplorePage() }
                    composable("Info") { InfoPage() }
                }
    }
}

@Composable
fun ExplorePage() {
    var searchQuery by remember { mutableStateOf("") }
    val policies = listOf(
        Policy("Free Health Checkup Scheme", "Health", "Details about free health checkups."),
        Policy("Subsidized Housing", "Housing", "Details about housing subsidies."),
        Policy("Free School Supplies Program", "Education", "Details about free supplies for schools."),
        Policy("Skill Development Program", "Employment", "Details about skill development opportunities."),
        Policy("Affordable Housing Scheme", "Housing", "Details about housing assistance for the poor.")
    )

    val filteredPolicies = policies.filter {
        it.title.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Policies") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Policies List
        LazyColumn {
            items(filteredPolicies) { policy ->  // Ensure filteredPolicies is a List<Policy>
                PolicyCard(policy = policy)       // Pass the 'policy' (not an integer index) to the PolicyCard
                Spacer(modifier = Modifier.height(8.dp))
            }
        }



        Spacer(modifier = Modifier.height(24.dp))

        // Chatbot Section
        ChatbotSection()
    }
}

@Composable
fun ChatbotSection() {
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }
    var chatbotResponse by remember { mutableStateOf("Hello! How can I assist you today?") }

    val policies = listOf(
        Policy("Free Health Checkup Scheme", "Health", "Details about free health checkups."),
        Policy("Subsidized Housing", "Housing", "Details about housing subsidies."),
        Policy("Free School Supplies Program", "Education", "Details about free supplies for schools."),
        Policy("Skill Development Program", "Employment", "Details about skill development opportunities."),
        Policy("Affordable Housing Scheme", "Housing", "Details about housing assistance for the poor.")
    )

    fun findPolicyBasedOnQuery(query: String): String {
        return when {
            query.contains("build a house", ignoreCase = true) -> {
                val housingPolicies = policies.filter { it.category == "Housing" }
                "Here are some relevant policies for building a house:\n" + housingPolicies.joinToString("\n") {
                    "${it.title}: ${it.details}"
                }
            }
            query.contains("health", ignoreCase = true) -> {
                val healthPolicies = policies.filter { it.category == "Health" }
                "Here are some health-related policies:\n" + healthPolicies.joinToString("\n") {
                    "${it.title}: ${it.details}"
                }
            }
            else -> "Sorry, I couldn't find anything related to '$query'. Please try again."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Chat with us",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Chatbot conversation
        Text("Chatbot: $chatbotResponse")
        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = userMessage,
            onValueChange = {
                userMessage = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                chatbotResponse = findPolicyBasedOnQuery(userMessage.text)
                userMessage = TextFieldValue("") // Clear the input field
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send")
        }
    }
}

@Composable
fun PolicyCard(policy: Int) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = policy.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = policy.category,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            if (expanded) {
                Text(
                    text = policy.details,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (expanded) "Hide Details" else "Show Details")
            }
        }
    }
}

@Composable
fun InfoPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "About the App",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "This app provides information about government policies aimed at improving the quality of life for underprivileged communities."
        )
    }
}

data class Policy(val title: String, val category: String, val details: String)



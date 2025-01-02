package com.example.com_nex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

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
    // Define custom colors
    val customColorScheme = darkColorScheme(
        primary = Color(0xFF2196F3),
        secondary = Color(0xFF03DAC5),
        tertiary = Color(0xFFE91E63),
        background = Color(0xFFF5F5F5)
    )

    MaterialTheme(
        colorScheme = customColorScheme
    ) {
        var selectedItem by remember { mutableStateOf(0) }
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                NavigationBar {
                    listOf(
                        "Home" to Icons.Default.Home,
                        "Projects" to Icons.Default.Build,
                        "Report" to Icons.Default.Warning,
                        "Info" to Icons.Default.Info
                    ).forEachIndexed { index, (label, icon) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(label) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
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
                composable("Home") { CommunityMappingScreen() }
                composable("Projects") { ProjectsDashboardScreen() }
                composable("Report") { ReportIssueScreen() }
                composable("Info") { ResourcesScreen() }
            }
        }
    }
}

@Composable
fun CommunityMappingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header with location
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Community Mapping",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enhanced Map Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Interactive Map View",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Enhanced Issue Markers
        Text(
            "Common Issues",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IssueChip("Water", MaterialTheme.colorScheme.primary)
            IssueChip("Electricity", MaterialTheme.colorScheme.secondary)
            IssueChip("Sanitation", MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Composable
fun IssueChip(label: String, chipColor: Color) {
    AssistChip(
        onClick = { },
        label = { Text(label) },
        leadingIcon = {
            Icon(
                Icons.Default.Warning,
                contentDescription = label,
                tint = chipColor
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = chipColor.copy(alpha = 0.1f),
            labelColor = chipColor
        )
    )
}

@Composable
fun ProjectsDashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Ongoing Projects",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            val projects = listOf(
                "Water Pipeline Project 1",
                "Water Pipeline Project 2",
                "Water Pipeline Project 3"
            )
            items(projects) { project ->
                ProjectCard(
                    title = project,
                    status = "In Progress",
                    progress = 0.7f
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProjectCard(title: String, status: String, progress: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = when(status) {
                            "In Progress" -> Color(0xFF4CAF50)
                            else -> MaterialTheme.colorScheme.primary
                        }.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = when(status) {
                            "In Progress" -> Color(0xFF4CAF50)
                            else -> MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportIssueScreen() {
    var category by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Report an Issue",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Removed the description text field part

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = { },
                readOnly = true,
                label = { Text("Category") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Water", "Electricity", "Sanitation", "Roads", "Other").forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            category = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // You can add logic to handle the submission here.
                // Example: submitting the report to a backend or storing locally.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Send, contentDescription = "Submit")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Submit Report")
        }
    }
}

@Composable
fun ResourcesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Educational Resources",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(resourceItems) { resource ->
                ResourceCard(resource)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResourceCard(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(16.dp))
            Text(title, style = MaterialTheme.typography.titleMedium)
        }
    }
}

private val resourceItems = listOf(
    "Rainwater Harvesting Guide",
    "Solar Lighting Solutions",
    "Community Waste Management",
    "Sustainable Building Practices"
)

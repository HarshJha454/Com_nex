package com.example.com_nex

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Custom color scheme
val customColorScheme = darkColorScheme(
    primary = Color(0xFF4CAF50),      // Green - representing community & growth
    secondary = Color(0xFF2196F3),    // Blue - representing trust
    tertiary = Color(0xFFFF9800),     // Orange - representing accessibility
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlumUpgradeApp() {
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
                        "Explore" to Icons.Default.Info,
                        "Community" to Icons.Default.Send,
                        "Help" to Icons.Default.Build
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
                composable("Home") { HomeScreen() }
                composable("Explore") { ExploreScreen() }
                composable("Community") { CommunityScreen() }
                composable("Help") { HelpScreen() }
            }
        }
    }
} 
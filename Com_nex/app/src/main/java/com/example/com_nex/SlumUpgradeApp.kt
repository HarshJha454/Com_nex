package com.example.com_nex

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.com_nex.screens.CommunityScreen
import com.example.com_nex.screens.HomeScreen
import com.example.com_nex.screens.LoginScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

// Custom color scheme
val customColorScheme = darkColorScheme(
    primary = Color(0xFF4CAF50),      // Green - representing community & growth
    secondary = Color(0xFF2196F3),    // Blue - representing trust
    tertiary = Color(0xFFFF9800),     // Orange - representing accessibility
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF)
)

/**
 * Application class for initializing app-wide components
 */
class SlumUpgradeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlumUpgradeAppComposable() {
    MaterialTheme(
        colorScheme = customColorScheme
    ) {
        val navController = rememberNavController()
        var selectedItem by remember { mutableStateOf(0) }

        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("main") {
                MainAppContent(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    val authHelper = remember { FirebaseAuthHelper() }
    val context = LocalContext.current
    
    // Get current user's display name
    val currentUser = authHelper.getCurrentUser()
    val userInfo = if (currentUser != null) {
        if (!currentUser.displayName.isNullOrBlank()) {
            currentUser.displayName!!
        } else if (!currentUser.email.isNullOrBlank()) {
            currentUser.email!!
        } else {
            "Signed In User"
        }
    } else {
        "Guest User"
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community Nexus") },
                actions = {
                    // User profile indicator with dropdown
                    var showDropdown by remember { mutableStateOf(false) }
                    
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(end = 16.dp) // Increased padding
                                .clickable { showDropdown = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = userInfo,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        
                        DropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = { showDropdown = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Account Settings") },
                                leadingIcon = { 
                                    Icon(Icons.Default.Settings, contentDescription = null) 
                                },
                                onClick = {
                                    showDropdown = false
                                    // TODO: Navigate to account settings
                                    Toast.makeText(context, "Account Settings Coming Soon", Toast.LENGTH_SHORT).show()
                                }
                            )
                            
                            Divider()
                            
                            DropdownMenuItem(
                                text = { 
                                    Text(
                                        "Logout", 
                                        color = Color(0xFFD32F2F),
                                        fontWeight = FontWeight.Bold
                                    ) 
                                },
                                leadingIcon = { 
                                    Icon(
                                        Icons.Default.Logout, 
                                        contentDescription = null,
                                        tint = Color(0xFFD32F2F)
                                    ) 
                                },
                                onClick = {
                                    showDropdown = false
                                    authHelper.signOut()
                                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login") {
                                        popUpTo(navController.graph.id) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
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
            composable("Home") { HomeScreen(navController) }
            composable("Explore") { 
                // Placeholder for ExploreScreen
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Explore Screen Coming Soon")
                }
            }
            composable("Community") { CommunityScreen() }
            composable("Help") { 
                // Placeholder for HelpScreen
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Help Screen Coming Soon")
                }
            }
        }
    }
} 
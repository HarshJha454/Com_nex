package com.example.com_nex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.com_nex.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Com_nexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    var selectedLanguage by remember { mutableStateOf("ಕನ್ನಡ") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedLanguage == "ಕನ್ನಡ") "ಸಮುದಾಯ ನೆಕ್ಸಸ್" else "Community Nexus",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        LanguageSelector(
                            currentLanguage = selectedLanguage,
                            onLanguageSelected = { selectedLanguage = it }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val items = listOf(
                    NavigationItem("ಮುಖಪುಟ", "Home", Icons.Rounded.Home),
                    NavigationItem("ಅನ್ವೇಷಿಸಿ", "Explore", Icons.Rounded.Explore),
                    NavigationItem("ಸಮುದಾಯ", "Community", Icons.Rounded.Groups),
                    NavigationItem("ಸಹಾಯ", "Help", Icons.Rounded.Help)
                )

                items.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { it.route == item.englishLabel } == true
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(if (selectedLanguage == "ಕನ್ನಡ") item.kannadaLabel else item.englishLabel) },
                        selected = selected,
                        onClick = {
                            navController.navigate(item.englishLabel) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Home") {
                HomeContent(selectedLanguage = selectedLanguage)
            }
            composable("Explore") {
                ExploreScreen(selectedLanguage)
            }
            composable("Community") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (selectedLanguage == "ಕನ್ನಡ") "ಸಮುದಾಯ ಪುಟ" else "Community Page")
                }
            }
            composable("Help") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (selectedLanguage == "ಕನ್ನಡ") "ಸಹಾಯ ಪುಟ" else "Help Page")
                }
            }
        }
    }
}

@Composable
fun ExploreScreen(selectedLanguage: String) {

}

@Composable
fun HomeContent(selectedLanguage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                WelcomeCard(selectedLanguage)
            }

            item {
                EmergencyServicesSection(selectedLanguage)
            }

            item {
                QuickActionsGrid(selectedLanguage)
            }

            item {
                CommunityUpdates(selectedLanguage)
            }
        }
    }
}

@Composable
fun WelcomeCard(language: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = if (language == "ಕನ್ನಡ") "ನಮಸ್ಕಾರ!" else "Welcome!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (language == "ಕನ್ನಡ")
                        "ಸಮುದಾಯ ನೆಕ್ಸಸ್‌ಗೆ ಸ್ವಾಗತ"
                    else
                        "Welcome to Community Nexus",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun EmergencyServicesSection(language: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = if (language == "ಕನ್ನಡ") "ತುರ್ತು ಸೇವೆಗಳು" else "Emergency Services",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(emergencyServices) { service ->
                EmergencyServiceCard(
                    service = service,
                    language = language
                )
            }
        }
    }
}

@Composable
fun QuickActionsGrid(language: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = if (language == "ಕನ್ನಡ") "ತ್ವರಿತ ಕ್ರಿಯೆಗಳು" else "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(240.dp)
        ) {
            items(quickActions) { action ->
                QuickActionCard(
                    action = action,
                    language = language
                )
            }
        }
    }
}

data class NavigationItem(
    val kannadaLabel: String,
    val englishLabel: String,
    val icon: ImageVector
)

data class EmergencyService(
    val kannadaTitle: String,
    val englishTitle: String,
    val icon: ImageVector,
    val color: Color
)

val emergencyServices = listOf(
    EmergencyService("ಪೊಲೀಸ್", "Police", Icons.Rounded.LocalPolice, Color(0xFF1A237E)),
    EmergencyService("ಆಂಬ್ಯುಲೆನ್ಸ್", "Ambulance", Icons.Rounded.LocalHospital, Color(0xFFC62828)),
    EmergencyService("ಅಗ್ನಿಶಾಮಕ", "Fire", Icons.Rounded.LocalFireDepartment, Color(0xFFE65100))
)

data class QuickAction(
    val kannadaTitle: String,
    val englishTitle: String,
    val kannadaDescription: String,
    val englishDescription: String,
    val icon: ImageVector,
    val backgroundColor: Color
)

val quickActions = listOf(
    QuickAction(
        "ಸರ್ಕಾರಿ ಯೋಜನೆಗಳು",
        "Government Schemes",
        "ವಸತಿ ನೆರವು ಯೋಜನೆಗಳು",
        "Housing assistance schemes",
        Icons.Rounded.AccountBalance,
        Color(0xFF1565C0)
    ),
    QuickAction(
        "ಧ್ವನಿ ಸಹಾಯಕ",
        "Voice Assistant",
        "ಧ್ವನಿ ಆಧಾರಿತ ಬೆಂಬಲ",
        "Voice-based support",
        Icons.Rounded.KeyboardVoice,
        Color(0xFF2E7D32)
    ),
    QuickAction(
        "ಸಮುದಾಯ ಚರ್ಚೆ",
        "Community Discussion",
        "ಸಮುದಾಯದ ಸದಸ್ಯರೊಂದಿಗೆ ಸಂಪರ್ಕ",
        "Connect with community",
        Icons.Rounded.Forum,
        Color(0xFF6A1B9A)
    ),
    QuickAction(
        "ಕೌಶಲ್ಯ ತರಬೇತಿ",
        "Skill Training",
        "ಉದ್ಯೋಗ ತರಬೇತಿ ಕಾರ್ಯಕ್ರಮಗಳು",
        "Job training programs",
        Icons.Rounded.School,
        Color(0xFFD84315)
    )
)

@Composable
fun LanguageSelector(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(
            onClick = { expanded = true },
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(currentLanguage)
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = "Select language"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("ಕನ್ನಡ") },
                onClick = {
                    onLanguageSelected("ಕನ್ನಡ")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    onLanguageSelected("English")
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun EmergencyServiceCard(service: EmergencyService, language: String) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { /* Handle emergency service click */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = service.color)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (language == "ಕನ್ನಡ") service.kannadaTitle else service.englishTitle,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun QuickActionCard(action: QuickAction, language: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle quick action click */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = action.backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (language == "ಕನ್ನಡ") action.kannadaTitle else action.englishTitle,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (language == "ಕನ್ನಡ") action.kannadaDescription else action.englishDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun CommunityUpdates(language: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = if (language == "ಕನ್ನಡ") "ಸಮುದಾಯ ಅಪ್‌ಡೇಟ್‌ಗಳು" else "Community Updates",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Sample updates
        UpdateCard(
            title = if (language == "ಕನ್ನಡ")
                "ಹೊಸ ಆರೋಗ್ಯ ಶಿಬಿರ"
            else
                "New Health Camp",
            time = if (language == "ಕನ್ನಡ") "2 ಗಂಟೆಗಳ ಹಿಂದೆ" else "2 hours ago"
        )
        UpdateCard(
            title = if (language == "ಕನ್ನಡ")
                "ಸಮುದಾಯ ಸಭೆ"
            else
                "Community Meeting",
            time = if (language == "ಕನ್ನಡ") "1 ದಿನದ ಹಿಂದೆ" else "1 day ago"
        )
    }
}

@Composable
fun UpdateCard(title: String, time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

// Add implementations for EmergencyServiceCard and other supporting composables...
package com.example.com_nex

import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Agriculture
import androidx.compose.material.icons.rounded.AirplanemodeActive
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.CleaningServices
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.KeyboardVoice
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.LocalHospital
import androidx.compose.material.icons.rounded.LocalPolice
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Sailing
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.com_nex.ui.theme.Com_nexTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


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
                CommunityPage()
            }
            composable("Help") {
                HelpScreen(selectedLanguage)
            }
        }
    }
}

@Composable
fun ExploreScreen(selectedLanguage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = if (selectedLanguage == "ಕನ್ನಡ") "ಅನ್ವೇಷಿಸಿ" else "Explore",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exploreItems) { item ->
                ExploreCard(item, selectedLanguage)
            }
        }
    }
}

data class ExploreItem(
    val kannadaTitle: String,
    val englishTitle: String,
    val descriptionKannada: String,
    val descriptionEnglish: String,
    val icon: ImageVector,
    val backgroundColor: Color
)

val exploreItems = listOf(

    ExploreItem(
        kannadaTitle = "ಮಹಾತ್ಮಾ ಗಾಂಧಿ ರಾಷ್ಟ್ರೀಯ ಗ್ರಾಮೀಣ ಉದ್ಯೋಗ ಖಾತರಿ ಯೋಜನೆ",
        englishTitle = "Mahatma Gandhi National Rural Employment Guarantee Act",
        descriptionKannada = "ಗ್ರಾಮೀಣ ಪ್ರದೇಶಗಳಲ್ಲಿ ಉದ್ಯೋಗ ಖಾತರಿಯನ್ನು ಒದಗಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme to provide employment guarantee in rural areas",
        icon = Icons.Rounded.AccountBalance,
        backgroundColor = Color(0xFF8BC34A)
    ),
    ExploreItem(
        kannadaTitle = "ಪಂದಿತ್ ದೀನ ದಯಾಲು ಉಪಾಧ್ಯಾಯ ಗರಿಫ ಫಂಡ್ ಯೋಜನೆ",
        englishTitle = "Pandit Deen Dayal Upadhyaya Gramin Kaushalya Yojana",
        descriptionKannada = "ಗ್ರಾಮೀಣ ಯುವಕರಿಗೆ ಉದ್ಯೋಗ ಕೌಶಲ್ಯವನ್ನು ಅಭಿವೃದ್ಧಿಪಡಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme to develop employment skills for rural youth",
        icon = Icons.Rounded.Work,
        backgroundColor = Color(0xFF3F51B5)
    ),
    ExploreItem(
        kannadaTitle = "ಜೀವ ಜೋತಿ ಯೋಜನೆ",
        englishTitle = "Jeevan Jyoti Yojana",
        descriptionKannada = "ಬೀಮಾ ಯೋಜನೆಯಡಿ ಜೀವ ವಿಮೆ ನೀಡುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme providing life insurance under a specific insurance policy",
        icon = Icons.Rounded.HealthAndSafety,
        backgroundColor = Color(0xFFF44336)
    ),
    ExploreItem(
        kannadaTitle = "ಆರೋಗ್ಯ ಸುರಕ್ಷಾ ಯೋಜನೆ",
        englishTitle = "Health Security Scheme",
        descriptionKannada = "ಆರೋಗ್ಯ ಸೇವೆಗಳ ಸುಲಭ ಪೂರೈಕೆಗಾಗಿ ಯೋಜನೆ",
        descriptionEnglish = "A scheme for the easy provision of health services",
        icon = Icons.Rounded.LocalHospital,
        backgroundColor = Color(0xFFFFC107)
    ),
    ExploreItem(
        kannadaTitle = "ನಿಷ್ಕಪಟ ವಿದ್ಯುತ್ ಯೋಜನೆ",
        englishTitle = "Unbiased Electricity Scheme",
        descriptionKannada = "ಎಲ್ಲಾ ವರ್ಗಗಳ ಮಂದಿಗೆ ವಿದ್ಯುತ್ ಸರಬರಾಜು",
        descriptionEnglish = "Electricity supply for all sections of society",
        icon = Icons.Rounded.Bolt,
        backgroundColor = Color(0xFF9C27B0)
    ),
    ExploreItem(
        kannadaTitle = "ಆತ್ಮ ನಿರ್ಭರ ಭಾರತ ಅಭಿಯಾನ",
        englishTitle = "Atmanirbhar Bharat Abhiyan",
        descriptionKannada = "ಸ್ವದೇಶಿ ಉತ್ಪನ್ನಗಳನ್ನು ಪ್ರೋತ್ಸಾಹಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme promoting indigenous products",
        icon = Icons.Rounded.Build,
        backgroundColor = Color(0xFF009688)
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಜನ ಧನ್ ಯೋಜನೆ",
        englishTitle = "Pradhan Mantri Jan Dhan Yojana",
        descriptionKannada = "ಹೆಚ್ಚು ಬ್ಯಾಂಕಿಂಗ್ ಲೆಕ್ಕಗಳನ್ನು ತೆರೆಯಲು ಯೋಜನೆ",
        descriptionEnglish = "A scheme to open more banking accounts",
        icon = Icons.Rounded.AccountBalanceWallet,
        backgroundColor = Color(0xFF795548)
    ),
    ExploreItem(
        kannadaTitle = "ಸ್ವಚ್ಛ ಭಾರತ ಅಭಿಯಾನ",
        englishTitle = "Swachh Bharat Abhiyan",
        descriptionKannada = "ಗ್ರಾಮಾಂತರ ಹಾಗೂ ನಗರ ಪ್ರದೇಶಗಳಲ್ಲಿ ಸ್ವಚ್ಛತೆಗಾಗಿ ಅಭಿಯಾನ",
        descriptionEnglish = "A campaign for cleanliness in rural and urban areas",
        icon = Icons.Rounded.CleaningServices,
        backgroundColor = Color(0xFF00BCD4)
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಕಿಸಾನ್ ಸಮ್ಮಾನ್ ನಿಧಿ",
        englishTitle = "Pradhan Mantri Kisan Samman Nidhi",
        descriptionKannada = "ಕೃಷಿಕರಿಗಾಗಿ ಆಯೋಜನೆಯೆಂದು ನಗದು ಸಹಾಯವನ್ನು ನೀಡುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme providing cash assistance to farmers",
        icon = Icons.Rounded.Work,
        backgroundColor = Color(0xFFCDDC39)
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಆಯುಷ್ಮಾನ್ ಭಾರತ್ ಯೋಜನೆ",
        englishTitle = "Ayushman Bharat Yojana",
        descriptionKannada = "ಆರೋಗ್ಯ ಸೇವೆಗಳ ವಿಸ್ತರಣೆ ಮತ್ತು ಸುಲಭ ಸರಬರಾಜು",
        descriptionEnglish = "Expansion and easy supply of health services",
        icon = Icons.Rounded.HealthAndSafety,
        backgroundColor = Color(0xFF4CAF50)
    ),
    ExploreItem(
        kannadaTitle = "ನಿಶ್ಚಿತ ಧನ ಯೋಜನೆ",
        englishTitle = "Fixed Deposit Scheme",
        descriptionKannada = "ನಿಶ್ಚಿತ ಅವಧಿಗೆ ಹಣವನ್ನು ಸಂಗ್ರಹಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme to accumulate funds for a fixed period",
        icon = Icons.Rounded.Money,
        backgroundColor = Color(0xFF2196F3)
    ),
    ExploreItem(
        kannadaTitle = "ಅಂತರಿಕ್ಷ ಅನ್ವೇಷಣೆ ಯೋಜನೆ",
        englishTitle = "Space Exploration Program",
        descriptionKannada = "ಅಂತರಿಕ್ಷದ ಅನ್ವೇಷಣೆಗೆ ಸಂಬಂಧಿಸಿದ ಯೋಜನೆ",
        descriptionEnglish = "A scheme related to space exploration",
        icon = Icons.Rounded.AirplanemodeActive,
        backgroundColor = Color(0xFF673AB7)
    ),
    ExploreItem(
        kannadaTitle = "ಮಹಾರಾಷ್ಟ್ರ ಕೃಷಿ ಯೋಜನೆ",
        englishTitle = "Maharashtra Agricultural Scheme",
        descriptionKannada = "ಕೃಷಿ ಕ್ಷೇತ್ರವನ್ನು ಅಭಿವೃದ್ಧಿಪಡಿಸಲು ಯೋಜನೆ",
        descriptionEnglish = "A scheme for developing the agriculture sector",
        icon = Icons.Rounded.Agriculture,
        backgroundColor = Color(0xFF3F51B5)
    ),
    ExploreItem(
        kannadaTitle = "ಗ್ರಾಮೀಣ ವಿನಿಯೋಗ ಯೋಜನೆ",
        englishTitle = "Rural Development Scheme",
        descriptionKannada = "ಗ್ರಾಮೀಣ ಪ್ರದೇಶಗಳ ಅಭಿವೃದ್ಧಿಗೆ ಯೋಜನೆ",
        descriptionEnglish = "A scheme for the development of rural areas",
        icon = Icons.Rounded.House,
        backgroundColor = Color(0xFF4CAF50)
    ),
    ExploreItem(
        kannadaTitle = "ನೌಕಾ ಯೋಜನೆ",
        englishTitle = "Navika Sagar Parikrama",
        descriptionKannada = "ಮಹಿಳೆಯರನ್ನು ನೌಕಾ ದಾಳಿಯ ಮೂಲಕ ಉಲ್ಲೇಖಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme to empower women through naval expeditions",
        icon = Icons.Rounded.Sailing,
        backgroundColor = Color(0xFFF44336)
    ),
    ExploreItem(
        kannadaTitle = "ಧನಸಹಾಯ ಯೋಜನೆ",
        englishTitle = "Financial Assistance Scheme",
        descriptionKannada = "ವಿತ್ತೀಯ ಸಹಾಯವನ್ನು ಪ್ರೋತ್ಸಾಹಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme encouraging financial assistance",
        icon = Icons.Rounded.AttachMoney,
        backgroundColor = Color(0xFF00BCD4)
    )
)

@Composable
fun ExploreCard(item: ExploreItem, selectedLanguage: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* Handle click */ },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = item.backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (selectedLanguage == "ಕನ್ನಡ") item.kannadaTitle else item.englishTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (selectedLanguage == "ಕನ್ನಡ") item.descriptionKannada else item.descriptionEnglish,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}


data class ChatMessage(val text: String, val isUser: Boolean)
@Composable
fun HelpScreen(selectedLanguage: String) {
    var userMessage by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var isListening by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    var hasRecordPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasRecordPermission = isGranted
        if (!isGranted) {
            errorMessage = "Microphone permission is required for voice input"
        }
    }

    LaunchedEffect(Unit) {
        if (!hasRecordPermission) {
            permissionLauncher.launch(RECORD_AUDIO)
        }
    }

    val speechRecognizer = remember(hasRecordPermission) {
        if (hasRecordPermission) {
            SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onResults(results: Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        matches?.get(0)?.let { result ->
                            userMessage = result
                            isListening = false
                            errorMessage = null
                        }
                    }

                    override fun onReadyForSpeech(params: Bundle?) {
                        isListening = true
                        errorMessage = null
                    }

                    override fun onEndOfSpeech() {
                        isListening = false
                    }

                    override fun onError(error: Int) {
                        isListening = false
                        errorMessage = when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH -> "No speech detected"
                            SpeechRecognizer.ERROR_NETWORK -> "Network error"
                            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permission denied"
                            else -> "Error occurred"
                        }
                    }

                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onPartialResults(partialResults: Bundle?) {}
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }
        } else null
    }

    DisposableEffect(speechRecognizer) {
        onDispose {
            speechRecognizer?.destroy()
        }
    }

    val backgroundColor = Color(0xFF121212)
    val userMessageColor = Color(0xFF2979FF)
    val botMessageColor = Color(0xFF1E1E1E)
    val textColor = Color.White
    val secondaryTextColor = Color.White.copy(alpha = 0.7f)
    val errorColor = Color(0xFFCF6679)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = if (selectedLanguage == "ಕನ್ನಡ") "ಸಹಾಯ ಪುಟ" else "Help Page",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage!!,
                color = errorColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            reverseLayout = false
        ) {
            items(messages) { message ->
                MessageBubble(
                    message = message,
                    userMessageColor = userMessageColor,
                    botMessageColor = botMessageColor,
                    textColor = textColor
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                label = {
                    Text(
                        text = if (selectedLanguage == "ಕನ್ನಡ") "ನಿಮ್ಮ ಸಂದೇಶ" else "Your message",
                        color = secondaryTextColor
                    )
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = userMessageColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = userMessageColor,
                    focusedContainerColor = botMessageColor,
                    unfocusedContainerColor = botMessageColor
                ),
                singleLine = true
            )

            IconButton(
                onClick = {
                    if (!isListening) {
                        if (hasRecordPermission) {
                            speechRecognizer?.let { recognizer ->
                                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, if (selectedLanguage == "ಕನ್ನಡ") "kn-IN" else "en-US")
                                    putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
                                }
                                try {
                                    recognizer.startListening(intent)
                                    errorMessage = null
                                } catch (e: Exception) {
                                    errorMessage = "Failed to start voice recognition"
                                }
                            } ?: run {
                                permissionLauncher.launch(RECORD_AUDIO)
                            }
                        } else {
                            permissionLauncher.launch(RECORD_AUDIO)
                        }
                    } else {
                        speechRecognizer?.stopListening()
                        isListening = false
                    }
                }
            ) {
                Icon(
                    imageVector = if (isListening) Icons.Filled.Mic else Icons.Filled.MicNone,
                    contentDescription = "Voice input",
                    tint = if (isListening) userMessageColor else Color.Gray
                )
            }

            IconButton(
                onClick = {
                    if (userMessage.isNotBlank()) {
                        val newMessage = ChatMessage(text = userMessage, isUser = true)
                        messages.add(newMessage)

                        fetchChatbotResponse(userMessage) { response ->
                            response?.let { ChatMessage(text = it, isUser = false) }
                                ?.let { messages.add(it) }
                        }

                        userMessage = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send message",
                    tint = userMessageColor
                )
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: ChatMessage,
    userMessageColor: Color,
    botMessageColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (message.isUser) userMessageColor else botMessageColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}

private fun startVoiceRecognition(speechRecognizer: SpeechRecognizer) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }
    try {
        speechRecognizer.startListening(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun fetchChatbotResponse(userMessage: String, onResponse: (String?) -> Unit) {
    val client = OkHttpClient()
    val url = "https://commuity-nexus-backend.vercel.app/chat"

    val json = JSONObject().apply {
        put("message", userMessage)
    }

    val requestBody = json.toString().toRequestBody("application/json".toMediaType())

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val chatbotResponse = JSONObject(responseBody ?: "{}").optString("response")

                    withContext(Dispatchers.Main) {
                        onResponse(chatbotResponse)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResponse("Error: ${response.code} ${response.message}")
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResponse("Error: ${e.localizedMessage}")
            }
        }
    }
}




data class Blog(val title: String, val content: String, val author: String, val date: String)

@Composable
fun CommunityPage() {
    val blogs = listOf(
        Blog(
            title = "How Solar Energy Is Helping Rural Communities",
            content = "Solar energy is being used to power homes and schools in rural areas, providing electricity and improving quality of life. This sustainable solution has reduced reliance on expensive grid power, and families are now able to work and study after dark.",
            author = "Ayush Ratan",
            date = "January 10, 2025"
        ),
        Blog(
            title = "Affordable Healthcare for the Underserved",
            content = "Mobile health clinics are being deployed in remote villages, offering free checkups, vaccines, and general health services. These efforts are making healthcare accessible to families that would otherwise have no means of getting medical attention.",
            author = "Ayush Ratan",
            date = "December 15, 2024"
        ),
        Blog(
            title = "Microfinance: Empowering Women in Poverty",
            content = "Microloans are helping women in poverty-stricken areas start small businesses, leading to financial independence and empowerment. This initiative is improving living conditions for entire communities.",
            author = "Ayush Ratan",
            date = "November 22, 2024"
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {

        // Display each blog
        blogs.forEach { blog ->
            BlogCard(blog)
        }
    }
}


@Composable
fun BlogCard(blog: Blog) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        // Correct way to set the elevation
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = blog.title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "By ${blog.author} - ${blog.date}",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = blog.content,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Optional: Include an image to make the blog look richer
            Image(
                painter = painterResource(id = R.drawable.dummy_image), // Replace with your image resource
                contentDescription = "Blog Image",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommunityPage() {
    Com_nexTheme {
        CommunityPage()
    }
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
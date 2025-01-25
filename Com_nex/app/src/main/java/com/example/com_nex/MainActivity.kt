package com.example.com_nex



import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Agriculture
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CleaningServices
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.KeyboardVoice
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.PregnantWoman
import androidx.compose.material.icons.rounded.Sailing
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.StoreMallDirectory
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.com_nex.ui.theme.Com_nexTheme
import com.google.android.gms.location.LocationServices
import firebase.com.protolitewrapper.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Com_nexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                videoUri = "android.resource://${BuildConfig.APPLICATION_ID}/raw/background_video"
            )
        }
        composable("main") {
            MainApp()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@androidx.annotation.OptIn(UnstableApi::class)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSkip: () -> Unit,
    videoUri: String
) {
    var showInitialTitle by remember { mutableStateOf(false) }
    var startTitleTransition by remember { mutableStateOf(false) }
    var showLoginBox by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Animation values for title transition
    val titleOffset by animateFloatAsState(
        targetValue = if (startTitleTransition) -200f else 0f,
        animationSpec = tween(1000, easing = EaseInOutQuart)
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (!showInitialTitle) 0f else if (startTitleTransition) 0f else 1f,
        animationSpec = tween(1000)
    )

    // Launch sequential animations
    LaunchedEffect(Unit) {
        delay(1000) // Wait for video to start
        showInitialTitle = true // Show title in center
        delay(2500) // Show title for 2.5 seconds
        startTitleTransition = true // Start moving title up
        delay(1000) // Wait for title to move
        showLoginBox = true // Show login box
    }

    // Set up ExoPlayer with URL video
    val context = LocalContext.current
    val exoPlayer = remember {
        val videoUri = RawResourceDataSource.buildRawResourceUri(R.raw.video2)
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = true
            volume = 0f
            prepare()
        }
    }

    // Lifecycle management
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Video Background
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        // Animated Title
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

        // Login Box Animation
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
                // Login fields
                CustomTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = "Username",
                    icon = Icons.Default.Person
                )

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    icon = Icons.Default.Lock,
                    isPassword = true
                )

                Button(
                    onClick = {
                        isLoading = true
                        scope.launch {
                            delay(1500)
                            onLoginSuccess()
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
                        Text("Sign In")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { /* Handle forgot password */ }) {
                        Text("Forgot Password?", color = Color.White.copy(alpha = 0.7f))
                    }
                    TextButton(onClick = onSkip) {
                        Text("Skip", color = Color.White.copy(alpha = 0.7f))
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
                    SocialLoginButton(Icons.Default.Email)
                    SocialLoginButton(Icons.Default.Person)
                    SocialLoginButton(Icons.Default.Phone)
                }
            }
        }
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
private fun SocialLoginButton(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.08f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = CircleShape
            ),
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

data class ExploreItem(
    val kannadaTitle: String,
    val englishTitle: String,
    val descriptionKannada: String,
    val descriptionEnglish: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val fullDescriptionKannada: String,
    val fullDescriptionEnglish: String,
    val websiteLink: String? = null,
    val contactNumber: String? = null,
    val eligibilityCriteria: String? = null,
    val benefitDetails: String? = null
)


@Composable
fun ExploreScreen(selectedLanguage: String) {
    var selectedItem by remember { mutableStateOf<ExploreItem?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Enhanced Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedLanguage == "ಕನ್ನಡ") "ಅನ್ವೇಷಿಸಿ" else "Explore Schemes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                // Optional: Add a filter or search icon here
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Schemes",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn with enhanced spacing and scrolling
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(exploreItems) { item ->
                    ExploreCard(
                        item = item,
                        selectedLanguage = selectedLanguage,
                        onCardClick = { selectedItem = it }
                    )
                }
            }
        }

        // Popup for selected item
        selectedItem?.let { item ->
            PolicyDetailsPopup(
                item = item,
                selectedLanguage = selectedLanguage,
                onDismiss = { selectedItem = null }
            )
        }
    }
}

@Composable
fun ExploreCard(
    item: ExploreItem,
    selectedLanguage: String,
    onCardClick: (ExploreItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCardClick(item) },
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

@Composable
fun PolicyDetailsPopup(
    item: ExploreItem,
    selectedLanguage: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(min = 300.dp, max = 600.dp)
                .clip(RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = item.backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header with Title and Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedLanguage == "ಕನ್ನಡ") item.kannadaTitle else item.englishTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Full Description
                Text(
                    text = if (selectedLanguage == "ಕನ್ನಡ")
                        (item.fullDescriptionKannada ?: item.descriptionKannada)
                    else
                        (item.fullDescriptionEnglish ?: item.descriptionEnglish),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Additional Info Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Website Link Button
                    item.websiteLink?.let { link ->
                        Button(
                            onClick = { /* Implement link opening logic */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Language,
                                contentDescription = "Website"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Website")
                        }
                    }

                    // Contact Button
                    item.contactNumber?.let { contact ->
                        Button(
                            onClick = { /* Implement contact logic */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Call,
                                contentDescription = "Contact"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Contact")
                        }
                    }
                }
            }
        }
    }
}

// Update the exploreItems list with additional details
val exploreItems = listOf(
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಆವಾಸ್ ಯೋಜನೆ",
        englishTitle = "Pradhan Mantri Awas Yojana",
        descriptionKannada = "ಎಲ್ಲಾ ಕುಟುಂಬಗಳಿಗೆ ಮನೆ ಒದಗಿಸುವ ಯೋಜನೆ",
        descriptionEnglish = "A scheme to provide housing for all families",
        icon = Icons.Rounded.House,
        backgroundColor = Color(0xFF3F51B5),
        fullDescriptionKannada = "ಪ್ರಧಾನಮಂತ್ರಿ ಆವಾಸ್ ಯೋಜನೆ ಎಲ್ಲಾ ಕುಟುಂಬಗಳಿಗೆ ಸಾಮರ್ಥ್ಯಕ್ಕೆ ಅನುಗುಣವಾಗಿ ಮನೆ ಒದಗಿಸುವ ಉದ್ದೇಶವನ್ನು ಹೊಂದಿದೆ.",
        fullDescriptionEnglish = "Pradhan Mantri Awas Yojana aims to provide housing to all families according to their capacity and needs.",
        websiteLink = "https://pmay.gov.in/",
        contactNumber = "1800-11-6139",
        eligibilityCriteria = "Low and middle-income families without owning a pucca house",
        benefitDetails = "Financial assistance for house construction or renovation"
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಸ್ವ-ನಿಧಿ ಯೋಜನೆ",
        englishTitle = "PM SVANidhi Scheme",
        descriptionKannada = "ಚಿಕ್ಕ ವ್ಯಾಪಾರಿಗಳಿಗೆ ಸಾಲ",
        descriptionEnglish = "Loan for street vendors",
        icon = Icons.Rounded.StoreMallDirectory,
        backgroundColor = Color(0xFF009688),
        fullDescriptionKannada = "ನಗರ ಪ್ರದೇಶಗಳ ರಸ್ತೆ ವ್ಯಾಪಾರಿಗಳಿಗೆ ಸಣ್ಣ ಸಾಲ ಒದಗಿಸಿ ಅವರ ಆರ್ಥಿಕ ಸಬಲೀಕರಣಕ್ಕೆ ಕೊಡುಗೆ ನೀಡುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A scheme providing small loans to street vendors in urban areas to support their economic empowerment.",
        websiteLink = "https://msme.gov.in/",
        contactNumber = "1800-180-0018",
        eligibilityCriteria = "Street vendors with valid identity proof and operating in urban areas",
        benefitDetails = "Collateral-free loan up to ₹10,000 with digital transactions incentives"
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಮತ್ಸ್ಯ ಸಂಪಾಳನಾ ಯೋಜನೆ",
        englishTitle = "Pradhan Mantri Matsya Sampada Yojana",
        descriptionKannada = "ಮೀನು ಸಂಸ್ಕರಣಾ ಮತ್ತು ಮೀನು ಸಾಗಣೆ ಯೋಜನೆ",
        descriptionEnglish = "Fisheries processing and transportation scheme",
        icon = Icons.Rounded.Sailing,
        backgroundColor = Color(0xFFF44336),
        fullDescriptionKannada = "ಮೀನಿನ ಉತ್ಪಾದನೆ, ಸಂಸ್ಕರಣೆ, ಮಾರುಕಟ್ಟೆ ಮತ್ತು ಮೀನು ಸಾಗಣೆಯನ್ನು ಸಮಗ್ರವಾಗಿ ಬೆಂಬಲಿಸುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A comprehensive scheme supporting fish production, processing, marketing, and transportation.",
        websiteLink = "https://fisheries.gov.in/",
        contactNumber = "011-23384918",
        eligibilityCriteria = "Fishers, fish farmers, self-help groups, cooperatives",
        benefitDetails = "Financial assistance for infrastructure, technology, and market linkages"
    ),
    ExploreItem(
        kannadaTitle = "ಸ್ಟಾರ್ಟ್-ಅಪ್ ಇಂಡಿಯಾ ಯೋಜನೆ",
        englishTitle = "Startup India Scheme",
        descriptionKannada = "ಉದ್ಯಮಿಗಳಿಗೆ ಬೆಂಬಲ",
        descriptionEnglish = "Support for entrepreneurs",
        icon = Icons.Rounded.Business,
        backgroundColor = Color(0xFF795548),
        fullDescriptionKannada = "ಹೊಸ ಉದ್ಯಮಿಗಳಿಗೆ ಹಣಕಾಸು, ಸಲಹೆ ಮತ್ತು ಇನ್ಕ್ಯೂಬೇಷನ್ ಬೆಂಬಲ ಒದಗಿಸಿ ಉದ್ಯಮಶೀಲತೆಯನ್ನು ಉತ್ತೇಜಿಸುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A scheme promoting entrepreneurship by providing financial, advisory, and incubation support to new startups.",
        websiteLink = "https://www.startupindia.gov.in/",
        contactNumber = "1800-111-025",
        eligibilityCriteria = "Registered startups recognized by DPIIT",
        benefitDetails = "Tax exemptions, funding support, and easier compliance"
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಕೌಶಲ್ಯ ವಿಕಾಸ್ ಯೋಜನೆ",
        englishTitle = "Pradhan Mantri Kaushal Vikas Yojana",
        descriptionKannada = "ಯುವಕರಿಗೆ ಕೌಶಲ್ಯ ತರಬೇತಿ",
        descriptionEnglish = "Skill training for youth",
        icon = Icons.Rounded.Work,
        backgroundColor = Color(0xFF9C27B0),
        fullDescriptionKannada = "ಯುವಕರಿಗೆ ರೋಜಗಾರ ಸಾಧ್ಯವಾಗುವಂತೆ ವೃತ್ತಿಪರ ಕೌಶಲ್ಯ ತರಬೇತಿ ಒದಗಿಸುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A scheme providing professional skill training to youth to enhance their employability.",
        websiteLink = "https://pmkvyofficial.org/",
        contactNumber = "011-23370704",
        eligibilityCriteria = "Unemployed youth aged 15-35 years",
        benefitDetails = "Free skill training, certification, and job placement assistance"
    ),
    ExploreItem(
        kannadaTitle = "ಅಯುಷ್ಮಾನ್ ಭಾರತ್ ಆರೋಗ್ಯ ಯೋಜನೆ",
        englishTitle = "Ayushman Bharat Yojana",
        descriptionKannada = "ಉಚಿತ ಆರೋಗ್ಯ ವಿಮೆ",
        descriptionEnglish = "Free health insurance",
        icon = Icons.Rounded.HealthAndSafety,
        backgroundColor = Color(0xFF4CAF50),
        fullDescriptionKannada = "ದೇಶದ ಅತಿ ಕಡಿಮೆ ಆದಾಯದ ಕುಟುಂಬಗಳಿಗೆ ₹5 ಲಕ್ಷ ಮೌಲ್ಯದ ಉಚಿತ ಆರೋಗ್ಯ ವಿಮೆ.",
        fullDescriptionEnglish = "Free health insurance coverage of ₹5 lakh for the poorest families in the country.",
        websiteLink = "https://pmjay.gov.in/",
        contactNumber = "18008894404",
        eligibilityCriteria = "Families identified in the Socio-Economic Caste Census",
        benefitDetails = "Cashless treatment in empaneled hospitals across India"
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಕಿಸಾನ್ ಸಮ್ಮಾನ್ ನಿಧಿ",
        englishTitle = "PM Kisan Samman Nidhi",
        descriptionKannada = "ಕೃಷಿಕರಿಗೆ ನಗದು ಬೆಂಬಲ",
        descriptionEnglish = "Cash support for farmers",
        icon = Icons.Rounded.Agriculture,
        backgroundColor = Color(0xFF2196F3),
        fullDescriptionKannada = "ಚಿಕ್ಕ ಮತ್ತು ಅಲ್ಪ ಭೂಸ್ವಾಮ್ಯ ಕೃಷಿಕರಿಗೆ ₹6,000 ವಾರ್ಷಿಕ ಹಣಕಾಸು ಬೆಂಬಲ.",
        fullDescriptionEnglish = "Annual financial assistance of ₹6,000 to small and marginal farmers.",
        websiteLink = "https://pmkisan.gov.in/",
        contactNumber = "011-23382129",
        eligibilityCriteria = "Landholding farmers with less than 2 hectares of land",
        benefitDetails = "Direct benefit transfer of ₹2,000 in three equal installments"
    ),
    ExploreItem(
        kannadaTitle = "ಮುದ್ರಾ ಯೋಜನೆ",
        englishTitle = "Mudra Yojana",
        descriptionKannada = "ಸಣ್ಣ ವ್ಯಾಪಾರಗಳಿಗೆ ಸಾಲ",
        descriptionEnglish = "Loan for small businesses",
        icon = Icons.Rounded.CreditCard,
        backgroundColor = Color(0xFFFF9800),
        fullDescriptionKannada = "ಸಣ್ಣ ಮತ್ತು ಸೂಕ್ಷ್ಮ ಉದ್ಯಮಗಳಿಗೆ ಸಾಲ ಒದಗಿಸುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "Scheme providing loans to micro and small enterprises.",
        websiteLink = "https://mudra.org.in/",
        contactNumber = "1800-120-3191",
        eligibilityCriteria = "Non-corporate, non-farm small/micro enterprises",
        benefitDetails = "Loans categorized as Shishu, Kishor, and Tarun based on business stage"
    ),
    ExploreItem(
        kannadaTitle = "ಡಿಜಿಟಲ್ ಇಂಡಿಯಾ ಯೋಜನೆ",
        englishTitle = "Digital India Programme",
        descriptionKannada = "ಡಿಜಿಟಲ್ ಸಾಮರ್ಥ್ಯ ಬೆಂಬಲ",
        descriptionEnglish = "Digital empowerment initiative",
        icon = Icons.Rounded.Work,
        backgroundColor = Color(0xFF673AB7),
        fullDescriptionKannada = "ಡಿಜಿಟಲ್ ಸಾಮರ್ಥ್ಯ ಮೂಲಕ ಸಮಾಜದ ಎಲ್ಲಾ ವರ್ಗಗಳಿಗೆ ಡಿಜಿಟಲ್ ಸೇವೆಗಳನ್ನು ಒದಗಿಸುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A comprehensive initiative to provide digital services to all sections of society through digital empowerment.",
        websiteLink = "https://digitalindia.gov.in/",
        contactNumber = "011-24301856",
        eligibilityCriteria = "Open to all citizens, focus on rural and marginalized communities",
        benefitDetails = "Digital literacy, online services, and technology infrastructure"
    ),
    ExploreItem(
        kannadaTitle = "ನಾಳೆ ಸ್ಕಾಲರ್ ಯೋಜನೆ",
        englishTitle = "Naale Scholarship Scheme",
        descriptionKannada = "ವಿದ್ಯಾರ್ಥಿಗಳಿಗೆ ಶಿಕ್ಷಣ ಬೆಂಬಲ",
        descriptionEnglish = "Scholarship for students",
        icon = Icons.Rounded.School,
        backgroundColor = Color(0xFF00BCD4),
        fullDescriptionKannada = "ಸಾಮಾಜಿಕ ಮತ್ತು ಆರ್ಥಿಕವಾಗಿ ಹಿಂದುಳಿದ ವಿದ್ಯಾರ್ಥಿಗಳಿಗೆ ಶೈಕ್ಷಣಿಕ ಬೆಂಬಲ ಒದಗಿಸುವ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A scholarship scheme providing educational support to socially and economically backward students.",
        websiteLink = "https://scholarships.gov.in/",
        contactNumber = "1800-208-8900",
        eligibilityCriteria = "Students from SC/ST/OBC/Minority communities with good academic performance",
        benefitDetails = "Financial assistance for higher education, tuition fees, and study materials"
    ),
    ExploreItem(
        kannadaTitle = "ಸ್ವಚ್ಛ ಭಾರತ್ ಮಿಷನ್",
        englishTitle = "Swachh Bharat Mission",
        descriptionKannada = "ನಮ್ಮ ಭಾರತವನ್ನು ಶುಚಿಯಾಗಿಸೋಣ",
        descriptionEnglish = "Clean India Mission",
        icon = Icons.Rounded.CleaningServices,
        backgroundColor = Color(0xFF8BC34A),
        fullDescriptionKannada = "ಗ್ರಾಮೀಣ ಮತ್ತು ನಗರ ಪ್ರದೇಶಗಳಲ್ಲಿ ಸ್ವಚ್ಛತಾ ಜಾಗೃತಿ ಮತ್ತು ಅಭಿಯಾನ.",
        fullDescriptionEnglish = "A comprehensive cleanliness campaign in rural and urban areas to promote hygiene and sanitation.",
        websiteLink = "https://swachhbharatmission.gov.in/",
        contactNumber = "011-23073254",
        eligibilityCriteria = "All citizens, local bodies, and community organizations",
        benefitDetails = "Funding for sanitation infrastructure, toilet construction, and waste management"
    ),
    ExploreItem(
        kannadaTitle = "ಅಟಲ್ ಪೆನ್ಷನ್ ಯೋಜನೆ",
        englishTitle = "Atal Pension Yojana",
        descriptionKannada = "ವಯೋಮಾನದ ಸಾಮಾಜಿಕ ಭದ್ರತಾ ಯೋಜನೆ",
        descriptionEnglish = "Social security pension scheme",
        icon = Icons.Rounded.Help,
        backgroundColor = Color(0xFFFF5722),
        fullDescriptionKannada = "ಅಸಂಘಟಿತ ವಲಯದ ಕಾರ್ಮಿಕರಿಗಾಗಿ ಪೆನ್ಷನ್ ಯೋಜನೆ.",
        fullDescriptionEnglish = "A pension scheme designed for workers in the unorganized sector.",
        websiteLink = "https://www.npscentral.gov.in/",
        contactNumber = "1800-222-080",
        eligibilityCriteria = "Individuals aged 18-40 years, not covered under any statutory social security scheme",
        benefitDetails = "Guaranteed minimum monthly pension between ₹1,000 to ₹5,000"
    ),
    ExploreItem(
        kannadaTitle = "ಪ್ರಧಾನಮಂತ್ರಿ ಮಾತೃ ವಂದನಾ ಯೋಜನೆ",
        englishTitle = "Pradhan Mantri Matru Vandana Yojana",
        descriptionKannada = "ಗರ್ಭಿಣಿ ಮಹಿಳೆಯರಿಗೆ ಆರ್ಥಿಕ ಬೆಂಬಲ",
        descriptionEnglish = "Financial support for pregnant women",
        icon = Icons.Rounded.PregnantWoman,
        backgroundColor = Color(0xFF9C27B0),
        fullDescriptionKannada = "ಗರ್ಭಿಣಿ ಮಹಿಳೆಯರಿಗೆ ಹಗಲಿನ ಆಹಾರ ಮತ್ತು ಆರೋಗ್ಯ ಸೇವೆಗಳಿಗಾಗಿ ಹಣಕಾಸು ಬೆಂಬಲ.",
        fullDescriptionEnglish = "Financial assistance for pregnant women for nutrition and health services.",
        websiteLink = "https://wcd.nic.in/",
        contactNumber = "011-23386423",
        eligibilityCriteria = "Pregnant women of 19 years and above, working in unorganized sector",
        benefitDetails = "Direct cash transfer of ₹5,000 for health and nutrition"
    )
)
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






// Data class remains the same

data class Blog(
    val title: String,
    val content: String,
    val author: String,
    val date: String,
    val imageResId: Int // Add image resource ID
)

@Composable
fun CommunityPage() {
    var blogs by remember { mutableStateOf(listOf(
        Blog(
            title = "How Solar Energy Is Helping Rural Communities",
            content = "Solar energy is being used to power homes and schools in rural areas, providing electricity and improving quality of life. This sustainable solution has reduced reliance on expensive grid power, and families are now able to work and study after dark.",
            author = "Ayush Ratan",
            date = "January 10, 2025",
            imageResId = R.drawable.so // Replace with actual image resource
        ),
        Blog(
            title = "Affordable Healthcare for the Underserved",
            content = "Mobile health clinics are being deployed in remote villages, offering free checkups, vaccines, and general health services. These efforts are making healthcare accessible to families that would otherwise have no means of getting medical attention.",
            author = "Ayush Ratan",
            date = "December 15, 2024",
            imageResId = R.drawable.he // Replace with actual image resource
        ),
        Blog(
            title = "Microfinance: Empowering Women in Poverty",
            content = "Microloans are helping women in poverty-stricken areas start small businesses, leading to financial independence and empowerment. This initiative is improving living conditions for entire communities.",
            author = "Ayush Ratan",
            date = "November 22, 2024",
            imageResId = R.drawable.wo // Replace with actual image resource
        )
    ))}

    var showAddBlogDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(blogs) { blog ->
                EnhancedBlogCard(blog)
            }
        }

        FloatingActionButton(
            onClick = { showAddBlogDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Blog")
        }

        if (showAddBlogDialog) {
            AddBlogDialog(
                onDismiss = { showAddBlogDialog = false },
                onBlogAdded = { newBlog ->
                    blogs = blogs + newBlog
                    showAddBlogDialog = false
                }
            )
        }
    }
}

@Composable
fun EnhancedBlogCard(blog: Blog) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            Image(
                painter = painterResource(id = blog.imageResId),
                contentDescription = blog.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = blog.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "By ${blog.author} - ${blog.date}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun AddBlogDialog(
    onDismiss: () -> Unit,
    onBlogAdded: (Blog) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var imageResId by remember { mutableStateOf(R.drawable.dummy_image) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add New Blog",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Image Selection
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Selected Blog Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Image Selection Buttons (You'd implement actual image picking logic)
                Button(
                    onClick = {
                        // Implement image selection
                        // This is a placeholder - replace with actual image picker
                        imageResId = when(imageResId) {
                            R.drawable.so -> R.drawable.so
                            R.drawable.so -> R.drawable.he
                            else -> R.drawable.dummy_image
                        }
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Change Image")
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Blog Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Blog Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(bottom = 16.dp),
                    maxLines = 5
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (title.isNotBlank() && content.isNotBlank()) {
                                onBlogAdded(
                                    Blog(
                                        title = title,
                                        content = content,
                                        author = "Current User",
                                        date = LocalDate.now().toString(),
                                        imageResId = imageResId
                                    )
                                )
                            }
                        }
                    ) {
                        Text("Add Blog")
                    }
                }
            }
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
                YouTubeSection(selectedLanguage)
            }
            item {
                QuickActionsGrid(selectedLanguage)
            }


            item {
                CommunityUpdates(selectedLanguage)
            }

            // New YouTube section

        }
    }
}

data class YouTubeVideo(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val channelName: String = "PM Narendra Modi"
)

// List of Mankibaat video links (You may want to replace these with actual video IDs)
val mankibaatVideos = listOf(
    YouTubeVideo(
        id = "WrSPtjpiRrE", // Replace with actual video ID
        title = "Mannki Baat Episode 1",
        thumbnailUrl = "https://www.prabhatkhabar.com/wp-content/uploads/2024/12/PM-Modi-Mann-Ki-Baat-Live.jpg"
    ),
    YouTubeVideo(
        id = "Di-GyUIU72U", // Replace with actual video ID
        title = "Mannki Baat Episode 2",
        thumbnailUrl = "https://feeds.abplive.com/onecms/images/uploaded-images/2022/05/29/bdae8485d3a205862257b7e98f5174aa_original.jpg?impolicy=abp_cdn&imwidth=1200&height=675"
    ),
    // Add more videos as needed
)

@Composable
fun YouTubeSection(language: String) {
    val context = LocalContext.current // Get the current context

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = if (language == "ಕನ್ನಡ") "ಮನ್ ಕಿ ಬಾತ್ ವಿಡಿಯೋ " else "Mann Ki Baat Videos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mankibaatVideos) { video ->
                YouTubeVideoCard(
                    video = video,
                    language = language,
                    onVideoClick = { videoId ->
                        // Open YouTube video
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/watch?v=$videoId")
                        )
                        context.startActivity(intent) // Use context to start the activity
                    }
                )
            }
        }
    }
}



@Composable
fun YouTubeVideoCard(
    video: YouTubeVideo,
    language: String,
    onVideoClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .clickable { onVideoClick(video.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkSecondaryColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Thumbnail
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = video.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Video Details
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = darkTextColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = video.channelName,
                    style = MaterialTheme.typography.bodySmall,
                    color = darkTextColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}

val darkPrimaryColor = Color(0xFF1E1E2E)
val darkSecondaryColor = Color(0xFF2C2C3E)
val darkAccentColor = Color(0xFF6A5ACD)
val darkHighlightColor = Color(0xFF4B0082)
val darkTextColor = Color(0xFFE6E6FA)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WelcomeCard(language: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkSecondaryColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            darkAccentColor,
                            darkHighlightColor
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                // Add dynamic greeting based on time of day
                val greeting = when (LocalTime.now().hour) {
                    in 0..11 -> if (language == "ಕನ್ನಡ") "ಶುಭ ಬೆಳಗಿನ ವಂದನೆಗಳು!" else "Good Morning!"
                    in 12..16 -> if (language == "ಕನ್ನಡ") "ಶುಭ ಮಧ್ಯಾಹ್ನ!" else "Good Afternoon!"
                    else -> if (language == "ಕನ್ನಡ") "ಶುಭ ಸಂಜೆ!" else "Good Evening!"
                }

                Text(
                    text = greeting,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (language == "ಕನ್ನಡ")
                        "ಸಮುದಾಯ ನೆಕ್ಸಸ್‌ನಲ್ಲಿ ಸ್ವಾಗತ - ನಿಮ್ಮ ಸಮಗ್ರ ಸಹಾಯ ಪ್ಲಾಟ್‌ಫಾರ್ಮ್"
                    else
                        "Welcome to Community Nexus - Your Comprehensive Support Platform",
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
    val color: Color,
    val phoneNumber: String,
    val description: String
)

val emergencyServices = listOf(
    EmergencyService(
        kannadaTitle = "ಪೊಲೀಸರು",
        englishTitle = "Emergency Police",
        icon = Icons.Default.Shield,
        color = Color(0xFF1A237E),
        phoneNumber = "100",
        description = "Immediate Law Enforcement Support"
    ),
    EmergencyService(
        kannadaTitle = "ಆಂಬ್ಯುಲೆನ್ಸ್",
        englishTitle = "Medical Response",
        icon = Icons.Default.LocalHospital,
        color = Color(0xFFC62828),
        phoneNumber = "108",
        description = "Urgent Medical Assistance"
    ),
    EmergencyService(
        kannadaTitle = "ಅಗ್ನಿಶಾಮಕ",
        englishTitle = "Fire Rescue",
        icon = Icons.Default.Fireplace,
        color = Color(0xFFE65100),
        phoneNumber = "101",
        description = "Immediate Fire Services"
    )
)

// Enhanced Quick Actions
val quickActions = listOf(
    QuickAction(
        "ಸರ್ಕಾರಿ ಯೋಜನೆಗಳು",
        "Government Support",
        "ವಸತಿ ನೆರವು ಯೋಜನೆಗಳು",
        "Comprehensive Welfare Schemes",
        Icons.Rounded.AccountBalance,
        Color(0xFF1565C0)
    ),
    QuickAction(
        "ಧ್ವನಿ ಸಹಾಯಕ",
        "AI Assistant",
        "ಧ್ವನಿ ಆಧಾರಿತ ಬೆಂಬಲ",
        "Intelligent Voice Support",
        Icons.Rounded.KeyboardVoice,
        Color(0xFF2E7D32)
    ),
    QuickAction(
        "ಸಮುದಾಯ ಚರ್ಚೆ",
        "Community Hub",
        "ಸಮುದಾಯದ ಸದಸ್ಯರೊಂದಿಗೆ ಸಂಪರ̥ಕ",
        "Connect & Collaborate",
        Icons.Rounded.Forum,
        Color(0xFF6A1B9A)
    ),
    QuickAction(
        "ಕೌಶಲ್ಯ ತರಬೇತಿ",
        "Skill Development",
        "ಉದ್ಯೋಗ ತರಬೇತಿ ಕಾರ್ಯಕ್ರಮಗಳು",
        "Professional Growth Programs",
        Icons.Rounded.School,
        Color(0xFFD84315)
    )
)
data class QuickAction(
    val kannadaTitle: String,
    val englishTitle: String,
    val kannadaDescription: String,
    val englishDescription: String,
    val icon: ImageVector,
    val backgroundColor: Color
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
        )
        {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    onLanguageSelected("English")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("ಕನ್ನಡ") },
                onClick = {
                    onLanguageSelected("ಕನ್ನಡ")
                    expanded = false
                }
            )

        }
    }
}


@Composable
fun EmergencyServiceCard(service: EmergencyService, language: String) {
    val context = LocalContext.current
    var isPressed by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(180.dp)
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
            }
            .clickable(
                onClick = {
                    // Temporary visual feedback
                    isPressed = true
                    // Launch dialer intent
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${service.phoneNumber}")
                    }
                    context.startActivity(intent)

                    // Reset press state
                    Handler(Looper.getMainLooper()).postDelayed({
                        isPressed = false
                    }, 100)
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = service.color.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (language == "ಕನ್ನಡ") service.kannadaTitle else service.englishTitle,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (language == "ಕನ್ನಡ")
                    "ಸಂಪರ್ಕ ${service.phoneNumber}"
                else
                    "Contact ${service.phoneNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun QuickActionCard(action: QuickAction, language: String) {
    var isPressed by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
            }
            .clickable {
                // Add your click handling logic
                isPressed = true
                // Reset press state
                Handler(Looper.getMainLooper()).postDelayed({
                    isPressed = false
                }, 100)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = action.backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
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



@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun CommunityUpdates(language: String) {
    var location by remember { mutableStateOf<Location?>(null) }
    var news by remember { mutableStateOf<List<NewsArticle>>(emptyList()) }
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // Request location permission and get the current location
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        location = loc
                        Log.d("Location", "Location: ${loc.latitude}, ${loc.longitude}")
                        fetchNewsBasedOnLocation(loc) { fetchedNews ->
                            news = fetchedNews
                        }
                    } else {
                        Log.d("Location", "Location is null, unable to fetch news")
                    }
                }
            }
        }
    )

    // Check if location permission is granted
    LaunchedEffect(true) {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    location = loc
                    Log.d("Location", "Location: ${loc.latitude}, ${loc.longitude}")
                    fetchNewsBasedOnLocation(loc) { fetchedNews ->
                        news = fetchedNews
                    }
                } else {
                    Log.d("Location", "Location is null, unable to fetch news")
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .height(200.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header Section
        item {
            Text(
                text = if (language == "ಕನ್ನಡ") "ಸಮುದಾಯ ಅಪ್‌ಡೇಟ್‌ಗಳು" else "Community Updates",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // News Items or Placeholder
        if (news.isNotEmpty()) {
            items(news) { article ->
                UpdateCard(
                    title = article.title,
                    time = article.publishedAt,
                    imageUrl = article.imageUrl
                )
            }
        } else {
            item {
                Text(
                    text = "No news available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

// UpdateCard for displaying news items
@Composable
fun UpdateCard(title: String, time: String, imageUrl: String?) {
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
            // If image URL is available, display the image
            imageUrl?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = "News Image",
                    modifier = Modifier.size(80.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = darkAccentColor
                )
            }
        }
    }
}

// Function to fetch news based on the user's location
fun fetchNewsBasedOnLocation(location: Location, onNewsFetched: (List<NewsArticle>) -> Unit) {
    val client = OkHttpClient()
    val apiKey = "1109e8cde88b5dbdb4aa2f8768eedaa8" // Your MediaStack API key
    val countryCode = "in" // Country code for India (can be dynamic based on location)

    // Construct the URL for the MediaStack request
    val url = "https://api.mediastack.com/v1/news?access_key=1109e8cde88b5dbdb4aa2f8768eedaa8&country=in"
    // Perform the network request asynchronously
    val request = Request.Builder().url(url).build()
    client.newCall(request).enqueue(object : okhttp3.Callback {
        @androidx.annotation.OptIn(UnstableApi::class)
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.e("NewsRequest", "Error fetching news: ${e.message}")
        }

        @androidx.annotation.OptIn(UnstableApi::class)
        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    try {
                        val jsonObject = JSONObject(responseBody)
                        val articles = jsonObject.getJSONArray("data")
                        val newsList = mutableListOf<NewsArticle>()

                        for (i in 0 until articles.length()) {
                            val article = articles.getJSONObject(i)
                            val title = article.getString("title")
                            val imageUrl = article.optString("image")
                            val publishedAt = article.getString("published_at")
                            newsList.add(NewsArticle(title, imageUrl, publishedAt))
                        }

                        onNewsFetched(newsList)

                    } catch (e: JSONException) {
                        Log.e("NewsResponse", "Error parsing JSON: ${e.message}")
                    }
                }
            } else {
                Log.e("NewsResponse", "API request failed with status code: ${response.code}")
            }
        }
    })
}

// Data class for NewsArticle
data class NewsArticle(val title: String, val imageUrl: String?, val publishedAt: String)

// Preview for testing in the Compose UI
@Preview(showBackground = true)
@Composable
fun PreviewCommunityUpdates() {
    CommunityUpdates(language = "English")
}

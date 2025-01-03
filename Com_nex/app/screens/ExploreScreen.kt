package com.example.com_nex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExploreScreen() {
    var selectedLanguage by remember { mutableStateOf("English") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Language Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterChip(
                selected = selectedLanguage == "English",
                onClick = { selectedLanguage = "English" },
                label = { Text("English") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = selectedLanguage == "हिंदी",
                onClick = { selectedLanguage = "हिंदी" },
                label = { Text("हिंदी") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(governmentSchemes) { scheme ->
                SchemeCard(
                    scheme = scheme,
                    isHindi = selectedLanguage == "हिंदी"
                )
            }
        }
    }
}

@Composable
fun SchemeCard(scheme: GovernmentScheme, isHindi: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate to detail screen */ }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (isHindi) scheme.titleHindi else scheme.titleEnglish,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isHindi) scheme.descriptionHindi else scheme.descriptionEnglish,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class GovernmentScheme(
    val titleEnglish: String,
    val titleHindi: String,
    val descriptionEnglish: String,
    val descriptionHindi: String
)

val governmentSchemes = listOf(
    GovernmentScheme(
        "Pradhan Mantri Awas Yojana",
        "प्रधानमंत्री आवास योजना",
        "Housing scheme for the urban poor",
        "शहरी गरीबों के लिए आवास योजना"
    ),
    // Add more schemes here
) 
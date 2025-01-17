package com.example.com_nex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.com_nex.R

@Composable
fun ExploreScreen(selectedLanguage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = if (selectedLanguage == "ಕನ್ನಡ") "ಯೋಜನೆಗಳು" else "Schemes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(governmentSchemes) { scheme ->
                SchemeCard(scheme = scheme, isHindi = selectedLanguage == "ಹಿಂದಿ")
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
            Image(
                painter = painterResource(id = scheme.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isHindi) scheme.titleHindi else scheme.titleEnglish,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
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
    val descriptionHindi: String,
    val imageRes: Int
)

val governmentSchemes = listOf(
    GovernmentScheme(
        titleEnglish = "Pradhan Mantri Awas Yojana",
        titleHindi = "प्रधानमंत्री आवास योजना",
        descriptionEnglish = "Housing scheme for the urban poor.",
        descriptionHindi = "शहरी गरीबों के लिए आवास योजना.",
        imageRes = R.drawable.pmay // Ensure you have the correct drawable resource
    ),
    GovernmentScheme(
        titleEnglish = "Mahatma Gandhi National Rural Employment Guarantee Act",
        titleHindi = "महात्मा गांधी राष्ट्रीय ग्रामीण रोजगार गारंटी अधिनियम",
        descriptionEnglish = "Provides at least 100 days of wage employment in a financial year to every rural household.",
        descriptionHindi = "हर ग्रामीण परिवार को वित्तीय वर्ष में कम से कम 100 दिन का वेतन रोजगार प्रदान करता है.",
        imageRes = R.drawable.mgnrega // Ensure you have the correct drawable resource
    ),
    GovernmentScheme(
        titleEnglish = "Pradhan Mantri Jan Dhan Yojana",
        titleHindi = "प्रधानमंत्री जन धन योजना",
        descriptionEnglish = "A financial inclusion program to ensure access to financial services.",
        descriptionHindi = "वित्तीय सेवाओं तक पहुंच सुनिश्चित करने के लिए एक वित्तीय समावेशन कार्यक्रम.",
        imageRes = R.drawable.pmdj // Ensure you have the correct drawable resource
    )
    // Add more schemes here...
) 
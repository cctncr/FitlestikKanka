package com.example.fitlestikkanka.auth.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * User selection screen for hardcoded login.
 *
 * Displays two buttons to select between Can and Yusuf users.
 * Automatically uses hardcoded password (123456) for both.
 */
@Composable
fun UserSelectionScreen(
    onUserSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fitlestik Kanka",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF128C7E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kullanıcı Seçin",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { onUserSelected("can") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF128C7E)
            )
        ) {
            Text(
                text = "Can olarak giriş yap",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onUserSelected("yusuf") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF128C7E)
            )
        ) {
            Text(
                text = "Yusuf olarak giriş yap",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

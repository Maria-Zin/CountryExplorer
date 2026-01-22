package ru.fefu.countryexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCountryScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Countries (Simple)") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Приложение работает!", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Список стран появится здесь")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { }) {
                    Text("Обновить")
                }
            }
        }
    }
}
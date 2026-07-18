package com.gesturecontacts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gesturecontacts.contacts.Contact
import com.gesturecontacts.ui.components.ContactListItem
import com.gesturecontacts.viewmodel.GestureSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestureSearchScreen(
    viewModel: GestureSearchViewModel = hiltViewModel()
) {
    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
    val allContacts by viewModel.allContacts.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var recognizedLetter by remember { mutableStateOf<Char?>(null) }
    
    LaunchedEffect(Unit) {
        viewModel.loadContacts()
    }
    
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchContacts(searchQuery)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gesture Contacts") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Canvas para desenho de gestos
            GestureCanvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onGestureRecognized = { recognizedChar ->
                    recognizedLetter = recognizedChar
                    if (recognizedChar != null) {
                        searchQuery = recognizedChar.toString()
                    }
                }
            )
            
            // Exibir letra reconhecida
            if (recognizedLetter != null) {
                Text(
                    text = "Reconhecido: ${recognizedLetter}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Lista de resultados
            if (searchQuery.isNotEmpty()) {
                Text(
                    text = "Resultados para \"$searchQuery\":",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
                
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(searchResults) { contact ->
                        ContactListItem(contact)
                    }
                }
            } else if (allContacts.isNotEmpty()) {
                Text(
                    text = "Seus Contatos (${allContacts.size}):",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
                
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(allContacts) { contact ->
                        ContactListItem(contact)
                    }
                }
            } else {
                Text(
                    text = "Nenhum contato encontrado. Desenhe uma letra para buscar!",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

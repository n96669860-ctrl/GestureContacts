package com.gesturecontacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gesturecontacts.contacts.Contact
import com.gesturecontacts.contacts.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GestureSearchViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {
    
    private val _allContacts: MutableStateFlow<List<Contact>> = MutableStateFlow(emptyList())
    val allContacts: StateFlow<List<Contact>> = _allContacts.asStateFlow()
    
    private val _searchResults: MutableStateFlow<List<Contact>> = MutableStateFlow(emptyList())
    val searchResults: StateFlow<List<Contact>> = _searchResults.asStateFlow()
    
    fun loadContacts() {
        viewModelScope.launch {
            try {
                contactRepository.loadContactsFromDevice()
                contactRepository.getAllContacts().collect { contacts ->
                    _allContacts.value = contacts
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun searchContacts(query: String) {
        viewModelScope.launch {
            try {
                contactRepository.searchContacts(query).collect { contacts ->
                    _searchResults.value = contacts
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun clearSearch() {
        _searchResults.value = emptyList()
    }
}

package com.hfad.moneyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    fun setUsername(name: String) {
        viewModelScope.launch {
            _username.emit(name)
        }
    }

    fun clearUsername() {
        viewModelScope.launch {
            _username.emit(null)
        }
    }
}
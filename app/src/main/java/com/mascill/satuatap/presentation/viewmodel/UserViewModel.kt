package com.mascill.satuatap.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mascill.satuatap.data.model.User
import com.mascill.satuatap.domain.usecase.GetUserByIdUseCase
import com.mascill.satuatap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<User>>(Resource.Loading())
    val userState: StateFlow<Resource<User>> = _userState.asStateFlow()

    private val _usersState = MutableStateFlow<Resource<List<User>>>(Resource.Loading())
    val usersState: StateFlow<Resource<List<User>>> = _usersState.asStateFlow()

    fun getUserById(userId: String) {
        viewModelScope.launch {
            getUserByIdUseCase(userId).collect { resource ->
                _userState.value = resource
            }
        }
    }

    fun clearUserState() {
        _userState.value = Resource.Loading()
    }
}
package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.datamodel.GetUserUseCase
import com.example.test.datamodel.User

class UserViewModel : ViewModel() {
    private val getUserUseCase = GetUserUseCase()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser() {
        val userData = getUserUseCase()
        _user.value = userData
    }
}
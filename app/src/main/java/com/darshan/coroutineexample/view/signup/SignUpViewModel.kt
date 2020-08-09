package com.darshan.coroutineexample.view.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darshan.coroutineexample.model.LoginState
import com.darshan.coroutineexample.model.User
import com.darshan.coroutineexample.model.UserDatabase
import kotlinx.coroutines.*

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    val signUpComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    var job: Job? = null
    private val db by lazy { UserDatabase(application).userDao() }
    fun signUp(userName: String, password: String, address: String, otherInfo: String) {
        job = coroutineScope.launch {
            val user = db.getUser(userName)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = "User is already exists."
                }
            } else {
                var user = User(userName, password.hashCode(), address, otherInfo)
                user.id = db.insertUser(user)
                LoginState.login(user)
                withContext(Dispatchers.Main) {
                    signUpComplete.value = true
                }
            }
        }
    }


}
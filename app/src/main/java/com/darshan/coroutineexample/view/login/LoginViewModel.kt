package com.darshan.coroutineexample.view.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.darshan.coroutineexample.model.LoginState
import com.darshan.coroutineexample.model.UserDatabase
import kotlinx.coroutines.*

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(application).userDao() }
    private var job: Job? = null

    fun login(username: String, password: String) {
        job = coroutineScope.launch {
            val user = db.getUser(username)
            if (user == null) {
                withContext(Dispatchers.Main) {
                    error.value = "User not found"
                }
            } else {
                if (user.password == password.hashCode()) {
                    LoginState.login(user)
                    withContext(Dispatchers.Main) {
                        loginComplete.value = true
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        error.value = "Incorrect Password"
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}
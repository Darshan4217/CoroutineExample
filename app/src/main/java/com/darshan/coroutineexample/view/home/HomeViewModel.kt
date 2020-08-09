package com.darshan.coroutineexample.view.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.darshan.coroutineexample.model.LoginState
import com.darshan.coroutineexample.model.UserDatabase
import kotlinx.coroutines.*

class HomeViewModel(application:Application): AndroidViewModel(application) {
    var userDeleted = MutableLiveData<Boolean>()
    val logOut = MutableLiveData<Boolean>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    private val db by lazy { UserDatabase(application).userDao() }

    fun logOut(){
        LoginState.logout()
        logOut.value = true
    }

    fun onDeleteUser(){
        coroutineScope.launch {
            LoginState.user?.let {
                db.deleteUser(it.id)
            }
            withContext(Dispatchers.Main){
                LoginState.logout()
                userDeleted.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
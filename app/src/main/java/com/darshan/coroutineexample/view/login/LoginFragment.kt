package com.darshan.coroutineexample.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.darshan.coroutineexample.R
import com.darshan.coroutineexample.util.showLongToast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.buttonLogin
import kotlinx.android.synthetic.main.fragment_sign_up.editTextPassword
import kotlinx.android.synthetic.main.fragment_sign_up.editTextUsername

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogin.setOnClickListener { onLogin() }
        buttonSignup.setOnClickListener { onGoToSignUp(it) }

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.loginComplete.observe(viewLifecycleOwner, Observer {
            context?.showLongToast("Login completed!")
            val action = LoginFragmentDirections.actionGoToHome()
            Navigation.findNavController(editTextUsername).navigate(action)
        })

        loginViewModel.error.observe(viewLifecycleOwner, Observer {
            context?.showLongToast("Error: $it")
        })
    }

    private fun onGoToSignUp(view: View) {
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(view).navigate(action)
    }

    private fun onLogin() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            context?.showLongToast("Please fill all fields")
        } else {
            loginViewModel.login(username, password)
        }
    }

}

package com.darshan.coroutineexample.view.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.darshan.coroutineexample.R
import com.darshan.coroutineexample.util.showLongToast
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSignUp.setOnClickListener { onSignUp() }
        buttonLogin.setOnClickListener { goToLogin(it) }

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        signUpViewModel.signUpComplete.observe(viewLifecycleOwner, Observer {
            context?.showLongToast("Sign up completed!")
            val action = SignUpFragmentDirections.actionGoToHome()
            Navigation.findNavController(editTextAddress).navigate(action)
        })

        signUpViewModel.error.observe(viewLifecycleOwner, Observer {
            context?.showLongToast("Error:$it")
        })
    }

    private fun goToLogin(view: View) {
        val action = SignUpFragmentDirections.actionGoToLogin()
        Navigation.findNavController(view).navigate(action)
    }

    private fun onSignUp() {
        val userName = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        val address = editTextAddress.text.toString()
        val otherInfo = editTextOtherInfo.text.toString()

        if (userName.isEmpty() || password.isEmpty() || address.isEmpty() || otherInfo.isEmpty()) {
            context?.showLongToast("Please fill all the fields")
        } else {
            signUpViewModel.signUp(userName, password, address, otherInfo)
        }
    }

}

package com.darshan.coroutineexample.view.home

import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonDeleteUser.setOnClickListener { onDelete() }
        buttonLogout.setOnClickListener { onLogOut() }
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.logOut.observe(viewLifecycleOwner, Observer {
            context?.showLongToast("Logged out")
            goToSignUpScreen()
        })

        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            context?.showLongToast("Logged out")
            goToSignUpScreen()
        })
    }

    private fun goToSignUpScreen() {
        val action = HomeFragmentDirections.actionGoToSignup()
        Navigation.findNavController(textView).navigate(action)
    }

    private fun onLogOut() {
        viewModel.logOut()
    }

    private fun onDelete() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete user")
                .setMessage("Are you sure you want to delete user")
                .setPositiveButton("Yes") { _, _ -> viewModel.onDeleteUser() }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

}

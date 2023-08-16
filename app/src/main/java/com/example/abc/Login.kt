package com.example.abc

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.abc.api.Retrofitclient
import com.example.abc.databinding.FragmentLoginBinding
import com.example.abc.model.UserRequest
import com.example.abc.model.UserResponse
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private fun isValidEmail(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    companion object {
        var token: String? = null
        lateinit var NAME: String
        lateinit var userEmail: String
        lateinit var loginOtpEmail: String
        var isStore:Boolean=true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val progressBar = binding.progressBar
        val signUp: TextView = binding.btnSignUp
        signUp.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, Singup())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        val signInBtn: Button = binding.btnLogin
        signInBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            signInBtn.isEnabled = false
            signInBtn.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.light_gray
                )
            )
            userEmail = binding.txtEmail.text.toString().trim()

            val userPassword = binding.txtPassword.text.toString().trim()

            if (!isValidEmail(userEmail)) {
                progressBar.visibility = View.INVISIBLE
//                val progressBar=v.findViewById<ProgressBar>(R.id.progressBar2)
                binding.txtEmail.error = "Please enter a valid email "
                signInBtn.isEnabled = true
                signInBtn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                return@setOnClickListener
            }
            if (userPassword.isEmpty()) {

                progressBar.visibility = View.INVISIBLE
                val progressBar = binding.progressBar
                binding.txtPassword.error = "Please enter the password"
                signInBtn.isEnabled = true
                signInBtn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                return@setOnClickListener
            }
            val customer = binding.customer
            val store = binding.store
            store.setOnClickListener {
               isStore =  false
            }
            customer.setOnClickListener {
               isStore = true
            }
             Retrofitclient.init().logInUser(userEmail,userPassword, isStore).enqueue(object :Callback<UserResponse>{
                 override fun onResponse(
                     call: Call<UserResponse>,
                     response: Response<UserResponse>
                 ) {
                     progressBar.visibility = View.INVISIBLE
                     if (response.code() == 201) {
                         NAME = response.body()?.email.toString()
                         Toast.makeText(
                             activity,
                             "You've been logged in",
                             Toast.LENGTH_LONG
                         ).show()
                         val fragmentManager = activity?.supportFragmentManager
                         val fragmentTransaction = fragmentManager?.beginTransaction()
                         fragmentTransaction?.replace(R.id.fragmentContainerView, Main())
                         fragmentTransaction?.addToBackStack(null)
                         fragmentTransaction?.commit()
                     }
                     else if (response.code() == 401) {
                         Toast.makeText(
                             activity,
                             "User not registered",
                             Toast.LENGTH_LONG
                         ).show()
//
                        binding.txtPassword.text?.clear()
                         signInBtn.isEnabled = true
                         signInBtn.setBackgroundColor(
                             ContextCompat.getColor(
                                 requireContext(),
                                 R.color.black
                             )
                         )
                     }
                     else if (response.code() == 403) {
                         Toast.makeText(
                             activity,
                             "User not validated, please verify your email first",
                             Toast.LENGTH_LONG
                         ).show()
                         signInBtn.isEnabled = true
                     }

                     else if(response.code() == 400){
                         Toast.makeText(
                             activity,
                             "Wrong password entered",
                             Toast.LENGTH_LONG
                         ).show()
                         binding.txtPassword.text?.clear()
                         signInBtn.isEnabled = true
                         signInBtn.setBackgroundColor(
                             ContextCompat.getColor(
                                 requireContext(),
                                 R.color.black
                             )
                         )
                     }
                 }

                 override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                     progressBar.visibility = View.INVISIBLE
                     signInBtn.isEnabled=true
                     Toast.makeText(
                         activity,
                         "Some error has been occurred!\n\nPlease try again",
                         Toast.LENGTH_LONG
                     ).show()
                     binding.txtPassword.text?.clear()
                     signInBtn.isEnabled = true
                     signInBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                 }

             })
        }

        return binding.root
    }
}
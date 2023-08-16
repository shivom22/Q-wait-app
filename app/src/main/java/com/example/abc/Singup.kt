package com.example.abc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.abc.api.Retrofitclient
import com.example.abc.databinding.FragmentSingupBinding
import com.example.abc.model.SignupRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Singup : Fragment() {
    private var _binding: FragmentSingupBinding? = null
    private val binding get() = _binding!!

    private fun isValidEmail(str: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }
      companion object{
        lateinit var emailSignUp:String
          lateinit var password:String
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSingupBinding.inflate(inflater,container,false)
        val login = binding.btnLogin
        login.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView,Login())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        val sign_up = binding.btnSignUp
        sign_up.setOnClickListener {
            val progressbar = binding.progressBar
            progressbar.visibility = View.VISIBLE
            sign_up.isEnabled = false
            sign_up.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.light_gray
                )
            )
            emailSignUp = binding.txtEmailSignup.text.toString().trim()
            password = binding.txtPassword.text.toString().trim()
            if (emailSignUp.isEmpty()) {
                progressbar.visibility = View.INVISIBLE
                binding.txtEmailSignup.error = "Please enter the email"
                sign_up.isEnabled = true
                sign_up.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                return@setOnClickListener
            }
            if (!isValidEmail(emailSignUp)) {
                progressbar.visibility = View.INVISIBLE
                binding.txtEmailSignup.error = "Please enter a valid email"
                sign_up.isEnabled = true
                sign_up.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                return@setOnClickListener
            }
            var flagLower = false
            var flagUpper = false
            var flagNumber = false
            for(i in password.indices){
                if(password[i] in 'a'..'z')
                    flagLower = true
                if(password[i] in 'A'..'Z')
                    flagUpper = true
                if(password[i] in '0'..'9')
                    flagNumber = true
            }
            if(!(flagLower && flagUpper && flagNumber) || password.length < 5){
                progressbar.visibility=View.INVISIBLE
                binding.txtPassword.setError("Minimum length of password should be 5 characters\n\nThere should be atleast one uppercase, lowercase and a numeric digit")
                sign_up.isEnabled = true
                sign_up.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                return@setOnClickListener
            }
            val re_entered_password = binding.txtRePassword.text.toString().trim()
            if(re_entered_password != password){
                progressbar.visibility=View.INVISIBLE
                binding.txtRePassword.error = "Please enter the same password"
                sign_up.isEnabled = true
                sign_up.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                return@setOnClickListener
            }
          Retrofitclient.init().signup(SignupRequest(emailSignUp)).enqueue(object : Callback<ResponseBody>{
              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if(response.code() == 201) {
                      progressbar.visibility=View.INVISIBLE
                      val fragmentManager = activity?.supportFragmentManager
                      val fragmentTransaction = fragmentManager?.beginTransaction()
                      fragmentTransaction?.replace(R.id.fragmentContainerView, otpfrag())
                      fragmentTransaction?.addToBackStack("tag")
                      fragmentTransaction?.commit()
                  }
                  else if(response.code()==406){
                      progressbar.visibility=View.INVISIBLE
                      Toast.makeText(activity,"Registration was not successful. please enter the details carefully", Toast.LENGTH_LONG).show()
                      binding.txtPassword.text?.clear()
                      binding.txtRePassword.text?.clear()
                      sign_up.isEnabled = true
                      sign_up.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                  }
                  else if(response.code()==403)
                  {
                      progressbar.visibility=View.INVISIBLE
                      Toast.makeText(activity,"Entered email is already registered", Toast.LENGTH_LONG).show()
                      binding.txtPassword.text?.clear()
                      binding.txtRePassword.text?.clear()
                      sign_up.isEnabled = true
                      sign_up.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                  }
              }

              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  progressbar.visibility=View.INVISIBLE
                  Toast.makeText(
                      activity, t.message,
                      Toast.LENGTH_LONG
                  ).show()
                  binding.txtPassword.text?.clear()
                  binding.txtRePassword.text?.clear()
                  sign_up.isEnabled = true
                  sign_up.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
              }
          })
        }
        return binding.root
    }


}
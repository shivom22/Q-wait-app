package com.example.abc

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.abc.api.Retrofitclient
import com.example.abc.databinding.FragmentOtpfragBinding
import com.example.abc.model.SignupRequest
import com.example.abc.model.otpRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class otpfrag : Fragment() {
    private var _binding: FragmentOtpfragBinding? = null
    private val binding get() = _binding!!
    lateinit var timerCountDownTimer: CountDownTimer
    var timerOnStatus: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpfragBinding.inflate(inflater, container, false)
        val resend_now = binding.resentBtn
        val verify = binding.verifyButtonSignUp
        fun startTimer() {
            timerCountDownTimer = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val timeLeft = millisUntilFinished / 1000
                    if (timeLeft.toString().length == 2) {
                        binding.timer.text =  "00:" + timeLeft.toString()
                    } else {
                        binding.timer.text = "00:0" + timeLeft.toString()
                    }
                    resend_now.setTextColor(Color.DKGRAY)
                }

                override fun onFinish() {
                    binding.timer.text = getString(R.string.default_timer)
                    timerOnStatus = false
                    resend_now.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.highlight_text_color
                        )
                    )
                }

            }.start()
        }

        startTimer()

        val otp1 = binding.OtpSignUp1
        val otp2 = binding.OtpSignUp2
        val otp3 = binding.OtpSignUp3
        val otp4 = binding.OtpSignUp4
        val progressBar = binding.progressBar
        otp1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp1.text.toString().length == 1) {
                    otp2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        otp2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp2.text.toString().length == 1) {
                    otp3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        otp3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp3.text.toString().length == 1) {
                    otp4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        verify.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            verify.isEnabled = false
            verify.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_gray))

            val userOtp = binding.OtpSignUp1.text.toString()
                .trim() + binding.OtpSignUp2.text.toString().trim() + otp3.text.toString()
                .trim() + otp4.text.toString().trim()

            Retrofitclient.init()
                .getotpsignup(otpRequest(Singup.emailSignUp, userOtp, Singup.password))
                .enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.code() == 200) {
                            progressBar.visibility = View.INVISIBLE
                            timerCountDownTimer.cancel()
                            Toast.makeText(
                                activity,
                                "You are now registered!\n\nPlease login to access the dashboard",
                                Toast.LENGTH_LONG
                            ).show()
                            val fragmentManager = activity?.supportFragmentManager
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(R.id.fragmentContainerView, Detailsfrag())
                            fragmentTransaction?.addToBackStack(null)
                            fragmentTransaction?.commit()
                        } else if (response.code() == 420) {
                            progressBar.visibility = View.INVISIBLE
                            verify.isEnabled = true
                            verify.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.light_gray
                                )
                            )
                            otp1.text.clear()
                            otp2.text.clear()
                            otp3.text.clear()
                            otp4.text.clear()

                            Toast.makeText(activity, "OTP incorrect", Toast.LENGTH_LONG).show()
                        } else if(response.code()==401){
                            progressBar.visibility = View.INVISIBLE
                            verify.isEnabled = true
                            verify.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.light_gray
                                )
                            )
                            Toast.makeText(activity, "Already registered.", Toast.LENGTH_LONG).show()
                        } else {
                            progressBar.visibility = View.INVISIBLE
                            verify.isEnabled = true
                            verify.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.light_gray
                                )
                            )
                            otp1.text.clear()
                            otp2.text.clear()
                            otp3.text.clear()
                            otp4.text.clear()

                            Toast.makeText(activity, "OTP expired", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        progressBar.visibility = View.INVISIBLE
                        verify.isEnabled = true
                        verify.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_gray
                            )
                        )
                        otp1.text.clear()
                        otp2.text.clear()
                        otp3.text.clear()
                        otp4.text.clear()

                        Toast.makeText(activity, "Crashed  Api", Toast.LENGTH_LONG).show()
                    }
                })
        }
        resend_now.setOnClickListener {
            if (!timerOnStatus) {
                progressBar.visibility = View.VISIBLE
                timerOnStatus = true
                Retrofitclient.init().signup(SignupRequest(Singup.emailSignUp))
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.code() == 201) {
                                progressBar.visibility = View.INVISIBLE
                                verify.isEnabled = true
                                Toast.makeText(
                                    activity,
                                    "Otp has been resent successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                                startTimer()
                            } else if (response.code() == 406) {
                                progressBar.visibility = View.INVISIBLE
                                Toast.makeText(
                                    activity,
                                    "Registration was not successful. please enter the details carefully",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (response.code() == 403) {
                                progressBar.visibility = View.INVISIBLE
                                progressBar.visibility = View.INVISIBLE
                                Toast.makeText(
                                    activity,
                                    "Otp has not been resent\n\nKindly try again",
                                    Toast.LENGTH_LONG
                                ).show()
                                timerOnStatus = false
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            progressBar.visibility = View.INVISIBLE
                            Toast.makeText(activity, "Crashed Api", Toast.LENGTH_LONG).show()
                            timerOnStatus = false
                        }
                    })
            }
        }
        return binding.root
    }
}
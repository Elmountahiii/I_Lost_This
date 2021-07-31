package com.redgunner.ilostthis.view.fragment.auth


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_log_in.*

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    private val viewModel: AuthViewModel by activityViewModels()



    override fun onResume() {
        super.onResume()

        ForgetPasswordText.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_resetPasswordFragment)

        }

        LogInButton.setOnClickListener {

            if(LoginEmail.text.toString().isNotEmpty() && LogInPassword.text.toString().isNotEmpty()){
                progressBar3.isVisible=true
                viewModel.login(LoginEmail.text.toString(),LogInPassword.text.toString())

            }else{
                Toast.makeText(this.context,"Email Or Password are Empty",Toast.LENGTH_LONG).show()
            }

        }


        viewModel.restPasswordResult.observe(viewLifecycleOwner,{result ->

            Toast.makeText(context,result,Toast.LENGTH_LONG).show()

        })

    }



}
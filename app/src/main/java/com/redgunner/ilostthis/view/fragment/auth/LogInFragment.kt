package com.redgunner.ilostthis.view.fragment.auth


import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_log_in.*

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    private val viewModel: AuthViewModel by activityViewModels()



    override fun onResume() {
        super.onResume()

        LogInButton.setOnClickListener {

            if(LoginEmail.text.toString().isNotEmpty() && LogInPassword.text.toString().isNotEmpty()){
                viewModel.login(LoginEmail.text.toString(),LogInPassword.text.toString())

            }else{
                Toast.makeText(this.context,"Email Or Password are Empty",Toast.LENGTH_LONG).show()
            }

        }

    }



}
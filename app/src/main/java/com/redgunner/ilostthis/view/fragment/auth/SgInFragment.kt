package com.redgunner.ilostthis.view.fragment.auth

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_sgin_in.*

class SgInFragment : Fragment(R.layout.fragment_sgin_in) {

    private val viewModel: AuthViewModel by activityViewModels()


    override fun onResume() {
        super.onResume()

        SingInButton.setOnClickListener {

            if (SignInEmail.text.toString().isNotEmpty()&& SignInPassword.text.toString().isNotEmpty()){
                viewModel.sgInIn(SignInEmail.text.toString(),SignInPassword.text.toString())

            }else{
                Toast.makeText(this.context,"Email Or Password are Empty", Toast.LENGTH_LONG).show()

            }

        }


    }
}
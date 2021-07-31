package com.redgunner.ilostthis.view.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    private val viewModel: AuthViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()
        setUpObservers()

    }


    override fun onResume() {
        super.onResume()
        SendPasswordButton.setOnClickListener {

            if (PasswordRestEditText.text.toString().isNotEmpty()){
                progressBar4.isVisible=true
                viewModel.restPassword(PasswordRestEditText.text.toString())

            }else{
                Toast.makeText(this.context,"Email is Empty", Toast.LENGTH_LONG).show()

            }


        }
    }




    private fun setUpObservers() {
        viewModel.restPasswordResult.observe(viewLifecycleOwner, Observer { result ->
            showToast(result)
            viewModel.restPasswordResult.value=""
            progressBar4.isVisible=false


        })
    }

    private fun showToast( message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }





}
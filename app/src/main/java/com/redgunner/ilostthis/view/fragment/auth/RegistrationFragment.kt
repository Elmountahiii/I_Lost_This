package com.redgunner.ilostthis.view.fragment.auth


import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.google.android.material.tabs.TabLayoutMediator
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.RegistrationStateAdapter
import com.redgunner.ilostthis.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_sgin_in.*
import kotlinx.coroutines.delay

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var facebookCallback: CallbackManager
    private lateinit var googleSinInClient: GoogleSignInClient
    private val googleSingInCode = 1


    override fun onStart() {
        super.onStart()
        setUpTapLayout()
        setUpFacebookCallback()
        setUpGoogle()


    }



    override fun onResume() {
        super.onResume()

        viewModel.firebaseRegResult.observe(viewLifecycleOwner,{result ->
            Toast.makeText(context,result,Toast.LENGTH_LONG).show()

        })

        viewModel.authSgInSuccessful.observe(viewLifecycleOwner,{ Successful ->

           if (Successful){
               findNavController().navigate(R.id.action_registrationFragment_to_addInformationFragment)
           }else{
               progressBar5.isVisible=false

           }
        })


        viewModel.socialMediaAuthSuccessful.observe(viewLifecycleOwner,{hasInformation ->


            lifecycleScope.launchWhenStarted {
                delay(500)
                if (hasInformation){
                    findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
                }else{
                    findNavController().navigate(R.id.action_registrationFragment_to_addInformationFragment)

                }
            }

        })

        viewModel.authLogInSuccessful.observe(viewLifecycleOwner,{Successful ->

            if (Successful){

                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
            }else{
                progressBar3.isVisible=false



            }

        })

        GoogleLogIn.setOnClickListener {
            val singInIntent = googleSinInClient.signInIntent
            startActivityForResult(singInIntent, googleSingInCode)

        }

        FacebookLogIn.setOnClickListener {
            FacebookButton.performClick()
        }
    }


    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleSingInCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {


                val account = task.getResult(ApiException::class.java)

                account?.let {

                    viewModel.loginWithGoogle(account)

                }

            } catch (e: ApiException) {

            }


        } else {


            facebookCallback.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun setUpTapLayout() {
        myViewpager.adapter = RegistrationStateAdapter(activity)
        TabLayoutMediator(TabLayout, myViewpager) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = "SignIn"
                }
                1 -> {
                    tab.text = "LogIn"
                }
            }

        }.attach()
    }

    private fun setUpFacebookCallback() {
        facebookCallback = CallbackManager.Factory.create()
        FacebookButton.setPermissions("email")
        FacebookButton.fragment = this
        FacebookButton.registerCallback(facebookCallback,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {

                        loginResult?.accessToken?.let { accessToken ->
                           viewModel.loginWithFacebook(accessToken)
                        }

                    }

                    override fun onCancel() {
                       Toast.makeText(context,"Cancel",Toast.LENGTH_LONG).show()


                    }

                    override fun onError(exception: FacebookException) {

                        exception.message?.let { message ->
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show()

                        }


                    }

                })


    }

    private fun setUpGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSinInClient = GoogleSignIn.getClient(requireView().context, googleSignInOptions)
    }

}
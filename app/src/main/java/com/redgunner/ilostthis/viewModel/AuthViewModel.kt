package com.redgunner.ilostthis.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.redgunner.ilostthis.repository.FirebaseRepository
import com.redgunner.ilostthis.utils.User

class AuthViewModel @ViewModelInject constructor(
        private val firebaseRepository: FirebaseRepository) : ViewModel() {


    val userAuth = MutableLiveData<Boolean>()
    val authSgInSuccessful by lazy {
        firebaseRepository.authSgInSuccessful
    }
    val authLogInSuccessful by lazy {
        firebaseRepository.authLogInSuccessful
    }

    val saveInformationSuccessful by lazy {
        firebaseRepository.addInformationSuccessful
    }

    val socialMediaAuthSuccessful by lazy {
        firebaseRepository.socialMediaAuthSuccessful
    }
    val firebaseRegResult by lazy {
        firebaseRepository.firebaseRegResult
    }

    init {
        userAuth.value = firebaseRepository.checkUser()
    }

    val restPasswordResult by lazy {
        firebaseRepository.restPasswordResult
    }





    fun login(email: String, password: String) {

        firebaseRepository.logIn(email, password)
    }

    fun sgInIn(email: String, password: String) {
        firebaseRepository.signIn(email, password)

    }

    fun loginWithFacebook(accessToken: AccessToken) {
        firebaseRepository.facebookAuthentication(accessToken)

    }

    fun loginWithGoogle(account: GoogleSignInAccount) {
        firebaseRepository.googleAuthentication(account)
    }

    fun restPassword(email: String) {
        firebaseRepository.resatPassword(email)
    }

    fun saveUserInformation(user: User) {
        firebaseRepository.saveUserInformation(user)

    }

}
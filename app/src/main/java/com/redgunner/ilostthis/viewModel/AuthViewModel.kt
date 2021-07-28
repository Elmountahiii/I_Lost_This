package com.redgunner.ilostthis.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redgunner.ilostthis.repository.FirebaseRepository
import com.redgunner.ilostthis.utils.User

class AuthViewModel @ViewModelInject constructor(
      private val firebaseRepository: FirebaseRepository ) :ViewModel(){


    val userAuth=MutableLiveData<Boolean>()
    val authSgInSuccessful by lazy {
        firebaseRepository.authSgInSuccessful
    }
    val authLogInSuccessful by lazy {
        firebaseRepository.authLogInSuccessful
    }

    val saveInformationSuccessful by lazy {
        firebaseRepository.addInformationSuccessful
    }

    init {
       userAuth.value=firebaseRepository.checkUser()
   }


    fun login(email: String, password: String){

        firebaseRepository.logIn(email, password)
    }

    fun sgInIn(email: String, password: String){
        firebaseRepository.signIn(email, password)

    }

    fun restPassword(email: String){
        firebaseRepository.resatPassword(email)
    }

    fun saveUserInformation(user: User){
        firebaseRepository.saveUserInformation(user)

    }

}
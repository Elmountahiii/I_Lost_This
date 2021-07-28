package com.redgunner.ilostthis.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.redgunner.ilostthis.utils.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseRepository @Inject constructor() {


     private val authenticator = FirebaseAuth.getInstance()

    val authSgInSuccessful=MutableLiveData<Boolean>()
    val authLogInSuccessful=MutableLiveData<Boolean>()
    val addInformationSuccessful=MutableLiveData<Boolean>()
    private val firebaseDatabase = FirebaseFirestore.getInstance()



    private val collectionsPath = "Users Information's"







    fun logIn(email: String, password: String) {

        authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        authLogInSuccessful.value=true
                    }

                }
                .addOnFailureListener { exception ->
                    authLogInSuccessful.value=false


                }
    }

    fun signIn(email: String, password: String) {

        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        authSgInSuccessful.value=true


                    }

                }.addOnFailureListener { exception ->
                    authSgInSuccessful.value=false

                }

    }

    fun resatPassword(email: String) {
        authenticator
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                    } else if (result.isCanceled) {

                    }

                }
                .addOnFailureListener { exception ->

                }
    }

    fun checkUser(): Boolean {
        return authenticator.currentUser != null
    }

    fun saveUserInformation(user: User) {
        firebaseDatabase
                .collection(collectionsPath)
                .document(authenticator.currentUser!!.uid)
                .set(user)
                .addOnSuccessListener {


                    addInformationSuccessful.value=true


                }
                .addOnFailureListener { exception ->
                    addInformationSuccessful.value=false

                }
    }
}
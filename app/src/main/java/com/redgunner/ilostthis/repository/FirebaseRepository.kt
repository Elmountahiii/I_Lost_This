package com.redgunner.ilostthis.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.redgunner.ilostthis.utils.SingleLiveEvent
import com.redgunner.ilostthis.utils.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseRepository @Inject constructor() {


    private val authenticator = FirebaseAuth.getInstance()

    val authSgInSuccessful = MutableLiveData<Boolean>()
    val authLogInSuccessful = MutableLiveData<Boolean>()


    val socialMediaAuthSuccessful = MutableLiveData<Boolean>()
    val addInformationSuccessful = MutableLiveData<Boolean>()
    val restPasswordResult = SingleLiveEvent<String>()
    val firebaseRegResult = SingleLiveEvent<String>()


    private val firebaseDatabase = FirebaseFirestore.getInstance()


    private val collectionsPath = "Users Information's"


    fun facebookAuthentication(FacebookAccessToken: AccessToken) {

        val authCredential = FacebookAuthProvider.getCredential(FacebookAccessToken.token)

        authentic(authCredential)

    }

    fun googleAuthentication(GoogleAccount: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(GoogleAccount.idToken, null)
        authentic(authCredential)


    }

    private fun authentic(authCredential: AuthCredential) {
        authenticator.signInWithCredential(authCredential)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        checkUserInformation(authenticator.currentUser!!.uid)
                        firebaseRegResult.value="Done"
                    }

                }
                .addOnFailureListener { exception ->
                    firebaseRegResult.value=exception.message
                }

    }


    private fun checkUserInformation(userId: String) {

        firebaseDatabase
                .collection(collectionsPath)
                .document(authenticator.currentUser!!.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->

                    socialMediaAuthSuccessful.value = documentSnapshot.exists()


                }
                .addOnFailureListener { exception ->

                }


    }


    fun logIn(email: String, password: String) {

        authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        authLogInSuccessful.value = true
                        firebaseRegResult.value="Done"
                    }

                }
                .addOnFailureListener { exception ->
                    authLogInSuccessful.value = false
                    firebaseRegResult.value=exception.message


                }
    }

    fun signIn(email: String, password: String) {

        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        authSgInSuccessful.value = true

                        firebaseRegResult.value="Done"


                    }

                }.addOnFailureListener { exception ->
                    authSgInSuccessful.value = false
                    firebaseRegResult.value= exception.message
                }

    }

    fun resatPassword(email: String) {
        authenticator
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        restPasswordResult.value = "We Sent You a Link Please Check Your Email"
                    } else if (result.isCanceled) {
                        restPasswordResult.value = "Canceled"

                    }

                }
                .addOnFailureListener { exception ->
                    restPasswordResult.value = exception.message

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


                    addInformationSuccessful.value = true


                }
                .addOnFailureListener { exception ->
                    addInformationSuccessful.value = false

                }
    }
}
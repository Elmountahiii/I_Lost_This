package com.redgunner.ilostthis.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.redgunner.ilostthis.utils.FoundItem
import com.redgunner.ilostthis.utils.LostItem
import com.redgunner.ilostthis.utils.SingleLiveEvent
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LostAndFoundFireBaseRepository @Inject constructor(){

    private val authenticator = FirebaseAuth.getInstance()

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    private val lostCollectionsPath =firebaseDatabase.collection("Lost Items")
    private val foundCollectionsPath = firebaseDatabase.collection("Found Items")


    val addItemSuccessful= SingleLiveEvent<Boolean>()


    val lostItemsList=MutableLiveData<MutableList<LostItem>>()
    val foundItemsList=MutableLiveData<MutableList<FoundItem>>()








    fun addToLostDatabase(lostItem:LostItem){

        lostItem.postId=System.currentTimeMillis().toString()
        lostItem.userId=authenticator.currentUser!!.uid
        lostItem.date= Calendar.getInstance().time.toString()
        lostCollectionsPath
                .add(lostItem).addOnSuccessListener {



                    addItemSuccessful.value=true


                }
                .addOnFailureListener { exception ->
                    addItemSuccessful.value=false

                }
    }

    fun addToFoundDatabase(foundItem:FoundItem){

        foundItem.postId=System.currentTimeMillis().toString()
        foundItem.userId=authenticator.currentUser!!.uid
        foundItem.date= Calendar.getInstance().time.toString()
        foundCollectionsPath
                .add(foundItem).addOnSuccessListener {



                    addItemSuccessful.value=true


                }
                .addOnFailureListener { exception ->
                    addItemSuccessful.value=false

                }
    }


    fun getLostItems(){
        lostCollectionsPath.
        orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty){

                        lostItemsList.value=result.toObjects(LostItem::class.java)
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d("7rira", "Error getting documents: ", exception)
                }
    }

    fun getFoundItems(){
        foundCollectionsPath.
        orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty){

                        foundItemsList.value=result.toObjects(FoundItem::class.java)
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d("7rira", "Error getting documents: ", exception)
                }
    }


}
package com.redgunner.ilostthis.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.redgunner.ilostthis.utils.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LostAndFoundFireBaseRepository @Inject constructor() {

    private val authenticator = FirebaseAuth.getInstance()

    private val firebaseDatabase = FirebaseFirestore.getInstance()


    private val lostCollectionsPath = firebaseDatabase.collection("Lost Items")
    private val foundCollectionsPath = firebaseDatabase.collection("Found Items")

    private val locationCollectionsPath = firebaseDatabase.collection("Locations")



    val addItemSuccessful = SingleLiveEvent<Boolean>()

    val lostItem=SingleLiveEvent<LostItem>()
    val foundItem=SingleLiveEvent<FoundItem>()
    val userInformation=SingleLiveEvent<User>()
    val locationList=SingleLiveEvent<List<Location>>()

    val lostItemsList = MutableLiveData<MutableList<LostItem>>()
    val lostItemsListByCategory = MutableLiveData<List<LostItem>>()
    val lostItemsListByLocation = MutableLiveData<List<LostItem>>()

    val foundItemsListByCategory = MutableLiveData<List<FoundItem>>()
    val foundItemsListByLocation = MutableLiveData<List<FoundItem>>()



    val foundItemsList = MutableLiveData<MutableList<FoundItem>>()



    fun addToLostDatabase(lostItem: LostItem, imageUri: Uri) {

        lostItem.postId = System.currentTimeMillis().toString()
        lostItem.userId = authenticator.currentUser!!.uid
        lostItem.date = Calendar.getInstance().time.toString()

        val storageRef = FirebaseStorage.getInstance().reference.child("images/${lostItem.date}")

        val uploadTask = storageRef.putFile(imageUri)

        addToLocation(lostItem.place)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful){
                val imageUrl=task.result
                lostItem.imageUrl=imageUrl.toString()
                lostCollectionsPath
                        .add(lostItem).addOnSuccessListener {


                            addItemSuccessful.value = true


                        }
                        .addOnFailureListener { exception ->
                            addItemSuccessful.value = false

                        }


            }

        }



    }

    fun addToFoundDatabase(foundItem: FoundItem, imageUri: Uri) {

        foundItem.postId = System.currentTimeMillis().toString()
        foundItem.userId = authenticator.currentUser!!.uid
        foundItem.date = Calendar.getInstance().time.toString()

        addToLocation(foundItem.place)


        val storageRef = FirebaseStorage.getInstance().reference.child("images/${foundItem.date}")

        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful){
                val imageUrl=task.result
                foundItem.imageUrl=imageUrl.toString()

                foundCollectionsPath
                        .add(foundItem).addOnSuccessListener {


                            addItemSuccessful.value = true


                        }
                        .addOnFailureListener { exception ->
                            addItemSuccessful.value = false

                        }

            }

        }

    }


    private fun addToLocation(location: String){
        locationCollectionsPath.add(Location(location))
    }


    fun getLostItems() {
        lostCollectionsPath.orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty) {

                        lostItemsList.value = result.toObjects(LostItem::class.java)
                    }

                }
                .addOnFailureListener { exception ->
                }
    }

    fun getFoundItems() {
        foundCollectionsPath.orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty) {

                        foundItemsList.value = result.toObjects(FoundItem::class.java)
                    }

                }
                .addOnFailureListener { exception ->
                }
    }


    fun getLostPost(postId:String){

        lostCollectionsPath
                .whereEqualTo("postId",postId)
                .get()
                .addOnSuccessListener {posts->
                    if (!posts.isEmpty){

                        for (post in posts){
                            lostItem.value=post.toObject(LostItem::class.java)
                            getUserInformation(lostItem.value!!.userId)


                        }
                    }

                }

    }

    fun getFoundPost(postId:String){
        foundCollectionsPath
                .whereEqualTo("postId",postId)
                .get()
                .addOnSuccessListener {posts->
                    if (!posts.isEmpty){

                        for (post in posts){
                            foundItem.value=post.toObject(FoundItem::class.java)
                            getUserInformation(foundItem.value!!.userId)


                        }
                    }

                }
    }


    private fun getUserInformation(userId:String){
        firebaseDatabase.collection("Users Information's")
                .document(userId)
                .get()
                 .addOnSuccessListener { documentSnapshot ->

                     if (documentSnapshot.exists()){
                         userInformation.value=documentSnapshot.toObject(User::class.java)
                     }



        }
                .addOnFailureListener { exception ->

                }
    }


    fun getLostItemsByCategory(category: String){

        lostCollectionsPath.whereEqualTo("category",category)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty) {

                        lostItemsListByCategory.value = result.toObjects(LostItem::class.java)

                    }else{
                        lostItemsListByCategory.value= emptyList()
                    }

                }
                .addOnFailureListener { exception ->
                }
    }

    fun getLostItemsByLocation(location: String){
        lostCollectionsPath.whereEqualTo("place",location)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty) {

                        lostItemsListByLocation.value = result.toObjects(LostItem::class.java)
                    }else{
                        lostItemsListByLocation.value= emptyList()
                    }

                }
                .addOnFailureListener { exception ->
                }
    }

    fun getFoundItemsByCategory(category: String){

        foundCollectionsPath.whereEqualTo("category",category)
                .get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) {

                        foundItemsListByCategory.value = result.toObjects(FoundItem::class.java)

                    }else{
                        foundItemsListByCategory.value= emptyList()
                    }

                }
                .addOnFailureListener { exception ->
                }
    }

    fun getFoundItemsByLocation(location: String){
        foundCollectionsPath.whereEqualTo("place",location)
                .get()
                .addOnSuccessListener { result ->

                    if (!result.isEmpty) {

                        foundItemsListByLocation.value = result.toObjects(FoundItem::class.java)
                    }else{
                        foundItemsListByLocation.value= emptyList()
                    }

                }
                .addOnFailureListener { exception ->
                }
    }

    fun getLocations(){
        locationCollectionsPath.
        get()
                .addOnSuccessListener { locations ->
                    if (!locations.isEmpty){
                        for (location in locations){
                            locationList.value= locations.toObjects(Location::class.java)
                        }
                    }

                }
    }


}
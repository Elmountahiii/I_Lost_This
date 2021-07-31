package com.redgunner.ilostthis.viewModel

import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.redgunner.ilostthis.repository.LostAndFoundFireBaseRepository
import com.redgunner.ilostthis.utils.FoundItem
import com.redgunner.ilostthis.utils.LostItem

class MainViewModel @ViewModelInject constructor(
        private val firebaseRepository: LostAndFoundFireBaseRepository) : ViewModel() {


    val addItemSuccessful by lazy {
        firebaseRepository.addItemSuccessful
    }
    val lostItems by lazy {
        firebaseRepository.lostItemsList
    }
    val foundItems by lazy {
        firebaseRepository.foundItemsList
    }
    val lostItem by lazy {
        firebaseRepository.lostItem
    }
    val foundItem by lazy {
        firebaseRepository.foundItem
    }
    val userInformation by lazy {
        firebaseRepository.userInformation
    }
    val lostItemsListByCategory by lazy {
        firebaseRepository.lostItemsListByCategory
    }
    val lostItemsListByLocation by lazy {
        firebaseRepository.lostItemsListByLocation
    }
    val foundItemsListByCategory by lazy {
        firebaseRepository.foundItemsListByCategory
    }
    val foundItemsListByLocation by lazy {
        firebaseRepository.foundItemsListByLocation
    }
    val locationList by lazy {
        firebaseRepository.locationList
    }


    fun getLostItems() {
        firebaseRepository.getLostItems()

    }

    fun getFoundItem() {
        firebaseRepository.getFoundItems()
    }

    fun addLostItem(lostItem: LostItem, imageUri: Uri) {


        firebaseRepository.addToLostDatabase(lostItem, imageUri)
    }

    fun addFoundItem(foundItem: FoundItem, imageUri: Uri) {

        firebaseRepository.addToFoundDatabase(foundItem, imageUri)

    }

    fun getLostPost(postId: String) {
        firebaseRepository.getLostPost(postId)
    }

    fun getFoundPost(postId: String) {
        firebaseRepository.getFoundPost(postId)
    }

    fun getLostItemsByCategory(category: String){
      firebaseRepository.getLostItemsByCategory(category)
    }

    fun getLostItemsByLocation(location: String){
       firebaseRepository.getLostItemsByLocation(location)
    }

    fun getFoundItemsByCategory(category: String){
        Log.d("BigKoma","in viewmodel")

        firebaseRepository.getFoundItemsByCategory(category)
    }

    fun getFoundItemsByLocation(location: String){
       firebaseRepository.getFoundItemsByLocation(location)
    }

    fun getLocations(){
       firebaseRepository.getLocations()
    }


}
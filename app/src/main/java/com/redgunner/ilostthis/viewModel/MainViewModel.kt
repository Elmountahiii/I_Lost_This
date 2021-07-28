package com.redgunner.ilostthis.viewModel

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



   fun getLostItems(){
       firebaseRepository.getLostItems()

   }
    fun getFoundItem(){
        firebaseRepository.getFoundItems()
    }

    fun addLostItem(lostItem: LostItem) {


        firebaseRepository.addToLostDatabase(lostItem)
    }

    fun addFoundItem(foundItem: FoundItem) {

        firebaseRepository.addToFoundDatabase(foundItem)

    }


}
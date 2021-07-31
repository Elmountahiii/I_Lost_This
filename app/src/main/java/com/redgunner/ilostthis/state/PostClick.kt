package com.redgunner.ilostthis.state

sealed class PostClick
{

    data class ItemLost(val postId:String, val isLost:Boolean):PostClick()
    data class ItemFound(val postId:String, val isLost:Boolean):PostClick()


}

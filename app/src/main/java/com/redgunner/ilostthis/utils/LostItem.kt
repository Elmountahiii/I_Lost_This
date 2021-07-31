package com.redgunner.ilostthis.utils

import android.net.Uri

data class LostItem(
        var postId: String = "",
        var userId: String = "",
        var name:String="",
        var title: String = "",
        var place: String = "",
        var date: String = "",
        var category: String = "",
        var description: String = "",
        var imageUrl:String="")

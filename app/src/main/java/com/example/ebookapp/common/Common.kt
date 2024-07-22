package com.example.ebookapp.common

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error<T>(val exception: Throwable) : ResultState<T>()
    data object Loading : ResultState<Nothing>()
}

data class BookModel(
    val bookUrl:String = "",
    val bookName : String = "",
    val category : String = "",
    val img : String = ""

)
data class BookCategoryModel(
    val name:String = "",
    val desc : String = "",
    val img : String = ""
)

data class TabsItem(val id: Int, val name: String, val icon: ImageVector)
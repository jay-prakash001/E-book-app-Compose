package com.example.ebookapp.presentation.navigation

import com.example.ebookapp.common.BookModel
import com.google.android.gms.common.internal.Objects
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed class NavigationItems {
    @Serializable
    object HomeScreen

    @Serializable
    data class BookCategoryScreen(val category: String)

    @Serializable
    data class ShowPdf(val pdfLink: String)

    @Serializable
    object AddBookScreen

    @Serializable
    object AddCategoryScreen

    @Serializable
    data class CardDetailScreen(
        val bookUrl: String,
        val bookName: String ,
        val category: String ,
        val img: String
    )
}
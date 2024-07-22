package com.example.ebookapp.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ebookapp.common.BookModel
import com.example.ebookapp.presentation.screens.AddNewBook
import com.example.ebookapp.presentation.screens.AddNewCategory
import com.example.ebookapp.presentation.screens.BooksByCategory
import com.example.ebookapp.presentation.screens.CardDetails
import com.example.ebookapp.presentation.screens.ShowPdf
import com.example.ebookapp.presentation.screens.TabLayoutScreen

@Composable
fun NavApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationItems.HomeScreen) {

        composable<NavigationItems.HomeScreen> {
            TabLayoutScreen(navController = navController)
        }
        composable<NavigationItems.BookCategoryScreen> {
            val category = it.toRoute<NavigationItems.BookCategoryScreen>()
            BooksByCategory(category = category.category, navController = navController)
        }
        composable<NavigationItems.ShowPdf> {
            Text(text = "hello")
            val pdfLink = it.toRoute<NavigationItems.ShowPdf>().pdfLink
            ShowPdf(pdfLink = pdfLink)
        }

        composable<NavigationItems.AddBookScreen> {
            AddNewBook(navController = navController)
        }
        composable<NavigationItems.AddCategoryScreen> {
            AddNewCategory(navController = navController)
        }
        composable<NavigationItems.CardDetailScreen> {
            val bookData = it.toRoute<NavigationItems.CardDetailScreen>()
            val book = BookModel(
                bookUrl = bookData.bookUrl,
                bookName = bookData.bookName,
                category = bookData.category,
                img = bookData.img
            )
            CardDetails(book = book, navController = navController)
        }

    }
}

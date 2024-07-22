package com.example.ebookapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.ebookapp.R
import com.example.ebookapp.common.BookModel
import com.example.ebookapp.presentation.navigation.NavigationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetails(modifier: Modifier = Modifier, book: BookModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = book.bookName.capitalize()) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back button",
                        modifier = Modifier.clickable {
                            navController.navigateUp()
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = book.img,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.ic_launcher_background)
                            error(R.drawable.ic_launcher_foreground)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "Book Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = book.bookName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Category: ${book.category}",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedButton(onClick = { navController.navigate(NavigationItems.ShowPdf(book.bookUrl)) }) {
                Text(text = "Read Book")
            }
           
          
        }
    }
}
package com.example.ebookapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ebookapp.presentation.viewModels.BookViewModel
import com.example.ebookapp.presentation.navigation.NavigationItems

@Composable
fun BooksByCategory(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    category: String,
    navController: NavHostController
) {

    LaunchedEffect(key1 = true) {
        viewModel.getBooksByCategory(category)
    }

    val data = viewModel.state.value
    if (data.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (data.error.isNotEmpty()) {
        Text(text = data.error.toString())
    } else {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(120.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(data.books) {
                    Card(
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
//                                navController.navigate(NavigationItems.ShowPdf(it.bookUrl))
                                navController.navigate(
                                    NavigationItems.CardDetailScreen(
                                        it.bookUrl,
                                        it.bookName,
                                        it.category,
                                        it.img
                                    )
                                )
                            },
                        elevation = CardDefaults.elevatedCardElevation(5.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            AsyncImage(
                                model = it.img,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clip(
                                        RoundedCornerShape(5.dp)
                                    )
                            )

                            Text(
                                text = it.bookName.capitalize(),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface

                            )
                            Text(
                                text = it.toString(),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface

                            )

                        }
                    }

                }
            },
            modifier = Modifier.fillMaxSize()
        )


    }


}


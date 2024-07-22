package com.example.ebookapp.presentation.screens

import android.view.animation.ScaleAnimation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var showText by remember {
        mutableStateOf(true)
    }
//    LaunchedEffect(key1 = true) {
//        viewModel.getAllBookCategories()
//    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(NavigationItems.AddCategoryScreen)
        }) {
            AnimatedVisibility(visible = showText) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

            }
            AnimatedVisibility(visible = !showText) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add new category")

            }


        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val categories = viewModel.state.value.categories
            if (categories.isEmpty()) {
                LinearProgressIndicator()

            } else {
                showText = false

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(120.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        items(categories) {
                            Card(
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable {
                                        navController.navigate(NavigationItems.BookCategoryScreen(it.name))

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
                                        text = it.name.capitalize(),
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
    }

}
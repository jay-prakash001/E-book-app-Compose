package com.example.ebookapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.ebookapp.common.TabsItem
import com.example.ebookapp.presentation.navigation.NavigationItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayoutScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabsArea(pagerState)
        TabContent(pagerState = pagerState, navController = navController)

    }

}

@Composable
fun TabContent(pagerState: PagerState, navController: NavHostController) {

    HorizontalPager(state = pagerState) {
        when (it) {
            0 -> {
                CategoryScreen(navController = navController)
            }

            else -> {
                AllBookScreen(navController = navController)
            }
        }
    }


}

@Composable
fun TabsArea(pagerState: PagerState) {
    val tabs = arrayOf(
        TabsItem(0, "categoryScreen", Icons.Default.Category),
        TabsItem(1, "allBooks", Icons.Default.Book)
    )

    val coroutineScope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage) {
        tabs.forEachIndexed { index, tab ->
            Tab(icon = {
                Icon(imageVector = tab.icon, contentDescription = "")

            }, selected = pagerState.currentPage == index, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)


                }

            }, text = {
                Text(text = tab.name.capitalize())

            })
        }
    }
}



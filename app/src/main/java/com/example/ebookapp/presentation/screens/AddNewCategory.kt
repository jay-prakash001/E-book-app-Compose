package com.example.ebookapp.presentation.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ebookapp.common.BookCategoryModel
import com.example.ebookapp.presentation.viewModels.BookViewModel


@Composable
fun AddNewCategory(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    navController: NavHostController
) {
    BookDetails(viewModel, navController)

}


@Composable
private fun BookDetails(
    viewModel: BookViewModel,
    navController: NavHostController
) {
    val categoryName = rememberSaveable {
        mutableStateOf("")
    }
    val categoryDesc = rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DocumentPicker(viewModel, true)

        OutlinedTextField(value = categoryName.value, onValueChange = {
            categoryName.value = it
        }, label = {
            Text(text = "Enter Category Name.")
        }, modifier = Modifier.fillMaxWidth(.9f))

        OutlinedTextField(value = categoryDesc.value, onValueChange = {
            categoryDesc.value = it
        }, label = {
            Text(text = "Enter Category Description.")
        }, modifier = Modifier.fillMaxWidth(.9f))
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewModel.addNewBookCategoryToDb(
                    BookCategoryModel(
                        name = categoryName.value,
                        desc = categoryDesc.value,
                        img = viewModel.imgUrl.value
                    )
                )
                navController.navigateUp()
            },
            enabled = (viewModel.imgUrl.collectAsState().value.isNotBlank())
        ) {
            Text(text = "Save")
        }
    }
}

package com.example.ebookapp.presentation.screens

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ebookapp.common.BookModel
import com.example.ebookapp.presentation.viewModels.BookViewModel
import kotlin.math.sin

@Composable
fun AddNewBook0(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val bookName = rememberSaveable {
            mutableStateOf("")
        }

        val category = rememberSaveable {
            mutableStateOf("")
        }

        OutlinedTextField(value = bookName.value, onValueChange = {
            bookName.value = it
        }, label = {
            Text(text = "Enter Book Name.")
        }, modifier = Modifier.fillMaxWidth(.9f))
        DocumentPicker(viewModel, true)

        val showDropdownMenuBoxScope = remember {
            mutableStateOf(false)
        }
        val enabled = rememberSaveable {
            mutableStateOf(true)
        }
        if (viewModel.pdfUrl.collectAsState().value.isNullOrBlank() || viewModel.imgUrl.collectAsState().value.isNullOrBlank()) {
            enabled.value = false
        }
        val categories = viewModel.state.value.categories
        Row(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .clickable {
                    showDropdownMenuBoxScope.value = !showDropdownMenuBoxScope.value
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = if (category.value.isNullOrBlank()) "Select Category" else category.value.capitalize())
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "drop down menu")
        }
        DropdownMenu(expanded = showDropdownMenuBoxScope.value, onDismissRequest = {
            showDropdownMenuBoxScope.value = false
        }) {
            categories.forEach {
                DropdownMenuItem(text = { Text(text = it.name.capitalize()) }, onClick = {
                    category.value = it.name
                    showDropdownMenuBoxScope.value = false
                })
            }

        }
        DocumentPicker(viewModel, false)


        Button(onClick = {
            viewModel.addNewBookToDb(
                BookModel(
                    bookUrl = viewModel.pdfUrl.value,
                    bookName = bookName.value,
                    category = category.value,
                    img = viewModel.imgUrl.value
                )
            )
            navController.navigateUp()

        }) {
            Text(text = "Save")
        }

    }
}

@Composable
fun AddNewBook(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bookName = rememberSaveable {
            mutableStateOf("")
        }
        val category = rememberSaveable {
            mutableStateOf("")
        }

        val showDropdownMenuBoxScope = remember {
            mutableStateOf(false)
        }
        val categories = viewModel.state.value.categories


        DocumentPicker(viewModel, true)
        TextField(value = bookName.value, onValueChange = {
            bookName.value = it
        }, label = {
            Text(text = "book name")
        }, modifier = Modifier.fillMaxWidth(.9f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)

                    .fillMaxWidth(.9f)
                    .clickable {
                        showDropdownMenuBoxScope.value = !showDropdownMenuBoxScope.value
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = if (category.value.isNullOrBlank()) "Select Category" else category.value.capitalize())
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "drop down menu"
                )

            }
            DropdownMenu(expanded = showDropdownMenuBoxScope.value, onDismissRequest = {
                showDropdownMenuBoxScope.value = false
            }) {
                categories.forEach {
                    DropdownMenuItem(text = { Text(text = it.name.capitalize()) }, onClick = {
                        category.value = it.name
                        showDropdownMenuBoxScope.value = false
                    })
                }

            }
        }



        DocumentPicker(viewModel, false)
        Text(
            text = "Note: Please check the name of the file before selecting and uploading.",
            fontSize = 8.sp,
            color = MaterialTheme.colorScheme.error
        )
        Button(
            onClick = {
                viewModel.addNewBookToDb(
                    BookModel(
                        viewModel.pdfUrl.value,
                        bookName = bookName.value,
                        category = category.value,
                        img = viewModel.imgUrl.value
                    )
                )
                navController.navigateUp()
            },
            enabled = (viewModel.imgUrl.collectAsState().value.isNotBlank() && viewModel.pdfUrl.collectAsState().value.isNotBlank())
        ) {
            Text(text = "Upload")
        }


    }
}
package com.example.ebookapp.presentation.screens

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebookapp.presentation.viewModels.BookViewModel


@Composable
fun DocumentPicker(viewModel: BookViewModel, isCoverImage: Boolean) {
    val byteImg = rememberSaveable {
        mutableStateOf(byteArrayOf())
    }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            try {
                if (uri != null) {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val tempImg = inputStream?.readBytes()

                    if (tempImg != null) {
                        byteImg.value = tempImg
                    }
                }
            }catch (e:Exception){
                Toast.makeText(context, e.localizedMessage.capitalize(), Toast.LENGTH_SHORT).show()
            }
        }

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(
                if (isCoverImage) {
                    .6f
                } else {
                    .2f
                }
            ),
        elevation = CardDefaults.elevatedCardElevation(5.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isCoverImage) {

                if (byteImg.value.contentEquals(byteArrayOf())) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            launcher.launch("image/*")
                        })
                } else {
                    val bitMap = BitmapFactory.decodeByteArray(byteImg.value, 0, byteImg.value.size)
                    println("byteImg :$byteImg ::$bitMap")

                    Image(bitmap = bitMap.asImageBitmap(),
                        contentDescription = "",
                        contentScale = if (viewModel.state.value.isZoomed.collectAsState().value) {
                            ContentScale.Crop
                        } else {
                            ContentScale.Fit
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                viewModel.state.value.isZoomed.value =
                                    !viewModel.state.value.isZoomed.value
                            })
                }
            } else {
                Icon(
                    imageVector = Icons.Default.DocumentScanner,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        launcher.launch("*/*")
                    })

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {

                IconButton(
                    onClick = {
                        viewModel.clearUrls(if (isCoverImage) 1 else 2)
                        if (isCoverImage) {

                            viewModel.uploadImg(byteImg.value)
                        } else {
                            viewModel.uploadPdf(byteImg.value)

                        }


                    },
                    colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary),
                    enabled = (viewModel.imgUrl.collectAsState().value.isNotBlank() && viewModel.pdfUrl.collectAsState().value.isNotBlank())
                ) {
                    Icon(
                        imageVector = Icons.Default.Upload,
                        contentDescription = "upload",
                        modifier = Modifier
                            .size(50.dp),
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }

                IconButton(
                    onClick = {

                        launcher.launch("*/*")
                    },
                    colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.onPrimaryContainer),
                    enabled = (viewModel.imgUrl.collectAsState().value.isNotBlank() && viewModel.pdfUrl.collectAsState().value.isNotBlank())
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp), tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }


            }


            if (isCoverImage) {
                if (viewModel.imgUrl.collectAsState().value.isBlank()) {
                    CircularProgressIndicator()
                }
            } else {
                if (viewModel.pdfUrl.collectAsState().value.isBlank()) {
                    CircularProgressIndicator()
                }
            }


        }
    }
}
package com.example.ebookapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun ShowPdf(pdfLink: String) {
    Scaffold() {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            val pdfState = rememberVerticalPdfReaderState(
                resource = ResourceType.Remote(pdfLink),
                isZoomEnable = true, isAccessibleEnable = true
            )

            VerticalPDFReader(
                state = pdfState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
            )
        }
    }
}
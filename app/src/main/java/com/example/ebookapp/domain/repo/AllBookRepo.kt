package com.example.ebookapp.domain.repo

import com.example.ebookapp.common.BookCategoryModel
import com.example.ebookapp.common.BookModel
import com.example.ebookapp.common.ResultState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow

interface AllBookRepo {

    fun getAllBook(): Flow<ResultState<List<BookModel>>>
    fun getAllBookCategories(): Flow<ResultState<List<BookCategoryModel>>>
    fun getBooksByCategory(category: String): Flow<ResultState<List<BookModel>>>
    fun addNewBook(bookModel: BookModel, id: Int)
    fun addNewBookCategory(bookCategoryModel: BookCategoryModel, id: Int)

    fun storageOp( byteArray: ByteArray) : String
    fun uploadImg(byteArray: ByteArray,name:String) : Flow<String>
    fun uploadPdf(byteArray: ByteArray,name:String) : Flow<String>
}
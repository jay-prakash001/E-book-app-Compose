package com.example.ebookapp.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebookapp.common.BookCategoryModel
import com.example.ebookapp.common.BookModel
import com.example.ebookapp.common.ResultState
import com.example.ebookapp.domain.repo.AllBookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repo: AllBookRepo) : ViewModel() {
    private val _itemState: MutableState<ItemsState> = mutableStateOf(ItemsState())
    val state: MutableState<ItemsState> = _itemState
    private val _pdfUrl = MutableStateFlow<String>("0")
    private val _imgUrl = MutableStateFlow<String>("0")
    val imgUrl = _imgUrl.asStateFlow()
    val pdfUrl = _pdfUrl.asStateFlow()

    init {

        getAllBooks()
        getAllBookCategories()
    }

    fun getAllBooks() {
        viewModelScope.launch {

            repo.getAllBook().collect {
                when (it) {
                    is ResultState.Error -> {
                        _itemState.value = ItemsState(error = it.exception.localizedMessage)
                    }

                    ResultState.Loading -> {
                        _itemState.value = ItemsState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _itemState.value =
                            ItemsState(books = it.data, categories = _itemState.value.categories)
                    }
                }

            }
        }
    }

    fun getBooksByCategory(category: String) {
        viewModelScope.launch {

            repo.getBooksByCategory(category).collect {
                when (it) {
                    is ResultState.Error -> {
                        _itemState.value = ItemsState(error = it.exception.localizedMessage)
                    }

                    ResultState.Loading -> {
                        _itemState.value = ItemsState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _itemState.value =
                            ItemsState(books = it.data, categories = _itemState.value.categories)
                    }
                }

            }
        }
    }

    fun getAllBookCategories() {
        viewModelScope.launch {

            repo.getAllBookCategories().collect {
                when (it) {
                    is ResultState.Error -> {
                        _itemState.value = ItemsState(error = it.exception.localizedMessage)
                    }

                    ResultState.Loading -> {
                        _itemState.value = ItemsState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _itemState.value =
                            ItemsState(categories = it.data, books = _itemState.value.books)
                    }
                }

            }
        }
    }


    fun addNewBookToDb(book: BookModel) {

        repo.addNewBook(bookModel = book, state.value.books.size + 1)
        clearUrls(0)
    }

    fun clearUrls(a: Int) {
        when (a) {
            1 -> {
                _imgUrl.value = ""

            }

            2 -> {

                    _pdfUrl.value = ""

            }

            else -> {
                _imgUrl.value = ""
                _pdfUrl.value = ""

            }
        }
    }

    fun addNewBookCategoryToDb(categoryModel: BookCategoryModel) {
        repo.addNewBookCategory(bookCategoryModel = categoryModel, state.value.categories.size + 1)
    }

    fun uploadPdf(byteArray: ByteArray) {
        viewModelScope.launch {
            repo.uploadPdf(byteArray, "Books/${Date().time}").collectLatest {
                _pdfUrl.value = it.toString()
            }

        }

    }

    fun uploadImg(byteArray: ByteArray) {
        viewModelScope.launch {
            repo.uploadImg(byteArray, "Images/${Date().time}").collectLatest {
                _imgUrl.value = it.toString()
            }

        }

    }
}

data class ItemsState(
    val books: List<BookModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val categories: List<BookCategoryModel> = emptyList(),
    var isZoomed: MutableStateFlow<Boolean> = MutableStateFlow(false)


)
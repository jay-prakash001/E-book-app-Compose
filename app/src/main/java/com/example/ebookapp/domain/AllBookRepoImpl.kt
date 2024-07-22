package com.example.ebookapp.domain

import android.util.Log
import com.example.ebookapp.common.BookCategoryModel
import com.example.ebookapp.common.BookModel
import com.example.ebookapp.common.ResultState
import com.example.ebookapp.domain.repo.AllBookRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AllBookRepoImpl @Inject constructor(
    val firebaseDatabase: FirebaseDatabase,
    val firebaseStorage: FirebaseStorage
) :
    AllBookRepo {
    override fun getAllBook(): Flow<ResultState<List<BookModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items = emptyList<BookModel>()

                items = snapshot.children.map { value ->

                    value.getValue(BookModel::class.java)!!

                }
                trySend(ResultState.Success(items))

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }


        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEventListener)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEventListener)
            close()
        }

    }

    override fun getAllBookCategories(): Flow<ResultState<List<BookCategoryModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var categories = emptyList<BookCategoryModel>()
                categories = snapshot.children.map { value ->
                    value.getValue(BookCategoryModel::class.java)!!
                }
                trySend(ResultState.Success(categories))

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }

        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEventListener)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEventListener)
            close()
        }
    }

    override fun getBooksByCategory(category: String): Flow<ResultState<List<BookModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var items = emptyList<BookModel>()

                    items = snapshot.children.filter {
                        it.getValue<BookModel>()!!.category.lowercase() == category.lowercase()

                    }.map { value ->

//                    value.getValue(BookModel::class.java)!!
                        value.getValue<BookModel>()!!

                    }
                    Log.d("DeBug", "onDataChange: $snapshot")
                    trySend(ResultState.Success(items))

                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.toException()))
                }


            }
            firebaseDatabase.reference.child("Books").addValueEventListener(valueEventListener)
            awaitClose {
                firebaseDatabase.reference.removeEventListener(valueEventListener)
                close()
            }
        }

    override fun addNewBook(bookModel: BookModel, id: Int) {
        firebaseDatabase.reference.child("Books").child(id.toString()).setValue(bookModel)

    }

    override fun addNewBookCategory(bookCategoryModel: BookCategoryModel, id: Int) {
        firebaseDatabase.reference.child("BookCategory").child(id.toString())
            .setValue(bookCategoryModel)
    }

    override fun storageOp(byteArray: ByteArray): String {
        var url = ""
        val storageRef = firebaseStorage.reference
        val ref = storageRef.child("images/${Date().time}")

        ref.putBytes(byteArray).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                url = it.toString()
                Log.d("Upload", "storageOp: ${it.toString()}")
                Log.d("Upload", "storageOp: ${it}")
            }

        }
        return url
    }

    override
    fun uploadImg(byteArray: ByteArray,name:String) = flow<String> {
        val storageRef = FirebaseStorage.getInstance().reference
        val ref = storageRef.child(name)

        val downloadUrl = suspendCancellableCoroutine<String> { continuation ->
            ref.putBytes(byteArray).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    if (uri.toString().isNotBlank()) {
                        continuation.resume(uri.toString())
                    } else {
                        continuation.resumeWithException(Exception("URL is blank"))
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
        println(downloadUrl+ " URL")
        emit(downloadUrl)
    }
    override
    fun uploadPdf(byteArray: ByteArray,name:String) = flow<String> {
        val storageRef = FirebaseStorage.getInstance().reference
        val ref = storageRef.child(name)

        val downloadUrl = suspendCancellableCoroutine<String> { continuation ->
            ref.putBytes(byteArray).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    if (uri.toString().isNotBlank()) {
                        continuation.resume(uri.toString())
                    } else {
                        continuation.resumeWithException(Exception("URL is blank"))
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
        println(downloadUrl+ " URL")
        emit(downloadUrl)
    }

}
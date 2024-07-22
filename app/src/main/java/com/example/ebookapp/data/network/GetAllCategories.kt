package com.example.ebookapp.data.network

import com.example.ebookapp.data.models.CategoryDTO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import javax.inject.Inject

class GetAllCategories @Inject constructor(val firebaseDatabase: FirebaseDatabase) {

    fun getAllCategories() {
        val categoryListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val category = snapshot.getValue<CategoryDTO>()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        firebaseDatabase.reference.child("BookCategory").addValueEventListener(categoryListener)

    }
}
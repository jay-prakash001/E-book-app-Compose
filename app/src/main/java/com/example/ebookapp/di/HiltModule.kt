package com.example.ebookapp.di

import com.example.ebookapp.domain.AllBookRepoImpl
import com.example.ebookapp.domain.repo.AllBookRepo
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideFirebaseRealtimeDB(): FirebaseDatabase {
//        return Firebase.database.reference
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepo(firebaseDatabase: FirebaseDatabase, firebaseStorage: FirebaseStorage): AllBookRepo {
        return AllBookRepoImpl(firebaseDatabase,firebaseStorage)
    }

}
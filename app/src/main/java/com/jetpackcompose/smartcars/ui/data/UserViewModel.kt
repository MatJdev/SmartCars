package com.jetpackcompose.smartcars.ui.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.jetpackcompose.smartcars.ui.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    val documentData = mutableStateOf<User?>(null)

    fun readDocument(documentId: String) {
        val documentReference = firestore.collection("users").document(documentId)

        viewModelScope.launch {
            try {
                val documentSnapshot = documentReference.get().await()
                val data = documentSnapshot.toObject(User::class.java)
                documentData.value = data
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }
        }
    }
}
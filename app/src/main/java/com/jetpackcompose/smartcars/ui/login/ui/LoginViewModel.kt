package com.jetpackcompose.smartcars.ui.login.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.jetpackcompose.smartcars.ui.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailPassword(email: String, password: String, home: () -> Unit)
        = viewModelScope.launch {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("Login", "Logueado")
            home()
        } catch (e: FirebaseAuthInvalidUserException) {
            showErrorDialog = true
            Log.d("Login", "FirebaseAuthInvalidUserException: ${e.message}")
        } catch (e: Exception) {
            Log.d("Login", "Exception: ${e.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    )
        = viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                Log.d("SignUp", "Registrado")
                home()
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d("SignUp", "FirebaseAuthInvalidCredentialsException: ${e.message}")
                showErrorDialog = true
            } catch (e: Exception) {
                Log.d("SignUp", "Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
    }

    fun createUserDataBase(email: String) {
        val db = FirebaseFirestore.getInstance()
        val coleccion = db.collection("users")
        val document = coleccion.document(auth.currentUser?.uid.toString())

        val name = email.substringBeforeLast("@")
        val img = "https://cdn3d.iconscout.com/3d/premium/thumb/trendy-person-avatar-6299537-5187869.png"

        val usuario = User(email = email, name = name, img = img)

        document.set(usuario, SetOptions.merge())
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->

            }
    }
}

package com.jetpackcompose.smartcars.ui.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.jetpackcompose.smartcars.ui.data.model.Car
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.jetpackcompose.smartcars.ui.data.model.SearchWidgetState
import androidx.compose.runtime.State


class DataViewModel : ViewModel() {
    val state = mutableStateOf(ArrayList<Car?>())
    val selectedBrand = mutableStateOf("")


    init {
        getData()
    }

    private fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            state.value = getDataFromFireStore(selectedBrand)
        }
    }

    fun filterCarsByBrand(brand: String) {
        if (brand.isEmpty()) {
            getData()
        } else {
            val filteredCars = state.value.filter { car ->
                car?.marca?.contains(brand, ignoreCase = true) == true ||
                        car?.modelo?.contains(brand, ignoreCase = true) == true
            }
            state.value = ArrayList(filteredCars)
        }
    }


    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}


suspend fun getDataFromFireStore(selectedBrand: MutableState<String>): ArrayList<Car?> {


    val db = FirebaseFirestore.getInstance()

    val coleccion = db.collection("cars")
    val querySnapshot = coleccion.get().await()
    val coches = querySnapshot.documents.map { doc ->
        doc.toObject(Car::class.java)
    } as ArrayList<Car?>

    Log.i("Datos en dataview", coches.toString())
    return coches

    //var car = Car()
    //var carsArray = ArrayList<Car>()
    /*val locations = listOf(
        "Model S",
        "500e"
    ).shuffled()*/

    //Este funciona para traer solo 1
    /*try {
        db.collection("cars").whereEqualTo("modelo", modelo).get().await().map {
            val result = it.toObject(Car::class.java)
            car = result
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("error", "getDataFromFireStore: $e")
    }*/


    //Descomentar esto
    /*try {
        db.collection("cars").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val coche = document.toObject(Car::class.java)
                coche.marca = document["marca"].toString()
                coche.modelo = document["modelo"].toString()
                coche.motor = document["motor"].toString()
                coche.img = document["img"].toString()
                coche.aceleracion = document["aceleracion"].toString()
                coche.bateria = document["bateria"].toString()
                coche.carga = document["carga"].toString()
                coche.maletero = document["maletero"].toString()
                coche.precio = document["precio"].toString()

                carsArray.add(coche)
                Log.i("Coche", coche.toString())
                Log.i("Datos Dentro", carsArray.toString())
            }
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
        Log.i("Datos Fuera", carsArray.toString())
    } catch (e: FirebaseFirestoreException) {
        Log.d("error", "getDataFromFireStore: $e")
    }*/
    /*var tareas: ArrayList<Car?> = ArrayList()
    val coleccion = db.collection("cars")
    coleccion.get().addOnSuccessListener { querySnapshot ->
        tareas = querySnapshot.documents.map { doc ->
            doc.toObject(Car::class.java)
        } as ArrayList<Car?>
        // hacer algo con las tareas

    }*/


    //return tareas
}

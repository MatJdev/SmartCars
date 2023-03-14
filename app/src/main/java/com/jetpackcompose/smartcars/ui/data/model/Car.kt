package com.jetpackcompose.smartcars.ui.data.model

data class Car(
    var marca: String = "",
    var modelo: String = "",
    var motor: String = "",
    var img: String = "",
    var aceleracion: String = "",
    var bateria: String = "",
    var carga: String = "",
    var maletero: String = "",
    var precio: String = "",
    var latitud: Double = 0.0,
    var longitud: Double = 0.0
)

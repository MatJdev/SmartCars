package com.jetpackcompose.smartcars.ui.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyArgs(
    val marca: String,
    val modelo: String,
    val shouldexp: Boolean?,
    val img: String,
    val motor: String,
    val distancia: Double,
    val bateria: String,
    val precio: String,
    val aceleracion: String,
    val maletero: String
) : Parcelable

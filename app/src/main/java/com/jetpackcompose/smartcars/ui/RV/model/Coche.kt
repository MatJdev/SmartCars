package com.jetpackcompose.smartcars.ui.RV.model

import androidx.annotation.DrawableRes

data class Coche(

    var marca: String,
    var modelo: String,
    var tipo: String,
    @DrawableRes var imagen: Int
)

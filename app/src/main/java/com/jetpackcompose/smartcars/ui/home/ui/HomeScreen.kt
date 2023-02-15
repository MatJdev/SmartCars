package com.jetpackcompose.smartcars.ui.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens

@Composable
fun HomeScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (info, BoxDark, btn, text) = createRefs()
        val guiaHorizontalTop = createGuidelineFromTop(0.2f)
        val guiaVerticalTop = createGuidelineFromStart(0.7f)

        Box(modifier = Modifier
            .background(color = Color(0xFF2C2B34))
            .constrainAs(BoxDark) {}
            .fillMaxSize()) {

        }


        TextButton(onClick = {
            navController.navigate(route = AppScreens.SignupScreen.route)
        },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF2C2B34)
            ), modifier = Modifier.constrainAs(info){
                top.linkTo(guiaHorizontalTop, margin = 10.dp)
                start.linkTo(parent.start, margin = 50.dp)
            }
        ) {
            Text(text = "Information")
        }

        Text( text = "Home",
            modifier = Modifier.constrainAs(text){
                top.linkTo(guiaHorizontalTop, margin = 80.dp)
                start.linkTo(parent.start, margin = 30.dp)
            },
            color = Color.White,
            fontSize = 35.sp)


    }

}

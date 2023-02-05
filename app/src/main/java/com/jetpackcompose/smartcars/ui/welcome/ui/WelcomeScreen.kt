package com.jetpackcompose.smartcars.ui.welcome.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (foto, BoxDark, btn, text) = createRefs()
        val guiaHorizontalTop = createGuidelineFromTop(0.2f)
        val guiaVerticalTop = createGuidelineFromStart(0.7f)

        Box(modifier = Modifier
            .background(color = Color(0xFF2C2B34))
            .constrainAs(BoxDark) {}
            .fillMaxSize()) {

        }


        Image(painter = painterResource(id = R.drawable.tesla1), contentDescription = ""
            , modifier = Modifier
                .fillMaxWidth()
                .constrainAs(foto) {
                    end.linkTo(guiaVerticalTop)
                    top.linkTo(guiaHorizontalTop)
                }
        )

        Text( text = "Premiums Cars.\nEnjoy the luxury",
            modifier = Modifier.constrainAs(text){
                top.linkTo(foto.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 30.dp)
            },
            color = Color.White,
            fontSize = 35.sp)

        Button(onClick = {
            navController.navigate(route = AppScreens.LoginScreen.route)
        },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color(0xFF2C2B34)
            ), modifier = Modifier.width(200.dp)
                .constrainAs(btn){
                    top.linkTo(text.bottom, margin = 100.dp)
                    start.linkTo(parent.start, margin = 100.dp)
                }){
            Text(text="Let's Go", fontSize = 20.sp)
        }

    }

}

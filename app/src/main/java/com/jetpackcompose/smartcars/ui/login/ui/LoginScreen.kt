package com.jetpackcompose.smartcars.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(navController: NavController) {
    Box {
        //Modifier.paint(painterResource(id = R.drawable.loginbg)) -> Imagen de Fondo
        //Image(painter = painterResource(id = R.drawable.loginbg), contentDescription = "",
        //modifier = Modifier.fillMaxWidth())
        BackGround()
        Logo()
        Text()
        Row1()
        EmailPass()
        Btn(navController)
    }

}

@Composable
fun Row1() {
    Text(text = "Log in", modifier = Modifier.padding(top = 330.dp, start = 70.dp),
        fontWeight = FontWeight.ExtraBold,
        style = TextStyle(textDecoration = TextDecoration.Underline,
        fontSize = 20.sp)
    )
    Text(text = "Sign up", modifier = Modifier.padding(top = 330.dp, start = 260.dp),
        fontWeight = FontWeight.Bold, fontSize = 20.sp)
}

@Composable
fun EmailPass() {
    var textEmail by remember {mutableStateOf("")}
    var textPass by remember {mutableStateOf("")}

    Text(text = "Email", modifier = Modifier.padding(top = 390.dp, start = 70.dp))
    TextField(value = textEmail, onValueChange = { textEmail = it },
        modifier = Modifier
            .padding(top = 420.dp, start = 60.dp)
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(listOf(Color.DarkGray, Color.DarkGray)),
                shape = RoundedCornerShape(12.dp)
            )
    )
    //Hay que poner los dos TextFields iguales aún por decidir
    Text(text = "Password", modifier = Modifier.padding(top = 490.dp, start = 70.dp))
    OutlinedTextField(value = textPass, onValueChange = { textPass = it},
        modifier = Modifier
            .padding(top = 520.dp, start = 60.dp)
            .background(color = Color.White)
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(listOf(Color.DarkGray, Color.DarkGray)),
                shape = RoundedCornerShape(12.dp)
            ))
}

@Composable
fun Btn(navController: NavController) {
    Column(Modifier.padding(top = 620.dp, start = 130.dp)) {
        Button(onClick = {
            navController.navigate(route = AppScreens.HomeScreen.route)
        },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF2C2B34),
                contentColor = Color.White
            ), modifier = Modifier.width(140.dp)){
            Text(text="Login")
        }

        TextButton(onClick = {
            navController.navigate(route = AppScreens.SignupScreen.route)
        },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF2C2B34)
            ), modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(text = "Or sign up here")
        }
    }

}

@Composable
fun Text() {
    Text( text = "Hey!\nWelcome Back",
        modifier = Modifier.padding(top = 190.dp, start = 40.dp),
    color = Color.White,
    fontSize = 35.sp)
}

@Composable
fun Logo() {
    Image(painter = painterResource(id = R.drawable.cocheelectrico), contentDescription = "",
        modifier = Modifier
            .width(270.dp)
            .padding(start = 140.dp, top = 60.dp))
}

@Preview(showBackground = true)
@Composable
fun BackGround() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()){

        val (BoxLight, BoxDark) = createRefs()
        val guiaHorizontalTop = createGuidelineFromTop(0.4f)

        Box(modifier = Modifier
            .background(color = Color(0xFF2C2B34))
            .constrainAs(BoxDark) {}
            .fillMaxSize()){

        }
        Box(
            modifier = Modifier
                .size(width = 400.dp, height = 800.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(color = Color(0xFFF3F3F3))
                .constrainAs(BoxLight) { top.linkTo(guiaHorizontalTop) }
        ) {
        }
    }

}
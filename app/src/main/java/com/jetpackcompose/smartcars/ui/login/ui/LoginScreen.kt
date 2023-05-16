package com.jetpackcompose.smartcars.ui.login.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
        BackGround()
        Logo()
        Text()
        Column(Modifier
            .fillMaxWidth()
            .padding(horizontal = 56.dp)) {
            Row1()
            EmailPass()
            Btn(navController)
        }
    }

}

@Composable
fun Row1() {
    Row(
        Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "Log in", modifier = Modifier
                .padding(top = 330.dp)
                .weight(1f),
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Sign up", modifier = Modifier
                .padding(top = 330.dp)
                .weight(1f),
            fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.End
        )
    }
}

@Preview
@Composable
fun EmailPass() {
    var textEmail by remember { mutableStateOf("") }
    var textPass by remember { mutableStateOf("") }

    Text(text = "Email", modifier = Modifier.padding(top = 40.dp, start = 0.dp))
    TextField(
        value = textEmail, onValueChange = { textEmail = it },
        modifier = Modifier
            .padding(top = 10.dp, start = 0.dp)
            .border(
                border = BorderStroke(2.dp, Color(0xFF2C2B34)),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(30)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF2C2B34),
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    Text(text = "Password", modifier = Modifier.padding(top = 20.dp, start = 0.dp))
    TextField(
        value = textPass, onValueChange = { textPass = it },
        modifier = Modifier
            .padding(top = 10.dp, start = 0.dp)
            .border(
                border = BorderStroke(2.dp, Color(0xFF2C2B34)),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(30)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF2C2B34),
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Btn(navController: NavController) {
    Column(Modifier.padding(top = 30.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(route = AppScreens.HomeScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF2C2B34),
                    contentColor = Color.White
                ),
                modifier = Modifier.width(140.dp)
            ) {
                Text(text = "Login")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    navController.navigate(route = AppScreens.SignupScreen.route)
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF2C2B34)
                )
            ) {
                Text(text = "Or sign up here")
            }
        }
    }
}

@Composable
fun Text() {
    Text(
        text = "Hey!\nWelcome Back",
        modifier = Modifier.padding(top = 190.dp, start = 40.dp),
        color = Color.White,
        fontSize = 35.sp
    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.cocheelectrico), contentDescription = "",
        modifier = Modifier
            .width(270.dp)
            .padding(start = 140.dp, top = 60.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun BackGround() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (BoxLight, BoxDark) = createRefs()
        val guiaHorizontalTop = createGuidelineFromTop(0.4f)

        Box(modifier = Modifier
            .background(color = Color(0xFF2C2B34))
            .constrainAs(BoxDark) {}
            .fillMaxSize()) {

        }
        Box(
            modifier = Modifier
                .height(800.dp)
                .fillMaxWidth()
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
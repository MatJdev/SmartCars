package com.jetpackcompose.smartcars.ui.login.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpackcompose.smartcars.R


@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    Box {
        Image(painter = painterResource(id = R.drawable.loginbg), contentDescription = "",
        modifier = Modifier.fillMaxWidth())
        Row1()
        EmailPass()
        Btn()
    }

}

@Composable
fun Row1() {
    Text(text = "Log in", modifier = Modifier.padding(top = 270.dp, start = 70.dp),
        fontWeight = FontWeight.Bold
    )
    Text(text = "Sign up", modifier = Modifier.padding(top = 270.dp, start = 300.dp),
        fontWeight = FontWeight.Bold)
}

@Composable
fun EmailPass() {
    var textEmail by remember {mutableStateOf("")}
    var textPass by remember {mutableStateOf("")}

    Text(text = "Email", modifier = Modifier.padding(top = 320.dp, start = 70.dp))
    OutlinedTextField(value = textEmail, onValueChange = {},
    modifier = Modifier.padding(top = 350.dp, start = 60.dp)
        .border(BorderStroke(width = 3.dp, color = Color.DarkGray))
        .background(color = Color.White))

    Text(text = "Password", modifier = Modifier.padding(top = 420.dp, start = 70.dp))
    OutlinedTextField(value = textPass, onValueChange = {},
        modifier = Modifier.padding(top = 450.dp, start = 60.dp).background(color = Color.White))
}

@Composable
fun Btn() {
    Column(Modifier.padding(top = 520.dp, start = 130.dp)) {
        Button(onClick = {

        },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White
            ), modifier = Modifier.width(140.dp)){
            Text(text="Login")
        }

        TextButton(onClick = { },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.DarkGray
            ), modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(text = "Or sign up here")
        }
    }

}
package com.jetpackcompose.smartcars.ui.account.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens
import com.jetpackcompose.smartcars.ui.home.ui.*

@Composable
fun AccountScreen(navController: NavController) {
    Scaffold(navController)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("account_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        UserCard()
        Column(modifier = Modifier.padding(top = 400.dp, start = 30.dp, end = 30.dp)) {
            CustomCard(stringCard = "Credit: ", height = 50, string1 = "$userSaldoFire €", fontSize = 16)
            CustomCard(stringCard = "Id MetaMask: ", height = 70, string1 = userMetamaskFire, fontSize = 12)
            LogOutBtn(navController)
        }
    }
}

@Composable
fun CustomCard(stringCard: String, height: Int, string1: String, fontSize: Int) {

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp)) {
            append(stringCard)
        }
        append(" ")
        withStyle(style = SpanStyle(fontSize = fontSize.sp)) {
            append(string1)
        }
    }

    Spacer(
        modifier = Modifier
            .height(20.dp)
    )
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(height.dp),
        elevation = 2.dp,
        backgroundColor = Color(0xFFE6E6E6),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = annotatedText,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp, top = 0.dp)
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun UserCard() {
    Column {
        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 20.dp)
                .fillMaxWidth()
                .height(300.dp),
            backgroundColor = Color(0xFFE6E6E6),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userImgFire)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                )
                Text(
                    text = userNameFire,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp).align(CenterHorizontally)
                )
                Text(
                    text = "$userSaldoFire €",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp).align(CenterHorizontally)
                )
                Text(
                    text = userEmailFire,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp).align(CenterHorizontally)
                )
            }


        }
        Canvas(
            modifier = Modifier
                .height(10.dp)
                .width(300.dp)
                .wrapContentSize()
                .padding(top = 40.dp, start = 80.dp)
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(x = -400f, y = 20f),
                end = Offset(x = 400f, y = 20f),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

    }
}

@Composable
fun LogOutBtn(navController: NavController) {
    Button(
        onClick = { navController.navigate(route = AppScreens.LoginScreen.route) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF9B0505),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.common__logout))
    }
}

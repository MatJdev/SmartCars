package com.jetpackcompose.smartcars.ui.account.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.jetpackcompose.smartcars.ui.home.ui.BottomNavigationBar
import com.jetpackcompose.smartcars.ui.home.ui.MyFab

@Composable
fun AccountScreen(navController: NavController) {
    Scaffold(navController)
}

//@Preview(showBackground = true, showSystemUi = true)
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
//            Spacer(modifier = Modifier
//                .height(10.dp)
//                .border(1.dp, color = Color.Black))

            Divider(color = Color.Black, thickness = 1.dp)
            DownCards()
        }
    }

@Composable
fun DownCards(){
    Row(Modifier.padding(top = 400.dp, bottom = 20.dp)){
        Column {
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .width(500.dp)
                    .height(50.dp),
                elevation = 2.dp,
                backgroundColor = Color(0xFFE6E6E6),
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),

                border = BorderStroke(1.dp, Color.Black),
            ){
                Text(
                    text = "Credit : ",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(20.dp))
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .width(500.dp)
                    .height(50.dp),
                elevation = 2.dp,
                backgroundColor = Color(0xFFE6E6E6),
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),

                border = BorderStroke(1.dp, Color.Black),
            ){
                Text(
                    text = "Id MetaMask : ",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(20.dp))
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .width(500.dp)
                    .height(50.dp),
                elevation = 2.dp,
                backgroundColor = Color(0xFFE6E6E6),
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),

                border = BorderStroke(1.dp, Color.Black),
            ){
                Text(
                    text = "Reset Password",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(20.dp))
            Card(
                modifier = Modifier
//                    .padding(start = 100.dp, end = 50.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                elevation = 2.dp,
                backgroundColor = Color(0xFF303030),
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                border = BorderStroke(1.dp, Color.Black),

            ){
                Text(
                    text = "Log out",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }


        }
    }

}

@Composable
fun UserCard() {

    Column {
        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 20.dp)
                .width(300.dp)
                .height(300.dp),
            backgroundColor = Color(0xFFE6E6E6),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(Modifier.align(Alignment.CenterHorizontally)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://www.pngitem.com/pimgs/m/551-5510463_default-user-image-png-transparent-png.png")
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                        .align(alignment = Alignment.Center)
                )

            }
            Text(
                text = "Name",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 200.dp)
            )
            Text(
                text = "150 €",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 220.dp)
            )
            Text(
                text = "Email",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 240.dp)
            )
            Canvas(modifier = Modifier
                .height(10.dp).width(300.dp)
                .wrapContentSize()) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = -400f, y = 20f),
                    end = Offset(x = 400f, y = 20f),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )}

        }

    }
}

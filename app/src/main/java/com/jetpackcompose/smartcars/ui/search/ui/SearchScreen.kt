package com.jetpackcompose.smartcars.ui.search.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//import com.jetpackcompose.smartcars.ui.home.ui.Scaffold

//@Composable
//fun SearcScreen(navController: NavController) {
//    Scaffold(navController)
//}

//Este componente es para la top bar de la screen del recycler view
//@Composable
//fun CustomTopAppBar(screen: String, navController: NavController) {
//    //var ind by remember { mutableStateOf(0)}
//
//    TopAppBar(
//        backgroundColor = Color(0xFF2C2B34),
//        contentColor = Color.White,
//        elevation = 0.dp,
//        title = {
//            Text(
//                text = "Available Cars"
//            )
//        },
//        navigationIcon = {
//            IconButton(onClick = { navController.navigate(route = AppScreens.HomeScreen.route) }) {
//                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
//            }
//        },
//        actions = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
//            }
//        }
//    )
//}
@Composable
fun CustomTopAppBar() {
    //var ind by remember { mutableStateOf(0)}

    TopAppBar(backgroundColor = Color(0xFF2C2B34),
        contentColor = Color.White,
        elevation = 0.dp,
        title = {
            Text(
                text = "Available Cars"
            )
        },
        navigationIcon = {
            IconButton(onClick = {/* TODO = Aqui va navegacion atras y finish activity*/ }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
            }
        })
}

//@Preview(showBackground = true, showSystemUi = true)
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun Scaffold(navController: NavController) {
//    val scaffoldState = rememberScaffoldState()
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = { CustomTopAppBar(screen = "search_screen", navController) }
//
//    ) {
//        Column {
//            RvScreen(dataViewModel = viewModel(), navController)
//        }
//    }
//
//}


// Esta función es para mostrar y que funcione la barra de búsqueda

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextField(modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
            onTextChange(it)
        }, placeholder = {
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "Search here...",
                color = Color.White
            )
        }, textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        ), singleLine = true, leadingIcon =
        {
            IconButton(
                modifier = Modifier.alpha(ContentAlpha.medium),
                onClick = { /*TODO*/ }) {
//                Icon(
//                    ImageBitmap = Icons.Default.Search ,contentDescription = "Search Icon",
//                    tint = Color.White
//                )

            }

        })
    }
}

@Preview
@Composable
fun AppBarPreview() {
    CustomTopAppBar()
}

package com.jetpackcompose.smartcars.ui.search.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.ui.RV.RvScreen
import com.jetpackcompose.smartcars.ui.data.DataViewModel
import com.jetpackcompose.smartcars.ui.data.model.SearchWidgetState

@Composable
fun SearchScreen(navController: NavController, viewModel: DataViewModel) {

    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState

    SearchScreenScaffold(
        navController = navController,
        viewModel,
        searchWidgetState,
        searchTextState
    )
}


//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenScaffold(
    navController: NavController,
    viewModel: DataViewModel,
    searchWidgetState: SearchWidgetState,
    searchTextState: String
) {
    Scaffold(
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                },
                navController = navController,
                viewModel = viewModel
            )
        }

    ) {
        Column {
            RvScreen(dataViewModel = viewModel, navController)
        }
    }
}


@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    navController: NavController,
    viewModel: DataViewModel
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            CustomTopAppBar(
                navController,
                "search_screen",
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun CustomTopAppBar(
    navController: NavController,
    screen: String,
    onSearchClicked: () -> Unit,
) {

    TopAppBar(backgroundColor = Color(0xFF2C2B34),
        contentColor = Color.White,
        elevation = 0.dp,
        title = {
            Text(
                text = "Available Cars"
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(route = "home_screen")
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = { onSearchClicked() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    viewModel: DataViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = colorResource(id = R.color.bar_color)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
                viewModel.filterCarsByBrand(it)
            }, placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            }, textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ), singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")

                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                textColor = Color.White
            )
        )
    }
}


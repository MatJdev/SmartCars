package com.jetpackcompose.smartcars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jetpackcompose.smartcars.model.Web3jSingleton
import com.jetpackcompose.smartcars.navigation.AppNavigation
import com.jetpackcompose.smartcars.ui.login.ui.LoginScreen
import com.jetpackcompose.smartcars.ui.theme.SmartCarsTheme
import com.jetpackcompose.smartcars.ui.welcome.ui.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartCarsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation()
                    Web3jSingleton.init()
                }
            }
        }
    }
}


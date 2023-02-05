package com.jetpackcompose.smartcars.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpackcompose.smartcars.ui.login.ui.LoginScreen
import com.jetpackcompose.smartcars.ui.welcome.ui.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.WelcomeScreen.route) {
        composable(route = AppScreens.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        //Todo crear las demas rutas cuando estén las screens terminadas
    }

}
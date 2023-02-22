package com.jetpackcompose.smartcars.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpackcompose.smartcars.ui.RV.RvScreen
import com.jetpackcompose.smartcars.ui.login.ui.LoginScreen
import com.jetpackcompose.smartcars.ui.signup.ui.SignUpScreen
import com.jetpackcompose.smartcars.ui.welcome.ui.WelcomeScreen
import com.jetpackcompose.smartcars.ui.home.ui.HomeScreen

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
        composable(route = AppScreens.SignupScreen.route) {
            SignUpScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = AppScreens.RecyclerScreen.route){
            RvScreen(navController)
        }
        //Todo crear las demas rutas cuando estén las screens terminadas
    }

}
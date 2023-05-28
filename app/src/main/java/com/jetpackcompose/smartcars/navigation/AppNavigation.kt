package com.jetpackcompose.smartcars.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpackcompose.smartcars.ui.RV.RvScreen
import com.jetpackcompose.smartcars.ui.account.ui.AccountScreen
import com.jetpackcompose.smartcars.ui.data.model.MyArgs
import com.jetpackcompose.smartcars.ui.login.ui.LoginScreen
import com.jetpackcompose.smartcars.ui.signup.ui.SignUpScreen
import com.jetpackcompose.smartcars.ui.welcome.ui.WelcomeScreen
import com.jetpackcompose.smartcars.ui.home.ui.HomeScreen
import com.jetpackcompose.smartcars.ui.map.ui.MapScreen
import com.jetpackcompose.smartcars.ui.search.ui.SearchScreen

//import com.jetpackcompose.smartcars.ui.search.ui.SearchScreen

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
//        composable(route = AppScreens.RvScreen.route){
//            RvScreen(navController)
//        }
        composable(route = AppScreens.MapScreen.route,
            arguments = listOf(navArgument("myargs") { type = NavType.StringType })
            ){backStackEntry ->
                MapScreen(navController,
                    backStackEntry.arguments?.getString("myargs"))
        }
        composable(route = AppScreens.AccountScreen.route){
            AccountScreen(navController)
        }
        composable(route = AppScreens.SearchScreen.route){
            SearchScreen(navController, viewModel())
        }
        //Todo crear las demas rutas cuando estén las screens terminadas
    }

}
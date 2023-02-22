package com.jetpackcompose.smartcars.navigation

sealed class AppScreens (val route: String) {
    object WelcomeScreen: AppScreens("welcome_screen")
    object LoginScreen: AppScreens("login_screen")
    object SignupScreen: AppScreens("signup_screen")
    object HomeScreen: AppScreens("home_screen")
    object RecyclerScreen:AppScreens("rv_screen")

}

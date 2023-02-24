package com.jetpackcompose.smartcars.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(val route: String, val Icon: ImageVector) {
    object WelcomeScreen: AppScreens("welcome_screen", Icons.Outlined.Home)
    object LoginScreen: AppScreens("login_screen", Icons.Outlined.Home)
    object SignupScreen: AppScreens("signup_screen", Icons.Outlined.Home)
    object HomeScreen: AppScreens("home_screen", Icons.Outlined.Home)
    object MapScreen: AppScreens("map_screen", Icons.Outlined.LocationOn)
    object AccountScreen: AppScreens("account_screen", Icons.Outlined.Person)
    object SearchScreen: AppScreens("search_screen", Icons.Outlined.Search)
    object RvScreen: AppScreens("rv_screen", Icons.Outlined.Search)
}

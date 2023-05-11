package com.chondosha.bookclub

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chondosha.bookclub.ui.LoginScreen
import com.chondosha.bookclub.ui.UserHomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login"){
            LoginScreen()
        }
        composable("user_home") {
            UserHomeScreen()
        }
    }
}
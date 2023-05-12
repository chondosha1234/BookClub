package com.chondosha.bookclub

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chondosha.bookclub.ui.CreateAccountScreen
import com.chondosha.bookclub.ui.GroupScreen
import com.chondosha.bookclub.ui.LoginScreen
import com.chondosha.bookclub.ui.UserHomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "user_home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login"){
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate("user_home")
                },
                onNavigateToCreateAccount = {
                    navController.navigate("create_account")
                }
            )
        }
        composable("create_account") {
            CreateAccountScreen(
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("user_home") {
            UserHomeScreen(
                onNavigateToGroup = { groupId ->
                    navController.navigate("group/${groupId}")
                }
            )
        }
        composable(
            "group/{groupId}",
            arguments = listOf(navArgument("groupId") {type = NavType.StringType} )
        ) { backStackEntry ->
            GroupScreen(
                modifier = Modifier,
                groupId = backStackEntry.arguments?.getString("groupId")
            )
        }
    }
}
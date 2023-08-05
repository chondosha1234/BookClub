package com.chondosha.bookclub

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chondosha.bookclub.SharedPreferencesManager.isLoggedIn
import com.chondosha.bookclub.ui.*
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    //isLoggedIn: Boolean,
    //startDestination: String = if (isLoggedIn) "user_home" else "login"
) {
    val isLoggedIn by SharedPreferencesManager.isLoggedIn.collectAsState()
    val startDestination: String = if (isLoggedIn) "user_home" else "login"

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login"){
            //Log.d("login", "inside nav login composable path: $isLoggedIn")
            //if (isLoggedIn) {
                //navController.navigate("user_home")
            //} else {
                LoginScreen(
                    onNavigateToHome = {
                        navController.navigate("user_home")
                    },
                    onNavigateToCreateAccount = {
                        navController.navigate("create_account")
                    }
                )
            //}
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
                },
                onNavigateToCreateGroup = {
                    navController.navigate("create_group")
                },
                onNavigateToAddFriend = {
                    navController.navigate("add_friend")
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("create_group") {
            CreateGroupScreen(
                onNavigateToHome = {
                    navController.navigate("user_home")
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("add_friend") {
            AddFriendScreen(
                onNavigateToHome = {
                    navController.navigate("user_home")
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable(
            "group/{groupId}",
            arguments = listOf(navArgument("groupId") {type = NavType.StringType} )
        ) { backStackEntry ->
            GroupScreen(
                groupId = backStackEntry.arguments?.getString("groupId"),
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onNavigateToConversation = { conversationId ->
                    navController.navigate("conversation/${conversationId}")
                },
                onNavigateToCreateConversation = { groupId ->
                    navController.navigate("create_conversation/${groupId}")
                },
                onNavigateToAddMember = { groupId ->
                    navController.navigate("add_member/${groupId}")
                }
            )
        }
        composable(
            "create_conversation/{groupId}",
            arguments = listOf(navArgument("groupId") {type = NavType.StringType} )
        )
            { backStackEntry ->
            CreateConversationScreen(
                groupIdString = backStackEntry.arguments?.getString("groupId"),
                onNavigateToGroup = { groupId ->
                    navController.navigate("group/${groupId}")
                }
            )
        }
        composable(
            "conversation/{conversationId}",
            arguments = listOf(navArgument("conversationId") {type = NavType.StringType} )
        ) { backStackEntry ->
            ConversationScreen(
                conversationId = backStackEntry.arguments?.getString("conversationId"),
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable(
            "add_member/{groupId}",
            arguments = listOf(navArgument("groupId") {type = NavType.StringType} )
        ) { backStackEntry ->
            AddMemberScreen(
                groupId = backStackEntry.arguments?.getString("groupId"),
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onNavigateToGroup = { groupId ->
                    navController.navigate("group/${groupId}")
                }
            )
        }
    }
}
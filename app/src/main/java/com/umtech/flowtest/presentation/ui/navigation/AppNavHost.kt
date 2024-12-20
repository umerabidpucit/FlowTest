package com.umtech.flowtest.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.umtech.flowtest.data.model.User
import com.umtech.flowtest.presentation.ui.MainScreen
import com.umtech.flowtest.presentation.ui.UserDetailsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Home Screen (your initial screen where you list users)
        composable("home") {
            MainScreen(navController = navController)
        }

        // User Details Screen
        composable(
            "user_details?user={user}",
            arguments = listOf(navArgument("user") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("user")
            userJson?.let {
                val user = Gson().fromJson(it, User::class.java)
                UserDetailsScreen(navController = navController, user = user)
            }
        }
    }
}
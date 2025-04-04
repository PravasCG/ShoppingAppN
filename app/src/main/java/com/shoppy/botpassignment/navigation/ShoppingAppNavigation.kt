package com.shoppy.botpassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoppy.botpassignment.screens.ForgotPasswordScreen
import com.shoppy.botpassignment.screens.SplashScreen
import com.shoppy.botpassignment.screens.login.LoginScreen
import com.shoppy.botpassignment.screens.mainscreenholder.MainScreenHolder
import com.shoppy.botpassignment.screens.register.RegisterScreen

@Composable
fun ShoppingAppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreens.SplashScreen.name){
        composable(NavScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(NavScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }

        composable(NavScreens.RegisterScreen.name){
            RegisterScreen(navController = navController)
        }

        composable(NavScreens.MainScreenHolder.name){
            MainScreenHolder(navController = navController)
        }

        composable(NavScreens.ForgotPasswordScreen.name) {
            ForgotPasswordScreen(navHostController = navController)
        }

    }

}
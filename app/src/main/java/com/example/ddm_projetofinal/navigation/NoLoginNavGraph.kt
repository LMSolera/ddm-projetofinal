package com.example.ddm_projetofinal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ddm_projetofinal.ui.feature.login.LoginScreen
import com.example.ddm_projetofinal.ui.feature.register.RegisterScreen
import kotlinx.serialization.Serializable


@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
data class MainNavRoute (val userId: String, val userName: String, val userEmail: String, val userPassword: String)


@Composable
fun NoLoginNavGraph () {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onSuccessfulLogin = { newUser ->
                    navController.navigate(MainNavRoute(
                        newUser.id,
                        newUser.name,
                        newUser.email,
                        newUser.password
                    )) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                },
                navigateToRegister = { navController.navigate(RegisterRoute) }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onSuccessfulRegister = { newUser ->
                    navController.navigate(MainNavRoute(
                        newUser.id,
                        newUser.name,
                        newUser.email,
                        newUser.password
                    )) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                },
                navigateToLogin = { navController.navigate(LoginRoute) }
            )
        }

        composable<MainNavRoute> { backStackEntry ->
            val mainNavRoute = backStackEntry.toRoute<MainNavRoute>()
            MainNavGraph(
                mainNavRoute.userId,
                mainNavRoute.userName,
                mainNavRoute.userEmail,
                mainNavRoute.userPassword
            )
        }
    }
}
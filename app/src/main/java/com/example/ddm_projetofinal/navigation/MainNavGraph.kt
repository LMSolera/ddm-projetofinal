package com.example.ddm_projetofinal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ddm_projetofinal.model.User
import com.example.ddm_projetofinal.ui.feature.expenses.ExpensesScreen
import com.example.ddm_projetofinal.ui.feature.home.HomeScreen
import com.example.ddm_projetofinal.ui.feature.trips.TripsScreen
import com.example.ddm_projetofinal.ui.feature.userpage.UserPageScreen
import kotlinx.serialization.Serializable

@Serializable
data class HomeRoute (val userId: String, val userName: String, val userEmail: String, val userPassword: String)

@Serializable
data class UserRoute (val userId: String, val userName: String, val userEmail: String, val userPassword: String)

@Serializable
data class TripsRoute (val userId: String, val userName: String, val userEmail: String, val userPassword: String)

@Serializable
data class ExpensesRoute (val userId: String, val userName: String, val userEmail: String, val userPassword: String)

@Serializable
object LogOutRoute


@Composable
fun MainNavGraph (userId: String, userName: String, userEmail: String, userPassword: String) {
    val navController = rememberNavController()
    NavHost (
        startDestination = HomeRoute(userId, userName, userEmail, userPassword),
        navController = navController
    ) {
        composable<HomeRoute> { backStackEntry ->
            var homeRoute = backStackEntry.toRoute<HomeRoute>()
            HomeScreen(
                userInfo = User(
                    homeRoute.userId,
                    homeRoute.userName,
                    homeRoute.userEmail,
                    homeRoute.userPassword
                ),
                navigateUser = { userInfo ->
                    navController.navigate(UserRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateTrips = { userInfo ->
                    navController.navigate(TripsRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateExpenses = { userInfo ->
                    navController.navigate(ExpensesRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                }
            )
        }

        composable<UserRoute> { backStackEntry ->
            var userRoute = backStackEntry.toRoute<HomeRoute>()
            UserPageScreen(
                userInfo = User(
                    userRoute.userId,
                    userRoute.userName,
                    userRoute.userEmail,
                    userRoute.userPassword
                ),
                navigateHome = { userInfo ->
                    navController.navigate(HomeRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateTrips = { userInfo ->
                    navController.navigate(TripsRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateExpenses = { userInfo ->
                    navController.navigate(ExpensesRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                onLogOut = {
                    navController.navigate(LogOutRoute)
                },
                onCredentialsUpdate = { userInfo ->
                    navController.navigate(UserRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                }
            )
        }
        composable<TripsRoute> { backStackEntry ->
            var tripsRoute = backStackEntry.toRoute<TripsRoute>()
            TripsScreen(
                userInfo = User(
                    tripsRoute.userId,
                    tripsRoute.userName,
                    tripsRoute.userEmail,
                    tripsRoute.userPassword
                ),
                navigateHome = { userInfo ->
                    navController.navigate(HomeRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateUser = { userInfo ->
                    navController.navigate(UserRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateExpenses = { userInfo ->
                    navController.navigate(ExpensesRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                }
            )
        }
        composable<ExpensesRoute> { backStackEntry ->
            var expensesRoute = backStackEntry.toRoute<ExpensesRoute>()
            ExpensesScreen(
                userInfo = User(
                    expensesRoute.userId,
                    expensesRoute.userName,
                    expensesRoute.userEmail,
                    expensesRoute.userPassword
                ),
                navigateHome = { userInfo ->
                    navController.navigate(HomeRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateUser = { userInfo ->
                    navController.navigate(UserRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                },
                navigateTrips = { userInfo ->
                    navController.navigate(TripsRoute(
                        userInfo.id,
                        userInfo.name,
                        userInfo.email,
                        userInfo.password
                    ))
                }
            )
        }

        composable<LogOutRoute> {
            NoLoginNavGraph()
        }
    }
}
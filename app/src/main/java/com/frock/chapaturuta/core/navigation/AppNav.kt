package com.frock.chapaturuta.core.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.frock.chapaturuta.core.root.Main
import com.frock.chapaturuta.core.ui.theme.AppTheme
import com.frock.chapaturuta.features.auth.presentation.login.LoginView
import com.frock.chapaturuta.features.auth.presentation.register.RegisterProfileView
import com.frock.chapaturuta.features.auth.presentation.register.RegisterUserView
import com.frock.chapaturuta.features.profile.presentation.RegisterVehicleView

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        composable(Route.Login.route) {
            LoginView(
                onLoginClick = {userId ->
                    navController.navigate(Route.Main.createRoute(userId)){
                        popUpTo(Route.Login.route){inclusive = true}
                    }
                },
                onSignUpClick = {
                    navController.navigate(Route.RegisterUser.route)
                }
            )
        }

        composable(Route.RegisterUser.route) {
            RegisterUserView(
                onRegisterClick = { userId ->
                    // Aquí validarías y si todo OK, ir a completar perfil
                    navController.navigate(Route.RegisterProfile.createRoute(userId)){
                        popUpTo(Route.RegisterUser.route){inclusive = true}
                    }
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.RegisterProfile.route, arguments = listOf(navArgument("userId"){type = NavType.IntType})
        ) { backStackEntry->
            val userId = backStackEntry.arguments?.getInt("userId")?:0
            RegisterProfileView(userId = userId,
                onRegisterClick = { profileId ->
                    navController.navigate(Route.RegisterVehicle.createRoute(profileId)) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.RegisterVehicle.route, arguments = listOf(navArgument("profileId"){type = NavType.IntType})
        ) { backStackEntry->
            val profileId = backStackEntry.arguments?.getInt("profileId")?:0
            RegisterVehicleView(profileId = profileId,
                onRegisterClick = { userId ->
                    navController.navigate(Route.Main.createRoute(userId)) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        /*composable(Route.RegisterProfile.route, arguments = listOf(navArgument("userId"){type = NavType.IntType})
        ) { backStackEntry->
            val userId = backStackEntry.arguments?.getInt("userId")?:0
            RegisterProfileView(userId = userId,
                onRegisterClick = { profileId ->
                    navController.navigate(Route.Main.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }*/

        composable(Route.Main.route,
            arguments = listOf(navArgument("userId"){type = NavType.IntType})
        ){ backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId")?:0
            Main(userId =  userId){
                navController.navigate(Route.Login.route)
            }
        }
    }
}

@Composable
fun MainScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavPreview() {
    AppTheme {
        AppNav()
    }
}

sealed class Route(val route: String) {
    object Main : Route("main/{userId}"){
        fun createRoute(userId:Int) = "main/$userId"
    }
    object Login : Route("login")
    object RegisterUser : Route("register_user")
    object RegisterProfile : Route("register_profile/{userId}"){
        fun createRoute(userId: Int) = "register_profile/$userId"
    }

    object RegisterVehicle: Route("register_vehicle/{profileId}"){
        fun createRoute(profileId: Int) = "register_vehicle/$profileId"
    }
}

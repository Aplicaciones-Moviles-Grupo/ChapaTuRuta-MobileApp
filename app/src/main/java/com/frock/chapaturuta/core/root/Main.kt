package com.frock.chapaturuta.core.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.frock.chapaturuta.core.ui.theme.AppTheme
import com.frock.chapaturuta.features.home.presentation.Home
import com.frock.chapaturuta.features.profile.domain.models.Profile
import com.frock.chapaturuta.features.profile.presentation.EditProfileView
import com.frock.chapaturuta.features.profile.presentation.EditVehicleView
import com.frock.chapaturuta.features.profile.presentation.ProfileView
import com.frock.chapaturuta.features.stops.presentation.*
import com.frock.chapaturuta.features.routes.presentation.*

@Composable
fun Main(userId: Int, onSignOut: () -> Unit) {
    val navigationItems = listOf(
        NavigationItem(icon = Icons.Default.Home, label = "Home"),
        NavigationItem(icon = Icons.Default.LocationOn, label = "Stops"),
        NavigationItem(icon = Icons.Default.DirectionsCar, label = "Routes"),
        NavigationItem(icon = Icons.Default.Person, label = "Profile")
    )

    val selectedIndex = remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex.intValue,
                        onClick = {
                            selectedIndex.intValue = index
                            when (index) {
                                0 -> navController.navigate("home/${userId}") {
                                    popUpTo("home/${userId}") { inclusive = true }
                                }
                                1 -> navController.navigate("stops/${userId}") {
                                    popUpTo("stops/${userId}") { inclusive = true }
                                }
                                2 -> navController.navigate("routes") {
                                    popUpTo("routes") { inclusive = true }
                                }
                                3 -> navController.navigate("profile/${userId}") {
                                    popUpTo("profile/${userId}") { inclusive = true }
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home/${userId}",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home/{userId}", arguments = listOf(navArgument("userId"){type = NavType.IntType})
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                selectedIndex.intValue = 0
                Home(userId)
            }

            // Stops navigation
            composable("stops/{userId}", arguments =  listOf(navArgument("userId"){type=
                NavType.IntType})) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                selectedIndex.intValue = 1
                StopsView(userId,
                    onNavigateToCreateStop = { navController.navigate("stops/create/$userId") },
                    onNavigateToEditStop = { stopId -> navController.navigate("stops/edit/$stopId") }
                )
            }
            composable("stops/create/{userId}", arguments = listOf(navArgument("userId"){type=
                NavType.IntType})) { backstackEntry ->
                val userId = backstackEntry.arguments?.getInt("userId") ?: 0
                CreateStopView(
                    onNavigateBack = { navController.popBackStack() },
                    profileId = userId
                )
            }
            composable("stops/edit/{stopId}") { backStackEntry ->
                val stopId = backStackEntry.arguments?.getString("stopId") ?: ""
                EditStopView(
                    stopId = stopId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Routes navigation
            composable("routes") {
                selectedIndex.intValue = 2
                RoutesView(
                    onNavigateToCreateRoute = { navController.navigate("routes/create") },
                    onNavigateToEditRoute = { routeId -> navController.navigate("routes/edit/$routeId") }
                )
            }
            composable("routes/create") {
                CreateRouteView(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("routes/edit/{routeId}") { backStackEntry ->
                val routeId = backStackEntry.arguments?.getString("routeId") ?: ""
                EditRouteView(
                    routeId = routeId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("profile/{userId}", arguments = listOf(navArgument("userId"){type=
                NavType.IntType})) { backStackEntry->
                val userId = backStackEntry.arguments?.getInt("userId")?:0
                selectedIndex.intValue = 3
                ProfileView(userId,
                    onEditProfile = { profileId ->
                        navController.navigate("profile/edit/${profileId}")},
                    onEditVehicle = {profileId ->
                        navController.navigate("profile/${profileId}/vehicle/edit")
                    },
                    onSignOut= onSignOut
                )
            }

            composable("profile/edit/{profileId}", arguments = listOf(navArgument("profileId"){type =
                NavType.IntType})){ backStackEntry->
                val profileId = backStackEntry.arguments?.getInt("profileId")?:0
                EditProfileView(profileId,
                    onEditClick = {navController.popBackStack()},
                    onCancelClick = {navController.popBackStack()}
                    )
            }

            composable("profile/{profileId}/vehicle/edit", arguments = listOf(navArgument("profileId"){type =
                NavType.IntType})){ backStackEntry->
                val profileId = backStackEntry.arguments?.getInt("profileId")?:0
                // EditVehicleView to be implemented
                EditVehicleView(profileId,
                    onEditClick = {navController.popBackStack()},
                    onCancelClick = {navController.popBackStack()}
                )
                }
        }
    }
}

data class NavigationItem(val icon: ImageVector, val label: String)


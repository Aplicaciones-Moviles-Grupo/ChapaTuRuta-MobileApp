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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.frock.chapaturuta.core.ui.theme.AppTheme
import com.frock.chapaturuta.features.home.presentation.Home
import com.frock.chapaturuta.features.stops.presentation.*
import com.frock.chapaturuta.features.routes.presentation.*

@Composable
fun Main(userId: Int) {
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
                                0 -> navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                                1 -> navController.navigate("stops") {
                                    popUpTo("stops") { inclusive = true }
                                }
                                2 -> navController.navigate("routes") {
                                    popUpTo("routes") { inclusive = true }
                                }
                                3 -> navController.navigate("profile") {
                                    popUpTo("profile") { inclusive = true }
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
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                selectedIndex.intValue = 0
                Home()
            }

            // Stops navigation
            composable("stops") {
                selectedIndex.intValue = 1
                StopsView(
                    onNavigateToCreateStop = { navController.navigate("stops/create") },
                    onNavigateToEditStop = { stopId -> navController.navigate("stops/edit/$stopId") }
                )
            }
            composable("stops/create") {
                CreateStopView(
                    onNavigateBack = { navController.popBackStack() }
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

            composable("profile") {
                selectedIndex.intValue = 3
                Text(text = "Profile")
            }
        }
    }
}

data class NavigationItem(val icon: ImageVector, val label: String)

@Composable
@Preview
fun MainPreview() {
    AppTheme(dynamicColor = false) {
        Main(1)
    }
}
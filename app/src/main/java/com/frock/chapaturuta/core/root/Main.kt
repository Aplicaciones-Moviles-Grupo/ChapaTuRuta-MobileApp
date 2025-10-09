package com.frock.chapaturuta.core.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.frock.chapaturuta.core.ui.theme.AppTheme
import com.frock.chapaturuta.features.auth.presentation.login.LoginView
import com.frock.chapaturuta.features.home.presentation.Home


@Composable
fun Main(){

    val navigationItems = listOf(
        NavigationItem(icon = Icons.Default.Home, label = "Home"),
        NavigationItem(icon = Icons.Default.LocationOn, label = "Stops"),
        NavigationItem(icon = Icons.Default.DirectionsCar, label = "Routes"),
        NavigationItem(icon = Icons.Default.Person, label = "Profile")
    )

    val selectedIndex = remember{
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                navigationItems.forEachIndexed { index,item ->
                    NavigationBarItem(
                        selected = index == selectedIndex.intValue,
                        onClick = {
                            selectedIndex.intValue = index
                        },
                        icon = {Icon(item.icon, contentDescription = item.label)},
                        label = {Text(item.label)}
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (selectedIndex.value == 0){
                Home ()
            }else if(selectedIndex.value == 1){
                Text(text = "Stops")
            }else if(selectedIndex.value == 2){
                Text(text = "Routes")
            }else if (selectedIndex.value == 3){
                Text(text = "Profile")
            }

        }
    }



}

data class NavigationItem(val icon: ImageVector, val label:String)

@Composable
@Preview
fun MainPreview(){
    AppTheme(dynamicColor = false) {
        Main()
    }
}
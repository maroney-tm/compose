package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.launch

data object Balls
data object Springs

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                val drawerState = rememberDrawerState(DrawerValue.Open)
                val scope = rememberCoroutineScope()
                val backStack = remember { mutableStateListOf<Any>(Balls) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(drawerState = drawerState) {
                            Column {
                                NavigationDrawerItem(
                                    label = { Text("Beautiful Balls") },
                                    selected = backStack.lastOrNull() == Balls,
                                    onClick = {
                                        scope.launch {
                                            backStack.add(Balls)
                                            drawerState.close()
                                        }
                                    })
                                NavigationDrawerItem(
                                    label = { Text("Fantastic Springs") },
                                    selected = backStack.lastOrNull() == Springs,
                                    onClick = {
                                        scope.launch {
                                            backStack.add(Springs)
                                            drawerState.close()
                                        }
                                    })
                                NavigationDrawerItem(
                                    label = { Text("Attraction") },
                                    selected = false,
                                    onClick = { /*TODO*/ })
                                NavigationDrawerItem(
                                    label = { Text("Repulsion") },
                                    selected = false,
                                    onClick = { /*TODO*/ })
                                NavigationDrawerItem(
                                    label = { Text("Clusters") },
                                    selected = false,
                                    onClick = { /*TODO*/ })
                            }
                        }
                    },
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = {
                                Text("Beautiful Balls")
                            }, navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            } else {
                                                drawerState.close()
                                            }
                                        }
                                    }) {
                                    Icon(
                                        Icons.Filled.Menu, contentDescription = "Menu"
                                    )
                                }
                            })
                        }) { innerPadding ->
                        NavDisplay(
                            backStack = backStack,
                            onBack = { backStack.removeLastOrNull() },
                            modifier = Modifier.padding(innerPadding),
                        ) { route ->
                            when (route) {
                                is Balls -> NavEntry(route) {
                                    Content()
                                }

                                is Springs -> NavEntry(route) {
                                    Text("Springs")
                                }

                                else -> NavEntry(route) {
                                    Text("Whoops")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


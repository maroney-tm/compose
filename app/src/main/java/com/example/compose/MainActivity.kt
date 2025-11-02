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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                val drawerState = rememberDrawerState(DrawerValue.Open)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(drawerState = drawerState) {
                            Column {
                                NavigationDrawerItem(
                                    label = { Text("Beautiful Balls") },
                                    selected = true,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    })
                                NavigationDrawerItem(
                                    label = { Text("Fantastic Springs") },
                                    selected = false,
                                    onClick = { /*TODO*/ })
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
                        Content(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.marsphotos.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marsphotos.R
import com.example.marsphotos.ui.screens.HomeScreen
import com.example.marsphotos.ui.screens.LoginScreen
import com.example.marsphotos.ui.screens.MarsViewModel

@Composable
fun MarsPhotosApp() {
    val navController = rememberNavController()

    // Configuramos la navegación
    NavHost(navController = navController, startDestination = "login") {

        // PANTALLA 1: LOGIN
        composable("login") {
            LoginScreen(
                onLoginClick = { user, time ->
                    // Navegamos a home pasando los argumentos
                    navController.navigate("home/$user/$time")
                }
            )
        }

        // PANTALLA 2: HOME (Recibe user y time)
        composable(
            route = "home/{user}/{time}",
            arguments = listOf(
                navArgument("user") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Recuperamos los datos pasados desde el Login
            val user = backStackEntry.arguments?.getString("user") ?: "Invitado"
            val time = backStackEntry.arguments?.getString("time") ?: ""

            // Aquí va el Scaffold original que tenías, ahora dentro de la ruta Home
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                // Pasamos los datos a la barra superior
                topBar = {
                    MarsTopAppBar(
                        scrollBehavior = scrollBehavior,
                        user = user,
                        time = time
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) // Usar el padding del Scaffold aquí
                ) {
                    val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)
                    HomeScreen(
                        marsUiState = marsViewModel.marsUiState,
                        retryAction = marsViewModel::getMarsPhotos
                        // El contentPadding ya no es necesario pasarlo a HomeScreen
                        // si se aplica directamente en el Modifier del Surface o un Layout contenedor.
                    )
                }
            }

        }
    }
}

@Composable
fun MarsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    user: String,
    time: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            // Columna para mostrar Título + Datos del usuario
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                )
                // REQUISITO: Mostrar nombre y hora
                Text(
                    text = "$user | $time",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        modifier = modifier
    )
}
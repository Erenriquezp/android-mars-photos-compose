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

    // Configuramos el NavHost para manejar la navegación entre Login y Home
    NavHost(navController = navController, startDestination = "login") {

        // --- PANTALLA 1: LOGIN ---
        composable("login") {
            LoginScreen(
                onLoginClick = { user, time ->
                    // Navegamos a la ruta 'home' enviando usuario y hora
                    navController.navigate("home/$user/$time")
                }
            )
        }

        // --- PANTALLA 2: HOME (Recibe user y time) ---
        composable(
            route = "home/{user}/{time}",
            arguments = listOf(
                navArgument("user") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 1. Recuperamos los datos de los argumentos
            val user = backStackEntry.arguments?.getString("user") ?: "Invitado"
            val time = backStackEntry.arguments?.getString("time") ?: ""

            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

            // 2. Scaffold con la Barra Superior Personalizada
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    MarsTopAppBar(
                        scrollBehavior = scrollBehavior,
                        user = user, // Pasamos el usuario para mostrarlo arriba
                        time = time  // Pasamos la hora para mostrarla arriba
                    )
                }
            ) { innerPadding ->
                // 3. Contenido de la pantalla (El JSON)
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) // Importante: Respetar el padding de la TopBar
                ) {
                    val marsViewModel: MarsViewModel = viewModel()

                    // Llamamos a HomeScreen pasando solo el estado y el modificador
                    HomeScreen(
                        marsUiState = marsViewModel.marsUiState,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

// Barra superior personalizada que cumple el requisito de mostrar Usuario y Hora
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Título de la App
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                )
                // Subtítulo con Datos del Usuario
                Text(
                    text = "$user | $time",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        modifier = modifier
    )
}
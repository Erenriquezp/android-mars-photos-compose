package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marsphotos.R
import java.text.SimpleDateFormat
import java.util.*

// Paso 4 y 3: Definir la paleta de colores para el tema oscuro.
val PurplePrimary = Color(0xFFD0BCFF) // Color de acento lila.
val DarkBackground = Color(0xFF121212) // Fondo principal muy oscuro.
val CardDark = Color(0xFF1E1E1E)      // Fondo de la tarjeta, un poco más claro.
val LightTextColor = Color(0xFFF5F5F5)  // Color para texto principal, casi blanco.
val GrayTextColor = Color.Gray         // Color para texto secundario y placeholders.

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground) // Paso 3: Fondo general oscuro.
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Paso 3: Tarjeta con un fondo oscuro para crear jerarquía.
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardDark // Usamos el gris oscuro para la tarjeta.
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Paso 1: Logo en blanco para que contraste con el fondo oscuro de la tarjeta.
                Image(
                    painter = painterResource(id = R.drawable.logo_grupo),
                    contentDescription = "Logo Grupo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 16.dp),
                    colorFilter = ColorFilter.tint(Color.White) // ¡Solución CRÍTICA!
                )

                // Paso 4: Título con color claro para máxima legibilidad.
                Text(
                    text = "Ingreso al Sistema",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = LightTextColor
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Paso 2: Inputs con estilo minimalista (solo línea inferior).
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = LightTextColor,
                        unfocusedTextColor = LightTextColor,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = PurplePrimary, // Línea de foco lila
                        unfocusedIndicatorColor = GrayTextColor, // Línea normal gris
                        focusedLabelColor = PurplePrimary,
                        unfocusedLabelColor = GrayTextColor,
                        cursorColor = PurplePrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = LightTextColor,
                        unfocusedTextColor = LightTextColor,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = PurplePrimary,
                        unfocusedIndicatorColor = GrayTextColor,
                        focusedLabelColor = PurplePrimary,
                        unfocusedLabelColor = GrayTextColor,
                        cursorColor = PurplePrimary
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Paso 4: Botón mantiene el color de acento.
                Button(
                    onClick = {
                        if (username.isNotEmpty()) {
                            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                            onLoginClick(username, currentTime)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurplePrimary,
                        contentColor = DarkBackground // Texto oscuro para contraste en botón.
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "INGRESAR",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

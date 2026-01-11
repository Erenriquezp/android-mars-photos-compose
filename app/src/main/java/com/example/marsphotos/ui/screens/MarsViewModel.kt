package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
// IMPORTS IMPORTANTES PARA JSON
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {

    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    // Creamos una configuración de JSON para que imprima "bonito" (Pretty Print)
    private val json = Json { prettyPrint = true }

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val listResult = MarsApi.retrofitService.getPhotos()

                // AQUÍ ESTÁ EL TRUCO:
                // Convertimos la lista de objetos a un String JSON formateado
                val jsonString = json.encodeToString(listResult)

                MarsUiState.Success(jsonString)
            } catch (e: IOException) {
                MarsUiState.Error
            }
        }
    }
}
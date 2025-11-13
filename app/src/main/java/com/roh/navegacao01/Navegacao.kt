package com.roh.navegacao01

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navegacao() {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val current = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.LightGray
                ), title = {
                    Text(
                        text = when (current) {
                            Rotas.Primeira.name -> "Primeira Tela"
                            Rotas.Segunda.name -> "Segunda Tela"
                            Rotas.Terceira.name -> "Terceira Tela"
                            else -> "App"
                        }
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {

                        IconButton(onClick = {
                            navController.navigate(Rotas.Primeira.name) {
                                popUpTo(Rotas.Primeira.name) { inclusive = true }
                            }

                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Rotas.Primeira.name
        ) {
            composable(Rotas.Primeira.name) {
                PrimeiraTela { nome, idade ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("nome", nome)
                    navController.currentBackStackEntry?.savedStateHandle?.set(("idade"), idade)
                    navController.navigate(Rotas.Segunda.name)
                }
            }

            composable(Rotas.Segunda.name) {
                val nome: String =
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("nome")
                        ?: ""
                val idade: Int =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Int>("idade") ?: 0

                SegundaTela(nome, idade) {
                    navController.navigate(Rotas.Terceira.name)

                }
            }

            composable(Rotas.Terceira.name) {
                TerceiraTela()
            }

        }

    }

}

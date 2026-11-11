package com.github.gleberphant.appagendamentos.views


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.gleberphant.appagendamentos.controllers.AgendamentoController
import com.github.gleberphant.appagendamentos.ui.theme.AppAgendamentosTheme

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val agendamentoController : AgendamentoController = viewModel()

    NavHost(
        navController = navController, startDestination = "home"
    ) {

        composable(route="home") {
            PageListarAgendamentos(
                agendamentoController = agendamentoController,
                funNovoAgendamento = { navController.navigate("cadastro") },
                funEditarAgendamento = { agendamentoID -> navController.navigate("cadastro/$agendamentoID") }
            )
        }

        composable ( route="cadastro" ){

            PageCriaAgendamento(
                agendamentoControler = agendamentoController,
                agendamentoID = null,
                voltar = { navController.navigate("home") }
            )
        }

        composable ( route="cadastro/$id"){
            PageCriaAgendamento(
                agendamentoControler = agendamentoController,
                agendamentoID = id,
                voltar = { navController.navigate("home") }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppNavigatorPreview() {
    AppAgendamentosTheme {
        AppNavigator()
    }
}

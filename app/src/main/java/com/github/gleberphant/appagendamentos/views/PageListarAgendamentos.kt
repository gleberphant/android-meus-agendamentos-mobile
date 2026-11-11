package com.github.gleberphant.appagendamentos.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gleberphant.appagendamentos.controllers.AgendamentoController
import com.github.gleberphant.appagendamentos.models.Agendamento

@Composable
fun PageListarAgendamentos(
    agendamentoController: AgendamentoController,
    funNovoAgendamento: () -> Unit,
    funEditarAgendamento: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = funNovoAgendamento) {
                Text(text = "+")
            }
        })
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Lista de Agendamentos")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(agendamentoController.agendamentos) { agendamento ->
                AgendamentoItem(
                    agendamento,
                    { funEditarAgendamento(agendamento.id) },
                    { agendamentoController.removerPorId(agendamento.id) })
            }


        }
    }
}

@Composable
fun AgendamentoItem(
    agendamento: Agendamento,
    btnEditar: () -> Unit,
    btnExcluir: () -> Unit
) {

    Card() {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = agendamento.data.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = agendamento.descricao,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = agendamento.status.toString(),
                style = MaterialTheme.typography.bodyMedium
            )

            Row() {
                Button(onClick = btnEditar) { Text("Editar") }
                Button(
                    onClick = btnExcluir,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                )
                {
                    Text("Excluir")
                }
            }

        }
    }

}
package com.github.gleberphant.appagendamentos.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gleberphant.appagendamentos.controllers.AgendamentoController
import com.github.gleberphant.appagendamentos.models.Agendamento
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PageListarAgendamentos(
    agendamentoController: AgendamentoController,
    funNovoAgendamento: () -> Unit,
    funEditarAgendamento: (String) -> Unit
) {
    var exibirConcluidos by remember { mutableStateOf(false) }

    val agendamentosFiltrados = if (exibirConcluidos) {
        agendamentoController.agendamentos
    } else {
        agendamentoController.agendamentos.filter { !it.status }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = funNovoAgendamento) {
                Text(text = "+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Agendamentos",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedButton(
                    onClick = { exibirConcluidos = !exibirConcluidos }
                ) {
                    Text(
                        text = if (exibirConcluidos) "Ocultar Concluídos" else "Mostrar Concluídos"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = agendamentosFiltrados,
                    key = { it.id }
                ) { agendamento ->
                    CardAgendamento(
                        item = agendamento,
                        btnEditar = { funEditarAgendamento(agendamento.id) },
                        btnConcluir = { agendamentoController.concluirAgendamento(agendamento.id) },
                        btnExcluir = { agendamentoController.removerPorId(agendamento.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CardAgendamento(
    item: Agendamento,
    btnEditar: () -> Unit,
    btnConcluir: () -> Unit,
    btnExcluir: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Spacer(modifier = Modifier.height(16.dp))
    Card {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "DATA: " + dateFormat.format(item.data),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = item.descricao,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = if (item.status) "concluído" else "pendente",
                style = MaterialTheme.typography.bodyMedium
            )

            Row {
                Button(
                    onClick = btnEditar
                ) { Text("Editar") }

                if (!item.status) {
                    Button(
                        onClick = btnConcluir,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Concluir")
                    }
                }

                Button(
                    onClick = btnExcluir,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}


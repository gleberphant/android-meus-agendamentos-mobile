package com.github.gleberphant.appagendamentos.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gleberphant.appagendamentos.controllers.AgendamentoController
import com.github.gleberphant.appagendamentos.models.Agendamento
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PageCriaAgendamento (
    agendamentoControler: AgendamentoController,
    agendamentoID: Int?,
    voltar: () -> Unit
){



    val agendamento = if (agendamentoID!=null) agendamentoControler.procurarPorId(agendamentoID) else null
    val titulo = if (agendamentoID!=null) "Editar Agendamento" else "Criar Agendamento"

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var dataStr by remember {
        mutableStateOf(agendamento?.data?.let { dateFormat.format(it) } ?: "")
    }
    var descricao by remember {
        mutableStateOf(agendamento?.descricao ?: "")
    }

    Scaffold(){
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(titulo, style= MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(
                value = dataStr,
                onValueChange = { dataStr = it },
                label = { Text("Data (dd/MM/yyyy)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = descricao.toString(),
                onValueChange = { descricao = it },
                label = { Text("Descrição")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    val parsedDate = try { dateFormat.parse(dataStr) } catch (e: Exception) { Date() }
                    val novoAgendamento = Agendamento(
                        id = agendamentoID?:0,
                        data = parsedDate ?: Date(),
                        descricao = descricao,
                        status = agendamento?.status ?: true
                    )

                    if (agendamentoID!=null) {
                        agendamentoControler.editarPorId(agendamentoID, novoAgendamento)
                    } else {
                        agendamentoControler.criar(novoAgendamento)
                    }
                    voltar()
                }
            ) {
                Text("Salvar")
            }


            Button(onClick = voltar) {
                Text("Voltar")
            }
        }



    }

}
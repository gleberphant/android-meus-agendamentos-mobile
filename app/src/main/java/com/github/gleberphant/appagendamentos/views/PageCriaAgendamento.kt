package com.github.gleberphant.appagendamentos.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gleberphant.appagendamentos.controllers.AgendamentoController
import com.github.gleberphant.appagendamentos.models.Agendamento
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageCriaAgendamento (
    agendamentoControler: AgendamentoController,
    agendamentoID: String?,
    voltar: () -> Unit
){
    val agendamento = if (agendamentoID != null) agendamentoControler.procurarPorId(agendamentoID) else null
    val titulo = if (agendamentoID != null) "Editar Agendamento" else "Criar Agendamento"

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var dataObj by remember { mutableStateOf(agendamento?.data ?: Date()) }
    var dataStr by remember { mutableStateOf(dateFormat.format(dataObj)) }
    var descricao by remember {
        mutableStateOf(agendamento?.descricao ?: "")
    }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = agendamento?.data?.time
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(16.dp))

            // Campo de Data (Abre o DatePicker ao clicar ou no ícone)
            OutlinedTextField(
                value = dataStr,
                onValueChange = {},
                readOnly = true, // Impede digitação manual incorreta
                label = { Text("Data") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Selecionar Data")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    val parsedDate = try { dateFormat.parse(dataStr) } catch (e: Exception) { Date() }
                    val novoAgendamento = Agendamento(
                        id = agendamentoID ?: "",
                        data = parsedDate ?: Date(),
                        descricao = descricao,
                        status = agendamento?.status ?: false
                    )

                    if (agendamentoID != null) {
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

    // Diálogo do DatePicker do Material 3
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            dataObj = Date(millis)
                            dataStr = dateFormat.format(dataObj)
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

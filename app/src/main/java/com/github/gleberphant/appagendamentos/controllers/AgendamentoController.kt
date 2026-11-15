package com.github.gleberphant.appagendamentos.controllers

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.github.gleberphant.appagendamentos.models.Agendamento
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgendamentoController(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val colletionRef = db.collection("agendamentos")

    var agendamentos = mutableStateListOf<Agendamento>()
        private set

    init {
        carregarAgendamentosDoFirebase()
    }

    private fun carregarAgendamentosDoFirebase() {
        colletionRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Log.e("AgendamentoController", "Erro ao carregar do Firebase: ${error.message}", error)
                return@addSnapshotListener
            }
            if (snapshots != null) {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val lista = snapshots.documents.mapNotNull { doc ->
                    try {
                        val id = doc.id
                        val rawData = doc.get("data")
                        val data: Date = when (rawData) {
                            is com.google.firebase.Timestamp -> rawData.toDate()
                            is Date -> rawData
                            is Long -> Date(rawData)
                            is String -> try { dateFormat.parse(rawData) ?: Date() } catch (e: Exception) { Date() }
                            else -> Date()
                        }

                        val descricao = doc.getString("descricao") ?: ""
                        val status = doc.getBoolean("status") ?: false

                        Agendamento(id, data, descricao, status)
                    } catch (e: Exception) {
                        Log.e("AgendamentoController", "Erro ao converter documento ${doc.id}", e)
                        null
                    }
                }

                agendamentos.clear()
                agendamentos.addAll(lista)
            }
        }
    }

    fun criar(novo: Agendamento) {
        val dados = mapOf(
            "data" to novo.data,
            "descricao" to novo.descricao,
            "status" to novo.status
        )
        colletionRef.add(dados)
    }

    fun concluirAgendamento(id: String) {
        colletionRef.document(id).update("status", true)
    }

    fun listar(): List<Agendamento> {
        return agendamentos
    }

    fun procurarPorId(id: String): Agendamento? {
        return agendamentos.find { it.id == id }
    }

    fun editarPorId(id: String, novo: Agendamento) {
        val dados = mapOf(
            "data" to novo.data,
            "descricao" to novo.descricao,
            "status" to novo.status
        )
        colletionRef.document(id).set(dados)
    }

    fun removerPorId(id: String) {
        colletionRef.document(id).delete()
    }
}


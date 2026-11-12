package com.github.gleberphant.appagendamentos.controllers

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.github.gleberphant.appagendamentos.data.AgendamentoDbHelper
import com.github.gleberphant.appagendamentos.models.Agendamento

class AgendamentoController(application: Application) : AndroidViewModel(application) {
    private val dbHelper = AgendamentoDbHelper(application)

    var agendamentos = mutableStateListOf<Agendamento>()
        private set

    init {
        carregarAgendamentos()
    }

    private fun carregarAgendamentos() {
        agendamentos.clear()
        agendamentos.addAll(dbHelper.listar())
    }

    fun criar(novo: Agendamento) {
        dbHelper.inserir(novo)
        carregarAgendamentos()
    }

    fun concluirAgendamento(id: Int) {
        val agendamento = dbHelper.buscarPorId(id)
        if (agendamento != null) {
            agendamento.status = true
            dbHelper.atualizar(agendamento)
            carregarAgendamentos()
        }
    }

    fun listar(): MutableList<Agendamento> {
        return agendamentos
    }

    fun procurarPorId(id: Int): Agendamento? {
        return dbHelper.buscarPorId(id)
    }

    fun editarPorId(id: Int, novo: Agendamento) {
        val agendamento = dbHelper.buscarPorId(id)
        if (agendamento != null) {
            agendamento.descricao = novo.descricao
            agendamento.data = novo.data
            agendamento.status = novo.status
            dbHelper.atualizar(agendamento)
            carregarAgendamentos()
        }
    }

    fun removerPorId(id: Int) {
        dbHelper.deletar(id)
        carregarAgendamentos()
    }
}

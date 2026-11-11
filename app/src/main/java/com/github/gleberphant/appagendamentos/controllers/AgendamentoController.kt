package com.github.gleberphant.appagendamentos.controllers

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.github.gleberphant.appagendamentos.models.Agendamento
import java.util.Date

// vai guardar o crud dos agendamentos
class AgendamentoController: ViewModel(){
    var proximoID: Int = 4
    var agendamentos = mutableStateListOf<Agendamento>(
        Agendamento(1, Date(), "teste", true),
        Agendamento(2, Date(), "teste", true),
        Agendamento(3, Date(), "teste", true)
    )

    // crud
    fun criar(novo: Agendamento) {
        agendamentos.add(Agendamento(proximoID, novo.data, novo.descricao, novo.status))
        proximoID++
    }

    fun listar() : MutableList<Agendamento>{
        return agendamentos
    }

    fun procurar(index: Int) : Agendamento? {
        if (index > agendamentos.size) return null
        return agendamentos[index]
    }

    fun procurarPorId(id: Int): Agendamento?{

        val index= agendamentos.indexOfFirst { it.id == id }
        if (index==-1 ) return null
        return agendamentos[index]


    }

    fun editar(index: Int, novo: Agendamento){
        if (index > agendamentos.size) return
        agendamentos[index] = novo
    }

    fun editarPorId(id: Int, novo: Agendamento){
        val index = agendamentos.indexOfFirst { it.id == id }
        if (index == -1) return
        agendamentos[index] = novo
    }

    fun remover(index : Int){
        if (index > agendamentos.size) return
        agendamentos.removeAt(index)
    }

    fun removerPorId(id : Int){
        val index = agendamentos.indexOfFirst { it.id == id }
        if (index == -1) return
        agendamentos.removeAt(index)
    }

}
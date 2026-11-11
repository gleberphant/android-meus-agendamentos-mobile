package com.github.gleberphant.appagendamentos.models

import java.util.Date

data class Agendamento (
    val id: Int,
    val data: Date,
    val descricao: String,
    val status: Boolean
)
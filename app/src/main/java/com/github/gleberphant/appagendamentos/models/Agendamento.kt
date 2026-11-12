package com.github.gleberphant.appagendamentos.models

import java.util.Date

data class Agendamento (
    val id: Int,
    var data: Date,
    var descricao: String,
    var status: Boolean
)
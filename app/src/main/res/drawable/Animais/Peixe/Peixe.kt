package com.example.tarefa_5.Animais.Peixe

import com.example.tarefa_5.Animais.Animal

class Peixe(
    idade: Int,
    peso: Double,
    val tipoAgua: String // Ex: doce ou salgada
) : Animal( idade, peso) {
}
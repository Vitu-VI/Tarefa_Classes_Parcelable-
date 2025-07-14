package com.example.tarefa_5.Animais.Bovi

import com.example.tarefa_5.Animais.Animal

open class Bovino(
    idade: Int,
    peso: Double,
    val raca: String
) : Animal(idade, peso)
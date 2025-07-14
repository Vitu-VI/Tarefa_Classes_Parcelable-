package com.example.tarefa_5.Animais.Bovi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Leite(
    override val idade: Int,
    override val peso: Double,
    override val raca: String,
    val litros: Double // produção diária de leite
) : Bovino(idade, peso, raca), Parcelable {
}
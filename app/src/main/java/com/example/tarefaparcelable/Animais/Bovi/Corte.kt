package com.example.tarefa_5.Animais.Bovi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Corte(
    override val idade: Int,
    override val peso: Double,
    override val raca: String,
    val carne: Int  // rendimento da carne, ex: %
) : Bovino(idade, peso, raca), Parcelable {
}
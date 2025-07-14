package com.example.tarefa_5.Animais.Peixe

import android.os.Parcelable
import com.example.tarefa_5.Animais.Animal
import kotlinx.android.parcel.Parcelize


@Parcelize
class Peixe(
    override val idade: Int,
    override val peso: Double,
    override val raca: String,
    val tipoAgua: String // doce, salgada, etc.
) : Animal(idade, peso, raca), Parcelable {
}
package com.example.tarefa_5.Animais.Ave
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Postura(
    override val idade: Int,
    override val peso: Double,
    override val raca: String,
    val ovos: Int  // ovos por dia
) : Ave(idade, peso, raca), Parcelable {


}
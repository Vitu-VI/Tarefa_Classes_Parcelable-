package com.example.tarefa_5.Animais.Ave

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AveCorte(
    override val idade: Int,
    override val peso: Double,
    override val raca: String,
    val carne: Int
) : Ave(idade, peso, raca), Parcelable {
}
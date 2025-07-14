package com.example.tarefa_5.Animais.Bovi

import android.os.Parcelable
import com.example.tarefa_5.Animais.Animal
import kotlinx.android.parcel.Parcelize


@Parcelize
open class Bovino(
   override val idade: Int,
   override val peso: Double,
   override val raca: String
) : Animal(idade, peso, raca),Parcelable{

}
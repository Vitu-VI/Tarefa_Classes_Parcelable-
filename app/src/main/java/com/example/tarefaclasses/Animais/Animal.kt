// Sua classe Animal (já corrigida de acordo com nossas conversas anteriores)
package com.example.tarefa_5.Animais

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Animal(
    open val idade: Int,
    open val peso: Double,
    open val raca: String
): Parcelable {

}
// AnimalData.kt
package com.example.tarefaparcelable.Recycleview

data class AnimalData(
    val id: Int,
    val animal: String,
    val age: Int, // Coluna 'Age' do DB
    val symptom1: String?, // 'Symptom 1' do DB
    val symptom2: String?, // 'Symptom 2' do DB
    val symptom3: String?, // 'Symptom 3' do DB
    val temperature: Float?, // 'Temperature' do DB
    val disease: String?, // 'Disease' do DB
    val dateSymptoms: String? // ADICIONADO: Nova propriedade para a data
)
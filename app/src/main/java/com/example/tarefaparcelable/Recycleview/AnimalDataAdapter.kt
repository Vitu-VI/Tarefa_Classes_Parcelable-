// AnimalDataAdapter.kt
package com.example.tarefaparcelable.Recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarefaparcelable.databinding.ItemAnimalDataBinding

class AnimalDataAdapter(private val animalList: List<AnimalData>) :
    RecyclerView.Adapter<AnimalDataAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(private val binding: ItemAnimalDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animalData: AnimalData) {
            binding.tvAnimalId.text = "ID: ${animalData.id}"
            binding.tvAnimalNome.text = "Animal: ${animalData.animal}"
            binding.tvAnimalIdade.text = "Idade: ${animalData.age}"

            val symptoms = mutableListOf<String>()
            animalData.symptom1?.let { if (it.isNotBlank()) symptoms.add(it) }
            animalData.symptom2?.let { if (it.isNotBlank()) symptoms.add(it) }
            animalData.symptom3?.let { if (it.isNotBlank()) symptoms.add(it) }
            binding.tvSymptoms.text = "Sintomas: ${symptoms.joinToString(", ")}"

            binding.tvTemperatureDisease.text = "Temp: ${animalData.temperature ?: "N/A"}, Doença: ${animalData.disease ?: "N/A"}"

            // ADICIONADO: Preenche o novo TextView com a data
            binding.tvDateSymptoms.text = "Data: ${animalData.dateSymptoms ?: "N/A"}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animalData = animalList[position]
        holder.bind(animalData)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }
}
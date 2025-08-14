package com.example.tarefaparcelable.Recycleview
// AnimalDataAdapter.kt


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarefaparcelable.databinding.ItemAnimalDataBinding



class AnimalDataAdapter(private val animalList: List<AnimalData>) :
    RecyclerView.Adapter<AnimalDataAdapter.AnimalViewHolder>() {

    // O ViewHolder mantém as referências das Views de um item da lista
    // Ele usa o binding do item_animal_data.xml
    class AnimalViewHolder(private val binding: ItemAnimalDataBinding) :
        RecyclerView.ViewHolder(binding.root) { // O root é o layout principal do item

        fun bind(animalData: AnimalData) {
            // Acessa as Views diretamente através do objeto 'binding'
            binding.tvAnimalId.text = "ID: ${animalData.id}"
            binding.tvAnimalNome.text = "Animal: ${animalData.animal}"
            binding.tvAnimalIdade.text = "Idade: ${animalData.age}"

            // Combina sintomas para exibir em um só TextView
            val symptoms = mutableListOf<String>()
            animalData.symptom1?.let { if (it.isNotBlank()) symptoms.add(it) }
            animalData.symptom2?.let { if (it.isNotBlank()) symptoms.add(it) }
            animalData.symptom3?.let { if (it.isNotBlank()) symptoms.add(it) }
            binding.tvSymptoms.text = "Sintomas: ${symptoms.joinToString(", ")}"

            // Combina temperatura e doença
            binding.tvTemperatureDisease.text = "Temp: ${animalData.temperature ?: "N/A"}, Doença: ${animalData.disease ?: "N/A"}"

            // Adicione mais bind() para outras Views/colunas aqui
        }
    }

    // Chamado quando o RecyclerView precisa de um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        // Infla o layout do item usando View Binding
        val binding = ItemAnimalDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    // Chamado para exibir os dados em uma posição específica
    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animalData = animalList[position]
        holder.bind(animalData) // Passa o objeto de dados para o ViewHolder
    }

    // Retorna o número total de itens na lista de dados
    override fun getItemCount(): Int {
        return animalList.size
    }
}
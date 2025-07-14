package com.example.tarefaclasses.Recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarefaclasses.R

class ResumoAdapter(private val lista: List<ResumoItem>):
    RecyclerView.Adapter<ResumoAdapter.ResumoViewHolder>() {

    class ResumoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textRaca = itemView.findViewById<TextView>(R.id.text_raca)
        val textQuantidade = itemView.findViewById<TextView>(R.id.text_quantidade)
        val textPeso = itemView.findViewById<TextView>(R.id.text_peso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemresumo, parent, false)
        return ResumoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResumoViewHolder, position: Int) {
        val item = lista[position]
        holder.textRaca.text = "Raça: ${item.raca}\n"
        holder.textQuantidade.text = "\nQuantidade: ${item.quantidade}\n"
        holder.textPeso.text = "\nPeso total:%.2f kg\n".format(item.pesoTotal)
    }

    override fun getItemCount(): Int = lista.size
}
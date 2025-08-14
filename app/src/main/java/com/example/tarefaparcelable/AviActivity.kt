package com.example.tarefaparcelable

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefa_5.Animais.Ave.AveCorte
import com.example.tarefa_5.Animais.Ave.Postura
import com.example.tarefaparcelable.databinding.ActivityAviBinding

class AviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAviBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAviBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val i = intent

        val apelido = i.getStringExtra("apelido").toString().trim()
        val escolha = i.getStringExtra("avi").toString().trim()
        val lista = ArrayList<String>()
        lista.add("Corte")
        lista.add("")
        lista.add("Postura")


        binding.listViewItem.setOnItemClickListener{_, _, position, _ ->
            val item = lista[position]
            if( item == "Corte"){
                val j = Intent(this, InfoActivity::class.java)
                val corte1 = AveCorte(3,12.2, "Cobb", 62)
                val corte2 = AveCorte(12,11.5, "Hubbard", 58)
                j.putExtra("animal1", corte1)
                j.putExtra("animal2", corte2)
                startActivity(j)

            }
            if( item == "Postura"){
                val j = Intent(this, InfoActivity::class.java)
                val postura1 = Postura(4,7.5, "Isa Brown", 30)
                val postura2 = Postura(10,9.3, "Dekalb White", 22)
                j.putExtra("animal1", postura1)
                j.putExtra("animal2", postura2)
                startActivity(j)
            }

        }





        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,lista)
        binding.listViewItem.adapter = adapter

        binding.textApelido.setText("Bem Vindo(a) $apelido")
        binding.textEscolha.setText(escolha)

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

    }
}
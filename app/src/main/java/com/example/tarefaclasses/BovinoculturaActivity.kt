package com.example.tarefaclasses

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefa_5.Animais.Bovi.Corte
import com.example.tarefa_5.Animais.Bovi.Leite
import com.example.tarefaclasses.databinding.ActivityBovinoculturaBinding

class BovinoculturaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBovinoculturaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBovinoculturaBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val i = intent

        val apelido = i.getStringExtra("apelido").toString().trim()
        val escolha = i.getStringExtra("bovi").toString().trim()
        val lista = ArrayList<String>()
        lista.add("Corte")
        lista.add("")
        lista.add("Leite")


        binding.listViewItem.setOnItemClickListener{_, _, position, _ ->
            val item = lista[position]
            if( item == "Corte"){
                val j = Intent(this, InfoActivity::class.java)
                val corte1 = Corte(3,600.2, "Angus", 62)
                val corte2 = Corte(12,670.2, "Nelore", 58)
                j.putExtra("animal1", corte1)
                j.putExtra("animal2", corte2)


                startActivity(j)

            }
            if( item == "Leite"){
                val j = Intent(this, InfoActivity::class.java)
                val leite1 = Leite(4,400.5, "Holandesa", 300.1)
                val leite2 = Leite(10,390.3, "Jersey", 220.7)
                j.putExtra("animal1", leite1)
                j.putExtra("animal2", leite2)
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
package com.example.tarefaparcelable

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefa_5.Animais.Peixe.Peixe
import com.example.tarefaparcelable.databinding.ActivityPsiBinding

class PsiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPsiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPsiBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val i = intent

        val apelido = i.getStringExtra("apelido").toString().trim()
        val escolha = i.getStringExtra("psi").toString().trim()
        val lista = ArrayList<String>()
        lista.add("Peixe")



        binding.listViewItem.setOnItemClickListener{_, _, position, _ ->
            val item = lista[position]
            if( item == "Peixe"){
                val j = Intent(this, InfoActivity::class.java)
                val corte1 = Peixe(3,16.2, "Tambaqui", "Doce")
                val corte2 = Peixe(9,17.5, "Robalo ", "Salgada")
                j.putExtra("animal1", corte1)
                j.putExtra("animal2", corte2)
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
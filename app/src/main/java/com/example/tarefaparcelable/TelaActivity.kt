package com.example.tarefaparcelable


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefaparcelable.databinding.ActivityTelaBinding


class TelaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTelaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val j = intent
        val apelido = j.getStringExtra("apelido").toString().trim()
        var primeiro = true // Flag para evitar que o listener dispare no primeiro carregamento

        val spinner = listOf("Bovicultura", "Avicultura", "Pscicultura", "Doenças")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOpcoes.adapter = adapter

        binding.textApelido.setText("Bem Vindo(a) $apelido")

        binding.spinnerOpcoes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (primeiro) {
                    primeiro = false
                    return
                }

                when (parent.getItemAtPosition(position) as String) {
                    "Bovicultura" -> {
                        val i = Intent(this@TelaActivity, BovinoculturaActivity::class.java)
                        val escolha = "Bovicultura"
                        i.putExtras(j)
                        i.putExtra("bovi", escolha)
                        startActivity(i)
                    }
                    "Avicultura" -> {
                        val i = Intent(this@TelaActivity, AviActivity::class.java)
                        val escolha = "Avicultura"
                        i.putExtras(j)
                        i.putExtra("avi", escolha)
                        startActivity(i)
                    }
                    "Pscicultura" -> {
                        val i = Intent(this@TelaActivity, PsiActivity::class.java)
                        val escolha = "Pscicultura"
                        i.putExtras(j)
                        i.putExtra("psi", escolha)
                        startActivity(i)
                    }
                    "Doenças" -> {
                        val i = Intent(this@TelaActivity, TipoDoencaActivity::class.java)
                        startActivity(i)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.buttonSair.setOnClickListener {
            finish()
        }
    }
}
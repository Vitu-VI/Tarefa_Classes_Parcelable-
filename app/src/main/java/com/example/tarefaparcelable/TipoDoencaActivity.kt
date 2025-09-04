package com.example.tarefaparcelable

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefaparcelable.databinding.ActivityTipoDoencaBinding

class TipoDoencaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipoDoencaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTipoDoencaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        var primeiro = true // Flag para evitar que o listener dispare no primeiro carregamento
        var segundo = true
        val spinner = listOf("Búfalos", "Vacas", "Cabras", "Ovelhas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOpcoes1.adapter = adapter
        binding.spinnerOpcoes2.adapter = adapter


        binding.spinnerOpcoes1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (primeiro) {
                    primeiro = false
                    return
                }

                when (parent.getItemAtPosition(position) as String) {
                    "Búfalos" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Búfalos"
                        i.putExtra("Buffalo", escolha)
                        startActivity(i)
                    }
                    "Vacas" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Vacas"
                        i.putExtra("Cow", escolha)
                        startActivity(i)
                    }
                    "Ovelhas" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Ovelhas"
                        i.putExtra("Sheep", escolha)
                        startActivity(i)
                    }
                    "Cabras" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Cabras"
                        i.putExtra("Goat", escolha)
                        startActivity(i)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        binding.spinnerOpcoes2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (segundo) {
                    segundo = false
                    return
                }

                when (parent.getItemAtPosition(position) as String) {
                    "Búfalos" -> {
                        val i = Intent(this@TipoDoencaActivity, GrafActivity::class.java)
                        val escolha = "Búfalos"
                        i.putExtra("Buffalo", escolha)
                        startActivity(i)
                    }
                    "Vacas" -> {
                        val i = Intent(this@TipoDoencaActivity, GrafActivity::class.java)
                        val escolha = "Vacas"
                        i.putExtra("Cow", escolha)
                        startActivity(i)
                    }
                    "Ovelhas" -> {
                        val i = Intent(this@TipoDoencaActivity, GrafActivity::class.java)
                        val escolha = "Ovelhas"
                        i.putExtra("Sheep", escolha)
                        startActivity(i)
                    }
                    "Cabras" -> {
                        val i = Intent(this@TipoDoencaActivity, GrafActivity::class.java)
                        val escolha = "Cabras"
                        i.putExtra("Goat", escolha)
                        startActivity(i)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}
package com.example.tarefaparcelable

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

        val spinner = listOf("Buffalo", "Cow", "Goat", "Sheep")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOpcoes.adapter = adapter



        binding.spinnerOpcoes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (primeiro) {
                    primeiro = false
                    return
                }

                when (parent.getItemAtPosition(position) as String) {
                    "Buffalo" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Buffalo"
                        i.putExtra("Buffalo", escolha)
                        startActivity(i)
                    }
                    "Cow" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Cow"
                        i.putExtra("Cow", escolha)
                        startActivity(i)
                    }
                    "Sheep" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Sheep"
                        i.putExtra("Sheep", escolha)
                        startActivity(i)
                    }
                    "Goat" -> {
                        val i = Intent(this@TipoDoencaActivity, DoencaActivity::class.java)
                        val escolha = "Goat"
                        i.putExtra("Goat", escolha)
                        startActivity(i)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }
}
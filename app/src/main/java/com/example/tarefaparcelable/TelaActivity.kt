package com.example.tarefaparcelable

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefaparcelable.databinding.ActivityTelaBinding

class TelaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTelaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recebe os dados da Intent anterior (como o apelido)
        val j = intent
        val apelido = j.getStringExtra("apelido")?.trim() ?: "Usuário"

        // Define a mensagem de boas-vindas
        binding.textApelido.text = "Bem Vindo(a) $apelido"

        // Configura os cliques para cada cartão
        setupCardClickListeners()


    }

    private fun setupCardClickListeners() {
        val originalIntentExtras = intent.extras

        binding.cardBovinocultura.setOnClickListener {
            val intent = Intent(this, BovinoculturaActivity::class.java)
            if (originalIntentExtras != null) {
                intent.putExtras(originalIntentExtras)
            }
            intent.putExtra("bovi", "Bovinocultura")
            startActivity(intent)
        }

        binding.cardAvicultura.setOnClickListener {
            val intent = Intent(this, AviActivity::class.java)
            if (originalIntentExtras != null) {
                intent.putExtras(originalIntentExtras)
            }
            intent.putExtra("avi", "Avicultura")
            startActivity(intent)
        }

        binding.cardPiscicultura.setOnClickListener {
            val intent = Intent(this, PsiActivity::class.java)
            if (originalIntentExtras != null) {
                intent.putExtras(originalIntentExtras)
            }
            intent.putExtra("psi", "Piscicultura")
            startActivity(intent)
        }

        binding.cardDoencas.setOnClickListener {
            val intent = Intent(this, TipoDoencaActivity::class.java)
            startActivity(intent)
        }
    }
}
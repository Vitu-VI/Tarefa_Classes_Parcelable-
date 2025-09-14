package com.example.tarefaparcelable

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tarefaparcelable.databinding.ActivityTipoDoencaBinding

class TipoDoencaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipoDoencaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTipoDoencaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // --- ALTERAÇÃO 1: Deixar os botões invisíveis no início ---
        binding.btnSintomas.visibility = View.GONE
        binding.btnGrafico.visibility = View.GONE

        // Chama a função para configurar os RadioButtons com imagens
        setupRadioButtonsWithImages()

        // --- ALTERAÇÃO 2: Adicionar um listener ao RadioGroup ---
        binding.radioGroupAnimais.setOnCheckedChangeListener { group, checkedId ->
            // Quando qualquer RadioButton for selecionado, torna os botões visíveis
            binding.btnSintomas.visibility = View.VISIBLE
            binding.btnGrafico.visibility = View.VISIBLE
        }

        binding.btnSintomas.setOnClickListener {
            navigateTo(DoencaActivity::class.java)
        }

        binding.btnGrafico.setOnClickListener {
            navigateTo(GrafActivity::class.java)
        }
    }

    private fun setupRadioButtonsWithImages() {
        // Define o tamanho desejado para as imagens em dp (ex: 64dp)
        val imageSizeDp = 100
        // Converte dp para pixels, para que a imagem tenha o mesmo tamanho em todas as telas
        val sizeInPixels = (imageSizeDp * resources.displayMetrics.density).toInt()

        // Configura cada RadioButton
        setRadioButtonImage(binding.radioBufalo, R.drawable.bufalo, sizeInPixels)
        setRadioButtonImage(binding.radioVaca, R.drawable.holandesa, sizeInPixels)
        setRadioButtonImage(binding.radioCabra, R.drawable.cabra, sizeInPixels)
        setRadioButtonImage(binding.radioOvelha, R.drawable.ovelha, sizeInPixels)
    }

    /**
     * Função auxiliar para carregar, redimensionar e atribuir uma imagem a um RadioButton.
     */
    private fun setRadioButtonImage(radioButton: RadioButton, drawableId: Int, size: Int) {
        // Carrega a imagem da pasta drawable
        val drawable = ContextCompat.getDrawable(this, drawableId)
        // Define o tamanho exato da imagem
        drawable?.setBounds(0, 0, size, size)
        // Atribui a imagem à direita do botão, e remove o texto
        radioButton.setCompoundDrawables(null, null, drawable, null)
        radioButton.text = "" // Remove o texto para mostrar apenas a imagem
    }

    private fun navigateTo(targetActivity: Class<*>) {
        val selectedId = binding.radioGroupAnimais.checkedRadioButtonId

        if (selectedId == -1) {
            Toast.makeText(this, "Por favor, selecione uma espécie.", Toast.LENGTH_SHORT).show()
            return
        }

        val (key, value) = when (selectedId) {
            R.id.radioBufalo -> "Buffalo" to "Búfalos"
            R.id.radioVaca -> "Cow" to "Vacas"
            R.id.radioCabra -> "Goat" to "Cabras"
            R.id.radioOvelha -> "Sheep" to "Ovelhas"
            else -> "" to ""
        }

        val intent = Intent(this, targetActivity)
        intent.putExtra(key, value)
        startActivity(intent)
    }
}
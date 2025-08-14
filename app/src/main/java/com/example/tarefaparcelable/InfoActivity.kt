package com.example.tarefaparcelable

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefa_5.Animais.Animal
import com.example.tarefa_5.Animais.Ave.AveCorte
import com.example.tarefa_5.Animais.Ave.Postura
import com.example.tarefa_5.Animais.Bovi.Corte
import com.example.tarefa_5.Animais.Bovi.Leite
import com.example.tarefa_5.Animais.Peixe.Peixe
import com.example.tarefaparcelable.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera os objetos Parcelable. Eles são do tipo Animal? (possivelmente nulo)
        val animal1: Animal? = intent.getParcelableExtra("animal1", Animal::class.java)
        val animal2: Animal? = intent.getParcelableExtra("animal2", Animal::class.java)

        // --- Adicione esta verificação para garantir que os objetos não são nulos ---
        if (animal1 == null || animal2 == null) {
            // Se um dos animais for nulo, algo deu errado.
            // Você pode exibir uma mensagem para o usuário ou simplesmente finalizar a Activity
            // para evitar um travamento.
            // Ex: Toast.makeText(this, "Erro ao carregar dados do animal!", Toast.LENGTH_LONG).show()
            finish()
            return // Impede que o restante do código seja executado se os dados estiverem faltando
        }
        // --- Fim da verificação ---

        // Agora que sabemos que animal1 e animal2 não são nulos, podemos acessá-los com segurança.
        // O Kotlin faz um "smart cast" para Animal (não nullável) dentro deste bloco if.
        binding.textTeste1.text = animal1.raca
        binding.textTeste2.text = animal2.raca


        when (animal1) {
            is Corte     -> binding.textValor1.text = "Rendimento: ${animal1.carne}%"
            is Leite     -> binding.textValor1.text = "Litros: ${animal1.litros}"
            is Postura   -> binding.textValor1.text = "Ovos: ${animal1.ovos}"
            is AveCorte  -> binding.textValor1.text = "Rendimento: ${animal1.carne}%"
            is Peixe     -> binding.textValor1.text = "Água: ${animal1.tipoAgua}"
            else -> binding.textValor1.text = "Dados específicos não disponíveis" // Caso não seja um tipo esperado
        }
        binding.textPeso1.text = "${animal1.peso} kg"


        when (animal2) {
            is Corte     -> binding.textValor2.text = "Rendimento: ${animal2.carne}%"
            is Leite     -> binding.textValor2.text = "Litros: ${animal2.litros}"
            is Postura   -> binding.textValor2.text = "Ovos: ${animal2.ovos}"
            is AveCorte  -> binding.textValor2.text = "Rendimento: ${animal2.carne}%"
            is Peixe     -> binding.textValor2.text = "Água: ${animal2.tipoAgua}"
            else -> binding.textValor2.text = "Dados específicos não disponíveis" // Caso não seja um tipo esperado
        }
        binding.textPeso2.text = "${animal2.peso} kg"



        binding.checkTipo1.setOnClickListener{
            if(binding.checkTipo1.isChecked ){
                binding.edit1.setText("1")
                binding.textPeso1.visibility = View.VISIBLE

            }else{
                binding.edit1.setText("0")
                binding.textPeso1.visibility = View.GONE
            }
        }
        binding.checkTipo2.setOnClickListener{
            if(binding.checkTipo2.isChecked ){
                binding.edit2.setText("1")
                binding.textPeso2.visibility = View.VISIBLE

            }else{
                binding.edit2.setText("0")
                binding.textPeso2.visibility = View.GONE
            }
        }
        binding.buttonVoltar.setOnClickListener {
            finish()
        }
        binding.buttonFinalizar.setOnClickListener {
            val j = Intent(this , ResumoActivity::class.java)
            j.putExtra("quant1",binding.edit1.text.toString())
            j.putExtra("quant2",binding.edit2.text.toString())
            // Passando os objetos Animal para a próxima Activity (eles são Animal! aqui)
            j.putExtra("animal1", animal1)
            j.putExtra("animal2", animal2)
            startActivity(j)
            finish()
        }
    }
}
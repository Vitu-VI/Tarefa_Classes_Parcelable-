package com.example.tarefaclasses

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarefa_5.Animais.Animal
import com.example.tarefaclasses.Recycleview.ResumoAdapter
import com.example.tarefaclasses.Recycleview.ResumoItem
import com.example.tarefaclasses.databinding.ActivityResumoBinding

class ResumoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumoBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animal1: Animal? = intent.getParcelableExtra("animal1", Animal::class.java)
        val animal2: Animal? = intent.getParcelableExtra("animal2", Animal::class.java)

        val quant1 = intent.getStringExtra("quant1")?.toIntOrNull() ?: 0
        val quant2 = intent.getStringExtra("quant2")?.toIntOrNull() ?: 0

        if (animal1 == null || animal2 == null) {

            finish()
            return
        }

        val listaResumo = listOf(
            ResumoItem(animal1.raca, quant1, quant1 * animal1.peso),
            ResumoItem(animal2.raca, quant2, quant2 * animal2.peso)
        )

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = ResumoAdapter(listaResumo)
        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }
}
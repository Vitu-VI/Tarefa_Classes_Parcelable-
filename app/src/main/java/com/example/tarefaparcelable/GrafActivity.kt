package com.example.tarefaparcelable

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.tarefaparcelable.databinding.ActivityGrafBinding
import com.github.mikephil.charting.components.Legend
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.concurrent.Executors


class GrafActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGrafBinding
    private val client = OkHttpClient()
    private val executorService = Executors.newSingleThreadExecutor()

    private var escolhaTipoAnimal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrafBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            for (key in extras.keySet()) {
                if (key == "Buffalo" || key == "Cow" || key == "Goat" || key == "Sheep") {
                    escolhaTipoAnimal = extras.getString(key)
                    break
                }
            }
        }

        // Exibe o nome que foi recebido, já em português
        binding.textTipo.setText("Doenças no rebanho de $escolhaTipoAnimal")

        if (escolhaTipoAnimal != null) {
            fetchDataAndSetupPieChart(escolhaTipoAnimal!!)
        } else {
            binding.pieChart.centerText = "Erro: Nenhuma escolha de animal recebida."
            binding.pieChart.setCenterTextColor(Color.RED)
            binding.pieChart.setCenterTextSize(16f)
            binding.pieChart.invalidate()
        }
    }

    private fun fetchDataAndSetupPieChart(animal: String) {
        // --- AQUI É ONDE O NOME É TRADUZIDO DE PORTUGUÊS PARA INGLÊS ---
        val animalNomeEmIngles = translateToEnglishForQuery(animal)
        val viewName = "doencas_porcentagem_${animalNomeEmIngles}_view"

        val url = "http://192.168.1.107:3000/$viewName"
        Log.d("GraficoApp", "URL de requisição: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Accept", "application/json")
            .build()

        executorService.execute {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    val jsonArray = JSONArray(responseBody)
                    val entries = ArrayList<PieEntry>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObj = jsonArray.getJSONObject(i)
                        val doenca = jsonObj.getString("doenca")
                        val porcentagem = jsonObj.getDouble("porcentagem").toFloat()

                        if (porcentagem > 0f) {
                            entries.add(PieEntry(porcentagem, doenca))
                        }
                    }

                    runOnUiThread {
                        if (entries.isEmpty()) {
                            binding.pieChart.centerText = "Nenhum dado disponível."
                            binding.pieChart.setCenterTextColor(Color.GRAY)
                            binding.pieChart.setCenterTextSize(16f)
                            binding.pieChart.invalidate()
                        } else {
                            setupPieChart(entries, animal)
                        }
                    }
                } else {
                    runOnUiThread {
                        Log.e("GraficoApp", "Erro na consulta: ${response.code} - ${response.message}")
                        binding.pieChart.centerText = "Erro na consulta: ${response.code}"
                        binding.pieChart.setCenterTextColor(Color.RED)
                    }
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Log.e("GraficoApp", "Erro de rede", e)
                    binding.pieChart.centerText = "Erro de rede."
                    binding.pieChart.setCenterTextColor(Color.RED)
                }
            } catch (e: JSONException) {
                runOnUiThread {
                    Log.e("GraficoApp", "Erro de parsing JSON: ${e.message}", e)
                    binding.pieChart.centerText = "Erro de dados."
                    binding.pieChart.setCenterTextColor(Color.RED)
                }
            }
        }
    }

    private fun setupPieChart(entries: ArrayList<PieEntry>, animal: String) {
        val dataSet = PieDataSet(entries, "") // removi "Doenças" para não duplicar na legenda
        dataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        dataSet.sliceSpace = 3f
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = PercentFormatter() // usa os valores da view (já em %)

        val data = PieData(dataSet)
        binding.pieChart.data = data

        // Já vem em porcentagem, não recalcula
        binding.pieChart.setUsePercentValues(false)

        // Configurações visuais
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.setEntryLabelTextSize(12f)
        binding.pieChart.setExtraOffsets(10f, 10f, 10f, 10f)
        binding.pieChart.animateY(1000)

        binding.pieChart.centerText = "Análise de Doenças em %\n($animal)"
        binding.pieChart.setCenterTextSize(16f)
        binding.pieChart.setCenterTextColor(Color.BLACK)
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.holeRadius = 50f
        binding.pieChart.setHoleColor(Color.WHITE)

        // 👉 Legenda ajustada para quebrar em linhas quando necessário
        val legend = binding.pieChart.legend
        legend.isEnabled = true
        legend.textSize = 14f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isWordWrapEnabled = true   
        legend.yEntrySpace = 8f           // espaço entre linhas

        binding.pieChart.invalidate()
    }



    // MÉTODO DE TRADUÇÃO DE PORTUGUÊS PARA INGLÊS PARA A URL
    private fun translateToEnglishForQuery(animal: String?): String {
        return when (animal) {
            "Búfalos" -> "buffalo"
            "Vacas" -> "cow"
            "Cabras" -> "goat"
            "Ovelhas" -> "sheep"
            else -> ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdown()
    }
}
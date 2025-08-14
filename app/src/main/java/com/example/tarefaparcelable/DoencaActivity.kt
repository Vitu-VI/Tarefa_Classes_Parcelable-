package com.example.tarefaparcelable

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarefaparcelable.Recycleview.AnimalData
import com.example.tarefaparcelable.Recycleview.AnimalDataAdapter
import com.example.tarefaparcelable.databinding.ActivityDoencaBinding
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.concurrent.Executors


class DoencaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoencaBinding
    private val client = OkHttpClient()
    private val executorService = Executors.newSingleThreadExecutor()

    private lateinit var animalDataAdapter: AnimalDataAdapter
    private val animalDataList = mutableListOf<AnimalData>()

    // Variável para armazenar a escolha do animal recebida
    private var escolhaTipoAnimal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoencaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // --- RECEBE O PARÂMETRO DA INTENT ---
        val extras = intent.extras
        if (extras != null) {
            for (key in extras.keySet()) {
                if (key == "Buffalo" || key == "Cow" || key == "Goat" || key == "Sheep") {
                    escolhaTipoAnimal = extras.getString(key)
                    break
                }
            }
        }
        binding.nomeText.setText(escolhaTipoAnimal)
        // --- Configuração do RecyclerView ---
        binding.recyclerViewAnimalData.layoutManager = LinearLayoutManager(this)
        animalDataAdapter = AnimalDataAdapter(animalDataList)
        binding.recyclerViewAnimalData.adapter = animalDataAdapter

        // --- Configuração do Botão de Consulta ---
        binding.btnConsultar.setOnClickListener {
            if (escolhaTipoAnimal != null) {
                consultarDadosAnimal()
            } else {
                binding.tvStatusMessage.text = "Status: Nenhuma escolha de animal recebida."
            }
        }
    }

    private fun consultarDadosAnimal() {
        val viewName = when (escolhaTipoAnimal) {
            "Buffalo" -> "doencas_buffalo_view"
            "Cow" -> "doencas_cow_view"
            "Goat" -> "doencas_goat_view"
            "Sheep" -> "doencas_sheep_view"
            else -> {
                binding.tvStatusMessage.text = "Status: Escolha de animal inválida."
                return
            }
        }

        // --- Pegar os inputs e filtrar os vazios ---
        val inputs = listOf(
            binding.editDeonca1.text.toString(),
            binding.editDeonca2.text.toString(),
            binding.editDeonca3.text.toString()
        ).filter { it.isNotBlank() }

        val symptomColumns = listOf("\"Symptom 1\"", "\"Symptom 2\"", "\"Symptom 3\"")
        val andConditions = mutableListOf<String>()

        // Para cada valor de entrada do usuário (sintoma A, B, C...)
        if (inputs.isNotEmpty()) {
            for (inputValue in inputs) {
                val orForThisValue = mutableListOf<String>()
                // Cria uma condição OR para cada coluna de sintoma
                for (column in symptomColumns) {
                    orForThisValue.add("$column.ilike.*$inputValue*")
                }
                // Agrupa todas as condições de OR do sintoma atual com parênteses
                andConditions.add("or(${orForThisValue.joinToString(",")})")
            }
        }

        val httpUrlBuilder = HttpUrl.Builder()
            .scheme("http")
            .host("172.29.255.147")
            .port(3000)
            .addPathSegment(viewName)

        if (andConditions.isNotEmpty()) {
            val finalAndFilter = andConditions.joinToString(",")

            httpUrlBuilder.addQueryParameter("and", "($finalAndFilter)")
        }

        val url = httpUrlBuilder.build().toString()

        Log.d("ConsultaApp", "URL de requisição: $url") // Para depuração

        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Accept", "application/json")
            .build()

        animalDataList.clear()
        runOnUiThread {
            binding.tvStatusMessage.text = "Status: Consultando dados de $escolhaTipoAnimal..."
            animalDataAdapter.notifyDataSetChanged()
        }

        executorService.execute {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    val jsonArray = JSONArray(responseBody)
                    val receivedAnimalData = mutableListOf<AnimalData>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObj = jsonArray.getJSONObject(i)
                        try {
                            val id = jsonObj.optInt("id", -1)
                            val animalNome = jsonObj.optString("Animal", "N/A")
                            val animalIdade = jsonObj.optInt("Age", -1)
                            val symptom1 = jsonObj.optString("Symptom 1", null)
                            val symptom2 = jsonObj.optString("Symptom 2", null)
                            val symptom3 = jsonObj.optString("Symptom 3", null)
                            val temperature = if (jsonObj.has("Temperature")) jsonObj.optDouble("Temperature").toFloat() else null
                            val disease = jsonObj.optString("Disease", null)

                            receivedAnimalData.add(
                                AnimalData(id, animalNome, animalIdade, symptom1, symptom2, symptom3, temperature, disease)
                            )
                        } catch (e: JSONException) {
                            Log.e("ConsultaApp", "Erro ao parsear objeto JSON: ${e.message} - Objeto: $jsonObj", e)
                        }
                    }

                    runOnUiThread {
                        animalDataList.addAll(receivedAnimalData)
                        animalDataAdapter.notifyDataSetChanged()
                        binding.tvStatusMessage.text = "Status: ${animalDataList.size} dados carregados para $escolhaTipoAnimal."
                    }
                } else {
                    runOnUiThread {
                        binding.tvStatusMessage.text = "Erro na consulta: ${response.code} - ${response.message}"
                        Log.e("ConsultaApp", "Erro: ${response.code} - ${response.message} - ${responseBody}")
                    }
                }
            } catch (e: IOException) {
                runOnUiThread {
                    binding.tvStatusMessage.text = "Erro de rede: ${e.message}"
                    Log.e("ConsultaApp", "Erro de rede", e)
                }
            } catch (e: JSONException) {
                runOnUiThread {
                    binding.tvStatusMessage.text = "Erro de parsing JSON: ${e.message}"
                    Log.e("ConsultaApp", "Erro de parsing JSON", e)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdown()
    }
}
package com.example.tarefaparcelable

import android.app.DatePickerDialog
import android.content.Intent
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.Executors
import kotlin.jvm.java


class DoencaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoencaBinding
    private val client = OkHttpClient()
    private val executorService = Executors.newSingleThreadExecutor()

    private lateinit var animalDataAdapter: AnimalDataAdapter
    private val animalDataList = mutableListOf<AnimalData>()
    private var escolhaTipoAnimal: String? = null

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDoencaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
        binding.nomeText.setText(escolhaTipoAnimal)

        binding.recyclerViewAnimalData.layoutManager = LinearLayoutManager(this)
        animalDataAdapter = AnimalDataAdapter(animalDataList)
        binding.recyclerViewAnimalData.adapter = animalDataAdapter

        binding.tvStartDate.setOnClickListener {
            showDatePicker(isStartDate = true)
        }
        binding.tvEndDate.setOnClickListener {
            showDatePicker(isStartDate = false)
        }

        binding.btnConsultar.setOnClickListener {
            if (escolhaTipoAnimal != null) {
                consultarDadosAnimal()
            } else {
                binding.tvStatusMessage.text = "Status: Nenhuma escolha de animal recebida."
            }

        }



    }

    private fun showDatePicker(isStartDate: Boolean) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                myCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
                val dateFormatDisplay = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormatDisplay.format(myCalendar.time)

                if (isStartDate) {
                    startDate = myCalendar
                    binding.tvStartDate.text = "Data Inicial: $formattedDate"
                } else {
                    endDate = myCalendar
                    binding.tvEndDate.text = "Data Final: $formattedDate"
                }
            }, year, month, day
        )
        datePicker.show()
    }

    // ... dentro da sua classe DoencaActivity ...
    private fun consultarDadosAnimal() {
        val viewName = when (escolhaTipoAnimal) {
            "Búfalos" -> "doencas_buffalo2_view"
            "Vacas" -> "doencas_cow2_view"
            "Cabras" -> "doencas_goat2_view"
            "Ovelhas" -> "doencas_sheep2_view"
            else -> {
                binding.tvStatusMessage.text = "Status: Escolha de animal inválida."
                return
            }
        }

        val inputs = listOf(
            binding.editDeonca1.text.toString(),
            binding.editDeonca2.text.toString(),
            binding.editDeonca3.text.toString()
        ).filter { it.isNotBlank() }

        val symptomColumns = listOf("\"Symptom 1\"", "\"Symptom 2\"", "\"Symptom 3\"")

        val httpUrlBuilder = HttpUrl.Builder()
            .scheme("http")
            .host("192.168.1.107")
            .port(3000)
            .addPathSegment(viewName)

        // Lógica para adicionar filtros de sintomas
        if (inputs.isNotEmpty()) {
            val orConditions = mutableListOf<String>()
            for (inputValue in inputs) {
                for (column in symptomColumns) {
                    orConditions.add("$column.ilike.*$inputValue*")
                }
            }
            val finalOrFilter = orConditions.joinToString(",")
            httpUrlBuilder.addQueryParameter("or", "($finalOrFilter)")
        }

        // Lógica para adicionar filtros de data
        val dateFormatQuery = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        startDate?.let {
            val formattedStartDate = dateFormatQuery.format(it.time)
            // Adiciona a data como um parâmetro separado
            httpUrlBuilder.addQueryParameter("date_symptoms", "gte.$formattedStartDate")
        }

        endDate?.let {
            val formattedEndDate = dateFormatQuery.format(it.time)
            // Adiciona a data como um parâmetro separado
            httpUrlBuilder.addQueryParameter("date_symptoms", "lte.$formattedEndDate")
        }

        val url = httpUrlBuilder.build().toString()

        Log.d("ConsultaApp", "URL de requisição: $url")

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
                            val dateSymptoms = jsonObj.optString("date_symptoms", null)


                            receivedAnimalData.add(
                                AnimalData(id, animalNome, animalIdade, symptom1, symptom2, symptom3, temperature, disease, dateSymptoms)
                            )
                        } catch (e: JSONException) {
                            Log.e("ConsultaApp", "Erro ao parsear objeto JSON: ${e.message} - Objeto: $jsonObj", e)
                        }
                    }

                    runOnUiThread {
                        animalDataList.addAll(receivedAnimalData)
                        animalDataAdapter.notifyDataSetChanged()
                        binding.tvStatusMessage.text = "Status: ${animalDataList.size} $escolhaTipoAnimal encontrados(as)."
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
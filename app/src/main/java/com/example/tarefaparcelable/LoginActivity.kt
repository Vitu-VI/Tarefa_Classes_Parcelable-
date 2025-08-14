package com.example.tarefaparcelable

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefaparcelable.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.buttonEntrar.setOnClickListener {
            val username = binding.editText1.text.toString().trim()
            val password = binding.editText2.text.toString().trim()
            val apelido = binding.editText3.text.toString().trim()

            if(username.equals("user") && password.equals("pass123") && !apelido.isEmpty()){
                Toast.makeText(applicationContext,"Login OK", Toast.LENGTH_SHORT).show()
                val i = Intent(this, TelaActivity::class.java)
                i.putExtra("apelido", apelido)
                startActivity(i)

                finish()

            }else{
                Toast.makeText(applicationContext,"Login Inválido", Toast.LENGTH_SHORT).show()

            }

            binding.editText1.setText("")
            binding.editText2.setText("")
            binding.editText3.setText("")
        }
    }
}
package com.example.meuimc

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meuimc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val intent = Intent(this, ResultActivity::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())

            v.setPadding(
                systemBars.left, systemBars.top, systemBars.right,
                maxOf(systemBars.bottom, ime.bottom)
            )
            insets
        }

        binding.buttonCalcular.setOnClickListener {
            val altura = binding.editAltura.text.toString().trim()
            val peso = binding.editPeso.text.toString().trim()

            if (altura.isEmpty()) {
                if (peso.isNotEmpty()) binding.txtCampoPeso.text = ""
                binding.txtCampoAltura.text = "Preencha o campo*"
            } else if (peso.isEmpty()) {
                if (altura.isNotEmpty()) binding.txtCampoAltura.text = ""
                binding.txtCampoPeso.text = "Preencha o campo*"
            } else {
                intent.putExtra("CHAVE_IMC", calcularImc(altura.toDouble(), peso.toDouble()))

                limparTextos()
                startActivity(intent)
            }
        }
    }

    private fun calcularImc(altura: Double, peso: Double): Double {
        return peso / (altura * altura)
    }

    private fun limparTextos() {
        binding.txtCampoAltura.text = ""
        binding.txtCampoPeso.text = ""
        binding.editPeso.setText("")
        binding.editAltura.setText("")
    }
}
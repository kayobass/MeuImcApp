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

            binding.txtCampoAltura.text = ""
            binding.txtCampoPeso.text = ""

            when {
                altura.isEmpty() -> {
                    binding.txtCampoAltura.text = "Preencha o campo*"
                    binding.editAltura.requestFocus()
                }
                altura.toDouble() == 0.0 -> {
                    binding.txtCampoAltura.text = "Coloque um valor maior que 0*"
                    binding.editAltura.requestFocus()
                }
                peso.isEmpty() -> {
                    binding.txtCampoPeso.text = "Preencha o campo*"
                    binding.editPeso.requestFocus()
                }
                peso.toDouble() == 0.0 -> {
                    binding.txtCampoPeso.text = "Coloque um valor maior que 0*"
                    binding.editPeso.requestFocus()
                }
                else -> {
                    val imc = calcularImc(altura.toDouble(), peso.toDouble())
                    intent.putExtra("CHAVE_IMC", imc)
                    limparTextos()
                    startActivity(intent)
                }
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
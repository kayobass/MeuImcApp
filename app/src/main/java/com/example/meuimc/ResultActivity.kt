package com.example.meuimc

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meuimc.databinding.ActivityResultBinding
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val imc = intent.getDoubleExtra("CHAVE_IMC", 0.0)
        binding = ActivityResultBinding.inflate(layoutInflater)

        val decimalFormat = DecimalFormat("##.##")
        val imcValue = decimalFormat.format(imc)
        lateinit var imcStatus: String
        binding.txtImc.text = imcValue

        when {
            imc < 17.0 -> {
                imcStatus = "Muito Abaixo"
                binding.txtImc.setTextColor(ContextCompat.getColor(this,
                    R.color.muito_abaixo))
                binding.txtMuitoAbaixo.text = "-> $imcStatus"
                binding.txtMuitoAbaixo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
            }

            imc < 18.5 -> {
                imcStatus = "Abaixo"
                binding.txtImc.setTextColor(ContextCompat.getColor(this,
                    R.color.abaixo))
                binding.txtAbaixo.text = "-> $imcStatus"
                binding.txtAbaixo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)

            }

            imc < 25 -> {
                imcStatus = "Normal"
                binding.txtImc.setTextColor(ContextCompat.getColor(this,
                    R.color.normal))
                binding.txtNormal.text = "-> $imcStatus"
                binding.txtNormal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
            }

            imc < 30 -> {
                imcStatus = "Acima"
                binding.txtImc.setTextColor(ContextCompat.getColor(this,
                    R.color.acima))
                binding.txtAcima.text = "-> $imcStatus"
                binding.txtAcima.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
            }

            else -> {
                imcStatus = "Muito Acima"
                binding.txtImc.setTextColor(ContextCompat.getColor(this,
                    R.color.muito_acima))
                binding.txtMuitoAcima.text = "-> $imcStatus"
                binding.txtMuitoAcima.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
            }
        }

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonCompartilhar.setOnClickListener {
            val mensagem = "Meu IMC hoje é: $imcValue ($imcStatus)"
            val intent = Intent(ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, mensagem)
            }

            val chooser = Intent.createChooser(intent, "Compartilhar via:")
            startActivity(chooser)
        }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

    }
}
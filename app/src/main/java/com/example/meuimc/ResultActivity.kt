package com.example.meuimc

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meuimc.databinding.ActivityResultBinding
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var status: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val imc = intent.getDoubleExtra("CHAVE_IMC", 0.0)
        binding = ActivityResultBinding.inflate(layoutInflater)

        val decimalFormat = DecimalFormat("##.##")
        val imcValue = decimalFormat.format(imc)
        binding.txtImc.text = imcValue

        when {
            imc < 17.0 -> {
                destacarLinha(imcStatus = "Muito Abaixo", textView = binding.txtMuitoAbaixo, cor = ContextCompat.getColor(this,
                    R.color.muito_abaixo))
            }

            imc < 18.5 -> {
                destacarLinha(imcStatus = "Abaixo", textView = binding.txtAbaixo, cor = ContextCompat.getColor(this,
                    R.color.abaixo))
            }

            imc < 25 -> {
                destacarLinha(imcStatus = "Normal", textView = binding.txtNormal, cor = ContextCompat.getColor(this,
                    R.color.normal))
            }

            imc < 30 -> {
                destacarLinha(imcStatus = "Acima", textView = binding.txtAcima, cor = ContextCompat.getColor(this,
                    R.color.acima))
            }

            else -> {
                destacarLinha(imcStatus = "Muito Acima", textView = binding.txtMuitoAcima, cor = ContextCompat.getColor(this,
                    R.color.muito_acima))
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
            val mensagem = "Meu IMC hoje é: $imcValue ($status)"

            val sendIntent = Intent(ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, mensagem)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Compartilhar via:")

            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            }
        }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

    }

    private fun destacarLinha(imcStatus: String, textView: TextView, cor: Int) {
        status = imcStatus
        binding.txtImc.setTextColor(cor)
        textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.outline_arrow_forward_ios_24,
            0, 0, 0)
        textView.setCompoundDrawableTintList(ColorStateList.valueOf(cor))
        textView.text = imcStatus
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
    }

}
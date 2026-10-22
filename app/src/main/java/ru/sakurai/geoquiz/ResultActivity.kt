package ru.sakurai.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    private lateinit var closeWindowResultButton: Button
    companion object {
        private const val EXTRA_PERCENT = "extra_percent"

        fun newIntent(context: Context, percent: Int): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(EXTRA_PERCENT, percent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val resultTextView: TextView = findViewById(R.id.result_percent)
        closeWindowResultButton = findViewById(R.id.close_result_window_button)

        val percent = intent.getIntExtra(EXTRA_PERCENT, 0)
        resultTextView.text = percent.toString() + "%"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        closeWindowResultButton.setOnClickListener {
            finish()
        }

    }
}
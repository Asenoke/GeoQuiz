package ru.sakurai.geoquiz


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cancelButton: Button
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cancelButton = findViewById(R.id.cancel_button)
        questionTextView = findViewById(R.id.question_text_view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            if (quizViewModel.isLastQuestion) {
                if (!quizViewModel.isCurrentAnswered) {
                    Toast.makeText(this, "Сначала ответьте на вопрос", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                startActivity(ResultActivity.newIntent(this, quizViewModel.percentCorrect))
            } else {
                quizViewModel.moveToNext()
                updateQuestion()
            }
        }

        cancelButton.setOnClickListener {
            quizViewModel.moveToCancel()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onResume() {
        super.onResume()
        quizViewModel.reset()
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val isCorrect = quizViewModel.checkAnswer(userAnswer)
        val messageResId = if (isCorrect) R.string.correct_toast else R.string.incorrect_toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
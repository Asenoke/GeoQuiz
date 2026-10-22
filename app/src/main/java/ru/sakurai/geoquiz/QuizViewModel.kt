package ru.sakurai.geoquiz

import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionBank: List<Question> = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex: Int = 0
    private var correctCount: Int = 0
    private var answeredCount: Int = 0
    private var isAnswered: Boolean = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val isLastQuestion: Boolean
        get() = currentIndex == questionBank.lastIndex
    val isCurrentAnswered: Boolean
        get() = isAnswered

    val percentCorrect: Int
        get() = if (questionBank.isEmpty()) 0
        else (correctCount * 100.0 / questionBank.size).roundToInt().coerceIn(0, 100)


    fun checkAnswer(userAnswer: Boolean): Boolean {
        val isCorrect = userAnswer == currentQuestionAnswer
        if (!isAnswered) {
            isAnswered = true
            answeredCount++
            if (isCorrect) correctCount++
        }
        return isCorrect
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        isAnswered = false
    }

    fun moveToCancel() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
        isAnswered = false
    }

    fun reset() {
        currentIndex = 0
        correctCount = 0
        answeredCount = 0
    }
}
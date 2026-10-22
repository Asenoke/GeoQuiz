package ru.sakurai.geoquiz

import androidx.annotation.StringRes

// Data Class Question (хранение вопросов и ответов на них)
data class Question(@StringRes val textResId: Int, val answer: Boolean)

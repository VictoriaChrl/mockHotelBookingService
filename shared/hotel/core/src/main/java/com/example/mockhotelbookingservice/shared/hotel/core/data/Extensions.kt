package com.example.mockhotelbookingservice.shared.hotel.core.data

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

fun generateOrdinalNumberString(n: Int): String? {

    val ordinal = when (n) {
        1 -> "Первый"
        2 -> "Второй"
        3 -> "Третий"
        4 -> "Четвертый"
        5 -> "Пятый"
        6 -> "Шестой"
        7 -> "Седьмой"
        8 -> "Восьмой"
        9 -> "Девятый"
        10 -> "Десятый"
        else -> null
    }

    return ordinal
}

fun formatWithSpaces(number: Long): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(number).replace(",", " ")
}

fun makeToast(view: View, text: String){
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}
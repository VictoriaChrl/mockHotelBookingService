package com.example.mockhotelbookingservice.shared.hotel.core.data

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

fun generateOrdinalNumberString(n: Int): String? {

    val ordinal = when (n) {
        0 -> "Первый"
        1 -> "Второй"
        2 -> "Третий"
        3 -> "Четвертый"
        4 -> "Пятый"
        5 -> "Шестой"
        6 -> "Седьмой"
        7 -> "Восьмой"
        8 -> "Девятый"
        9 -> "Десятый"
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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
package com.example.mockhotelbookingservice.shared.hotel.core.data

import java.text.DecimalFormat

fun generateOrdinalNumberString(n: Int): String {
    val ones = arrayOf("ый", "ый", "ой", "ий", "ый", "ый", "ой", "ой", "ый", "ый")
    val tens = arrayOf("", "десятый", "двадцатый", "тридцатый", "сороковой", "пятидесятый", "шестидесятый", "семидесятый", "восьмидесятый", "девяностый")

    val ordinal = when (n) {
        1 -> "Первый"
        2 -> "Второй"
        3 -> "Третий"
        4 -> "Четвертый"
        else -> "${n}${ones[n % 10]} ${tens[n / 10]}"
    }

    return ordinal
}

fun formatWithSpaces(number: Long): String {
    val formatter = DecimalFormat("# ###")
    return formatter.format(number)
}
package ru.iteco.fmhandoid.uitesting.testdata

import java.text.SimpleDateFormat
import java.util.*

class DataHelper {
    companion object {
        fun getCurrentDate(): String {
            return SimpleDateFormat("dd.MM.yyyy").format(Date())
        }

        fun getRandomInt(): Int {
            return (0..999).random()
        }
    }
}
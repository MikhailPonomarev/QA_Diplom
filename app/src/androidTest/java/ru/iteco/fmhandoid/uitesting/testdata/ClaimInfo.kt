package ru.iteco.fmhandoid.uitesting.testdata

import com.github.javafaker.Faker
import java.util.*

class ClaimInfo(val status: String) {
    private val faker = Faker(Locale("en"))

    val title = faker.book().title() + faker.number().digits(6)
    val executor = Constants.EXECUTOR
    val planDate = "01.01.1979"
    val time = "12:00"
    val description = "Claim description ${DataHelper.getRandomInt()}"
}
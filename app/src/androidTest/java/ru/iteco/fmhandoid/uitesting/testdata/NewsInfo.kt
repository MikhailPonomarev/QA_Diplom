package ru.iteco.fmhandoid.uitesting.testdata

import com.github.javafaker.Faker
import java.util.*

class NewsInfo(val publicationDate: String) {
    private val randomInt = DataHelper.getRandomInt()
    private val faker = Faker(Locale("en"))

    val newsTitle = faker.number().digits(6) + faker.company().name()
    val publicationTime = DataHelper.getCurrentTime()
    val description = "News Description $randomInt"
}
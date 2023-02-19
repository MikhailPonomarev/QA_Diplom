package ru.iteco.fmhandoid.uitesting.testdata

class NewsInfo(val publicationDate: String) {
    private val randomInt = DataHelper.getRandomInt()

    val newsTitle = "$randomInt New News"
    val publicationTime = DataHelper.getCurrentTime()
    val description = "News Description $randomInt"
//    val author = "Ivanov I.I."
}
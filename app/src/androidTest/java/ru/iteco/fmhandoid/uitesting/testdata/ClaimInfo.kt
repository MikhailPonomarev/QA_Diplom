package ru.iteco.fmhandoid.uitesting.testdata

class ClaimInfo(val status: String) {
    private val randomInt = DataHelper.getRandomInt()

    val title = "New claim $randomInt"
    val executor = Constants.EXECUTOR
    val planDate = "01.01.1979"
    val createDate = DataHelper.getCurrentDate()
    val time = "12:00"
    val description = "Claim description $randomInt"
}
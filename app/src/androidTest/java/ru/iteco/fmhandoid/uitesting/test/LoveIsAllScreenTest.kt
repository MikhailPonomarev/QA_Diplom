package ru.iteco.fmhandoid.uitesting.test

import org.junit.Test

class LoveIsAllScreenTest : BaseTest() {

    @Test
    fun loveIsAllScreenTest() {
        val loveIsAllScreen = appBar.openLoveIsAllScreen()
        loveIsAllScreen.assertIsLoveIsAllScreen()
        loveIsAllScreen.assertFirstQuoteContent()
    }
}
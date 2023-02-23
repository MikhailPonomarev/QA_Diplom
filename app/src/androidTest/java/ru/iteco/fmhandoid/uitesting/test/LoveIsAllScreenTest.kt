package ru.iteco.fmhandoid.uitesting.test

import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test

class LoveIsAllScreenTest : BaseTest() {

    @Test
    @DisplayName("Проверка элементов экрана Love is all")
    fun loveIsAllScreenTest() {
        val loveIsAllScreen = appBar.openLoveIsAllScreen()
        loveIsAllScreen.assertIsLoveIsAllScreen()
        loveIsAllScreen.assertFirstQuoteContent()
    }
}
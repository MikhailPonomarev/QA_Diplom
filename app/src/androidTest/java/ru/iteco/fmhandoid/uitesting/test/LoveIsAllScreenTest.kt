package ru.iteco.fmhandoid.uitesting.test

import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AllureAndroidJUnit4::class)
class LoveIsAllScreenTest : BaseTest() {

    @Test
    @DisplayName("Проверка элементов экрана Love is all")
    fun loveIsAllScreenTest() {
        val loveIsAllScreen = appBar.openLoveIsAllScreen()
        loveIsAllScreen.assertIsLoveIsAllScreen()
        loveIsAllScreen.assertFirstQuoteContent()
    }
}
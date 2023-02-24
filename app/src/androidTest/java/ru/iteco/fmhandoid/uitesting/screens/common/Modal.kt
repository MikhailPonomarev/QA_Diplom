package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertEquals

class Modal(private val device: UiDevice) : BaseScreen(device) {

    fun assertModalText(expectedText: String) {
        Allure.step("Проверить текст модального окна")

        val modalText = this.device
            .findObject(UiSelector().resourceId("android:id/message")).text
        assertEquals(expectedText, modalText)
    }

    fun clickOk() {
        Allure.step("Нажать кнопку ОК в модальном окне")

        this.device.findObject(UiSelector().resourceId("android:id/button1")).click()
    }
}
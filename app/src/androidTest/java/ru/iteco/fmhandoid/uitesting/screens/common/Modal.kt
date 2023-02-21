package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import org.junit.Assert.assertEquals
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen

class Modal(private val device: UiDevice) : BaseScreen(device) {

    @Step
    fun assertModalText(expectedText: String) {
        val modalText = this.device
            .findObject(UiSelector().resourceId("android:id/message")).text
        assertEquals(expectedText, modalText)
    }

    @Step
    fun clickOk() {
        this.device.findObject(UiSelector().resourceId("android:id/button1")).click()
    }
}
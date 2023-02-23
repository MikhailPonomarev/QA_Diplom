package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.*
import ru.iteco.fmhandoid.uitesting.testdata.Constants

open class BaseScreen(private val device: UiDevice) {
    protected val baseId = "${Constants.PACKAGE}:id/"

    fun find(by: BySelector): UiObject2 = device.findObject(by)

    fun findByResId(id: String): UiObject {
        return device.findObject(
            UiSelector().resourceId(
                "${baseId}${id}"
            )
        )
    }

    fun findByText(text: String): UiObject = device.findObject(UiSelector().text(text))
}
package ru.iteco.fmhandoid.uitesting.utils

import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertTrue

class CustomAssertions {
    companion object {
        fun assertViewIsVisible(uiObject: UiObject) {
            assertTrue(uiObject.exists())
        }

        fun assertEmptyFieldAlertIconIsVisible(fieldLayout: UiObject, alertIconId: String) {
            assertTrue(
                fieldLayout
                    .getChild(UiSelector().resourceId(alertIconId))
                    .exists()
            )
        }
    }
}
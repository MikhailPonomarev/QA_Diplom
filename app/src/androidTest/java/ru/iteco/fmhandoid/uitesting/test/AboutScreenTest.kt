package ru.iteco.fmhandoid.uitesting.test

import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AllureAndroidJUnit4::class)
class AboutScreenTest : BaseTest() {

    @Test
    @DisplayName("Тест элементов экрана About")
    fun aboutScreenTest() {
        val aboutScreen = appBar.openAboutScreen()
        aboutScreen.assertIsAboutScreen()
        aboutScreen.assertAppVersionText("1.0.0")
        aboutScreen.assertPrivacyPolicyContent()
        aboutScreen.assertThermsOfUseContent()

        aboutScreen.assertPrivacyPolicyLinkClickOpensBrowser()
        device.pressBack()
        aboutScreen.assertIsAboutScreen()

        aboutScreen.assertThermsOfUseLinkClickOpensBrowser()
        device.pressBack()
        aboutScreen.assertIsAboutScreen()

        aboutScreen
            .clickBackBtn()
            .assertIsMainScreen()
    }
}
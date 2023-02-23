package ru.iteco.fmhandoid.uitesting.screens.about

import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertEquals
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.screens.common.MainSection
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class AboutScreen(private val device: UiDevice) : BaseScreen(device)  {
    private val versionTitle = findByResId("about_version_title_text_view")
    private val privacyPolicyTitle = findByResId("about_privacy_policy_label_text_view")
    private val thermsOfUseTitle = findByResId("about_terms_of_use_label_text_view")
    private val privacyPolicyLink = findByResId("about_privacy_policy_value_text_view")
    private val thermsOfUseLink = findByResId("about_terms_of_use_value_text_view")
    private val chromePackage = "com.android.chrome"

    fun clickBackBtn(): MainSection {
        Allure.step("Нажать кнопку \"Назад\"")

        findByResId("about_back_image_button").click()
        return MainSection(this.device)
    }

    fun assertIsAboutScreen() {
        Allure.step("Должен быть открыт раздел Love is all")

        assertViewIsVisible(findByResId("trademark_image_view"))
        assertViewIsVisible(versionTitle)
        assertViewIsVisible(privacyPolicyTitle)
        assertViewIsVisible(thermsOfUseTitle)
        assertViewIsVisible(findByResId("about_company_info_label_text_view"))
    }

    fun assertAppVersionText(expectedVersion: String) {
        Allure.step("Проверить версию приложени")

        assertEquals(
            "Version:",
            versionTitle.text
        )

        assertEquals(
            expectedVersion,
            findByResId("about_version_value_text_view").text
        )
    }

    fun assertPrivacyPolicyContent() {
        Allure.step("Проверить заголовок и ссылку Privacy Policy")

        assertEquals(
            "Privacy Policy:",
            privacyPolicyTitle.text
        )

        assertEquals(
            "https://vhospice.org/#/privacy-policy/",
            privacyPolicyLink.text
        )
    }

    fun assertThermsOfUseContent() {
        Allure.step("Проверить заголовок и ссылку Terms of use")

        assertEquals(
            "Terms of use:",
            thermsOfUseTitle.text
        )

        assertEquals(
            "https://vhospice.org/#/terms-of-use",
            thermsOfUseLink.text
        )
    }

    fun assertPrivacyPolicyLinkClickOpensBrowser() {
        Allure.step("Нажатие на ссылку Privacy Policy должно открыть браузер")

        privacyPolicyLink.click()
        val currentPackage: String = this.device.currentPackageName
        assertEquals(
            chromePackage,
            currentPackage
        )
    }

    fun assertThermsOfUseLinkClickOpensBrowser() {
        Allure.step("Нажатие на ссылку Terms of use должно открыть браузер")

        thermsOfUseLink.click()
        val currentPackage: String = this.device.currentPackageName
        assertEquals(
            chromePackage,
            currentPackage
        )
    }
}
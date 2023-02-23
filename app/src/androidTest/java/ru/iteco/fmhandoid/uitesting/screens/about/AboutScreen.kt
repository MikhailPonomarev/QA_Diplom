package ru.iteco.fmhandoid.uitesting.screens.about

import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Step
import org.junit.Assert.assertEquals
import org.junit.Test
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

    @Step
    fun clickBackBtn(): MainSection {
        findByResId("about_back_image_button").click()
        return MainSection(this.device)
    }

    @Step
    fun assertIsAboutScreen() {
        assertViewIsVisible(findByResId("trademark_image_view"))
        assertViewIsVisible(versionTitle)
        assertViewIsVisible(privacyPolicyTitle)
        assertViewIsVisible(thermsOfUseTitle)
        assertViewIsVisible(findByResId("about_company_info_label_text_view"))
    }

    @Step
    fun assertAppVersionText(expectedVersion: String) {
        assertEquals(
            "Version:",
            versionTitle.text
        )

        assertEquals(
            expectedVersion,
            findByResId("about_version_value_text_view").text
        )
    }

    @Step
    fun assertPrivacyPolicyContent() {
        assertEquals(
            "Privacy Policy:",
            privacyPolicyTitle.text
        )

        assertEquals(
            "https://vhospice.org/#/privacy-policy/",
            privacyPolicyLink.text
        )
    }

    @Step
    fun assertThermsOfUseContent() {
        assertEquals(
            "Terms of use:",
            thermsOfUseTitle.text
        )

        assertEquals(
            "https://vhospice.org/#/terms-of-use",
            thermsOfUseLink.text
        )
    }

    @Test
    fun assertPrivacyPolicyLinkClickOpensBrowser() {
        privacyPolicyLink.click()
        val currentPackage: String = this.device.currentPackageName
        assertEquals(
            chromePackage,
            currentPackage
        )
    }

    @Test
    fun assertThermsOfUseLinkClickOpensBrowser() {
        thermsOfUseLink.click()
        val currentPackage: String = this.device.currentPackageName
        assertEquals(
            chromePackage,
            currentPackage
        )
    }
}
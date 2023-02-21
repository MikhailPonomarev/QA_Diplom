package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.screens.common.Modal
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions

class NewsCreateEditScreen(private val device: UiDevice) : BaseScreen(device) {
    private val categoryInput = findByResId("news_item_category_text_auto_complete_text_view")
    private val titleInput = findByResId("news_item_title_text_input_edit_text")
    private val publicationDateInput = findByResId("news_item_publish_date_text_input_edit_text")
    private val publicationTimeInput = findByResId("news_item_publish_time_text_input_edit_text")
    private val descriptionInput = findByResId("news_item_description_text_input_edit_text")
    private val activeSwitcher = findByResId("switcher")
    private val saveBtn = findByResId("save_button")
    private val cancelBtn = findByResId("cancel_button")

    @Step
    fun assertIsCreatingNewsScreen() {
        CustomAssertions.assertViewIsVisible(findByText("Creating"))
        CustomAssertions.assertViewIsVisible(findByText("News"))
    }

    @Step
    fun assertIsEditingNewsScreen() {
        CustomAssertions.assertViewIsVisible(findByText("Editing"))
        CustomAssertions.assertViewIsVisible(findByText("News"))
    }

    @Step
    fun createNews(info: NewsInfo): NewsSection {
        categoryInput.click()
        val rect = categoryInput.bounds
        this.device.waitForWindowUpdate(Constants.PACKAGE, 2000)
        this.device.click(rect.left + 64, rect.bottom + 72)

        val actualCategory = categoryInput.text
        assertEquals("Объявление", actualCategory)

        fillAndSaveNews(info)
        return NewsSection(this.device)
    }

    @Step
    fun editNews(info: NewsInfo): NewsSection {
        fillAndSaveNews(info)
        return NewsSection(this.device)
    }

    private fun fillAndSaveNews(info: NewsInfo) {
        titleInput.text = info.newsTitle
        publicationDateInput.text = info.publicationDate
        publicationTimeInput.text = info.publicationTime
        descriptionInput.text = info.description
        saveBtn.click()
    }

    @Step
    fun clickActiveSwitcher() {
        activeSwitcher.click()
    }

    @Step
    fun clickSaveBtn() {
        saveBtn.click()
    }

    @Step
    fun clickCancelBtn(): Modal {
        cancelBtn.click()
        return Modal(this.device)
    }

    @Step
    fun assertEmptyInputAlertsAreVisible() {
        val categoryInputAlertImgId = "${baseId}text_input_start_icon"
        val inputAlertImgId = "${baseId}text_input_end_icon"

        CustomAssertions.assertEmptyFieldAlertIconIsVisible(
            findByResId("news_item_category_text_input_layout"),
            categoryInputAlertImgId
        )

        CustomAssertions.assertEmptyFieldAlertIconIsVisible(
            findByResId("news_item_title_text_input_layout"),
            inputAlertImgId
        )

        CustomAssertions.assertEmptyFieldAlertIconIsVisible(
            findByResId("news_item_create_date_text_input_layout"),
            inputAlertImgId
        )

        CustomAssertions.assertEmptyFieldAlertIconIsVisible(
            findByResId("news_item_publish_time_text_input_layout"),
            inputAlertImgId
        )

        CustomAssertions.assertEmptyFieldAlertIconIsVisible(
            findByResId("news_item_description_text_input_layout"),
            inputAlertImgId
        )
    }
}
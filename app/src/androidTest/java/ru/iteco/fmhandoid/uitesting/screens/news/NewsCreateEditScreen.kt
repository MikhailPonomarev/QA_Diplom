package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertEquals
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.screens.common.Modal
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class NewsCreateEditScreen(private val device: UiDevice) : BaseScreen(device) {
    private val creatingTitle = findByText("Creating")
    private val editingTitle = findByText("Editing")
    private val subTitle = findByText("News")
    private val categoryInput = findByResId("news_item_category_text_auto_complete_text_view")
    private val titleInput = findByResId("news_item_title_text_input_edit_text")
    private val publicationDateInput = findByResId("news_item_publish_date_text_input_edit_text")
    private val publicationTimeInput = findByResId("news_item_publish_time_text_input_edit_text")
    private val descriptionInput = findByResId("news_item_description_text_input_edit_text")
    private val activeSwitcher = findByResId("switcher")
    private val saveBtn = findByResId("save_button")
    private val cancelBtn = findByResId("cancel_button")

    fun getCreatingTitle(): UiObject = creatingTitle

    fun getEditingTitle(): UiObject = editingTitle

    fun assertIsCreatingNewsScreen() {
        Allure.step("Должен быть открыт экран Creating News")

        assertViewIsVisible(creatingTitle)
        assertViewIsVisible(subTitle)
    }

    fun assertIsEditingNewsScreen() {
        Allure.step("Должен быть открыт экран Editing News")

        assertViewIsVisible(editingTitle)
        assertViewIsVisible(subTitle)
    }

    fun createNews(info: NewsInfo): NewsSection {
        Allure.step("Создать новость")

        categoryInput.click()
        val rect = categoryInput.bounds
        this.device.waitForWindowUpdate(Constants.PACKAGE, 2000)
        this.device.click(rect.left + 64, rect.bottom + 72)

        val actualCategory = categoryInput.text
        assertEquals("Объявление", actualCategory)

        fillAndSaveNews(info)
        return NewsSection(this.device)
    }

    fun editNews(info: NewsInfo): NewsSection {
        Allure.step("Отредактировать новость")

        fillAndSaveNews(info)
        return NewsSection(this.device)
    }

    private fun fillAndSaveNews(info: NewsInfo) {
        Allure.step("Заполнить новость и нажать кнопку Save")

        titleInput.text = info.newsTitle
        publicationDateInput.text = info.publicationDate
        publicationTimeInput.text = info.publicationTime
        descriptionInput.text = info.description
        saveBtn.click()
    }

    fun clickActiveSwitcher() {
        Allure.step("Нажать свитчер Active/Not active")

        activeSwitcher.click()
    }

    fun clickSaveBtn() {
        Allure.step("Нажать кнопку Save")

        saveBtn.click()
    }

    fun clickCancelBtn(): Modal {
        Allure.step("Нажать кнопку Cancel")

        cancelBtn.click()
        return Modal(this.device)
    }

    fun assertEmptyInputAlertsAreVisible() {
        Allure.step("Должны появиться алерты для незаполненных полей")

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
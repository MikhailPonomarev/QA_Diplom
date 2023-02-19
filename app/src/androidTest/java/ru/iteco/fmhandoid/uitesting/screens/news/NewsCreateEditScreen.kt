package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import org.junit.Assert.assertEquals
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo

class NewsCreateEditScreen(private val device: UiDevice) : BaseScreen(device) {
    private val categoryInput = findByResId("news_item_category_text_auto_complete_text_view")
    private val title = findByResId("news_item_title_text_input_edit_text")
    private val publicationDate = findByResId("news_item_publish_date_text_input_edit_text")
    private val publicationTime = findByResId("news_item_publish_time_text_input_edit_text")
    private val description = findByResId("news_item_description_text_input_edit_text")
    private val activeSwitcher = findByResId("switcher")
    private val saveBtn = findByResId("save_button")
    private val cancelBtn = findByResId("cancel_button")

    @Step
    fun assertIsCreatingNewsScreen() {
        findByText("Creating").exists()
        findByText("News").click()
    }

    @Step
    fun assertIsEditingNewsScreen() {
        findByText("Editing").exists()
        findByText("News").click()
    }

    @Step
    fun createNews(info: NewsInfo): NewsSection {
        categoryInput.click()
        val rect = categoryInput.bounds
        this.device.waitForWindowUpdate(Constants.PACKAGE, 2000)
        this.device.click(rect.left + 64, rect.bottom + 72)

        val actualCategory = categoryInput.text
        assertEquals("Объявление", actualCategory)

        title.text = info.newsTitle
        publicationDate.text = info.publicationDate
        publicationTime.text = info.publicationTime
        description.text = info.description
        saveBtn.click()
        return NewsSection(this.device)
    }
}
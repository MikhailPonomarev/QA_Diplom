package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.testdata.DataHelper
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo

class NewsSection(private val device: UiDevice) : BaseScreen(device) {
    private val controlPanelBtn = findByResId("edit_news_material_button")
    private val addNewsBtn = findByResId("add_news_image_view")

    @Step
    fun assertIsNewsScreen() {
        findByText("News").exists()
    }

    @Step
    fun assertIsControlPanel() {
        findByText("Control panel").exists()
    }

    @Step
    fun clickControlPanelBtn(): NewsSection {
        controlPanelBtn.click()
        findByText("Control panel").exists()
        return NewsSection(this.device)
    }

    @Step
    fun clickAddNewsBtn(): NewsCreateEditScreen {
        addNewsBtn.click()
        return NewsCreateEditScreen(this.device)
    }

    @Step
    fun assertCreatedNewsInfoInControlPanel(info: NewsInfo) {
        findByText(info.newsTitle).click()
        findByText(info.publicationDate).exists()
        findByText(DataHelper.getCurrentDate()).exists()
        //TODO: assert isActive

        findByText(info.description).exists()
    }

    @Step
    fun assertCreatedNewsInNewsSection(info: NewsInfo) {
        device.findObject(UiSelector().resourceId("${baseId}view_news_item_image_view")
            .fromParent(UiSelector().fromParent(UiSelector().text(info.newsTitle))))
            .click()
        findByText(info.newsTitle).exists()
        findByText(info.publicationDate).exists()
        findByText(info.description).exists()
    }

    @Step
    fun filterNewsByCreateDate(date: String) {
        findByResId("filter_news_material_button").click()
        findByResId("news_item_publish_date_start_text_input_edit_text").text = date
        findByResId("news_item_publish_date_end_text_input_edit_text").text = date
        findByResId("filter_button").click()
    }
}
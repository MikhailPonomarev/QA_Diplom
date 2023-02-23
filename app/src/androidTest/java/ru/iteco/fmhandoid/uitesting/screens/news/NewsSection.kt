package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.screens.common.Modal
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class NewsSection(private val device: UiDevice) : BaseScreen(device) {
    private val controlPanelBtn = findByResId("edit_news_material_button")
    private val addNewsBtn = findByResId("add_news_image_view")
    private val newsRecyclerViewId = "${baseId}news_list_recycler_view"
    private val newsCardViewId = "${baseId}news_item_material_card_view"

    @Step
    fun assertIsNewsSection() {
        assertViewIsVisible(findByText("News"))
    }

    @Step
    fun assertIsControlPanel() {
        assertViewIsVisible(findByText("Control panel"))
    }

    @Step
    fun clickControlPanelBtn(): NewsSection {
        controlPanelBtn.click()
        assertViewIsVisible(findByText("Control panel"))
        return NewsSection(this.device)
    }

    @Step
    fun clickAddNewsBtn(): NewsCreateEditScreen {
        addNewsBtn.click()
        return NewsCreateEditScreen(this.device)
    }

    @Step
    fun assertCreatedNewsInfoInControlPanel(info: NewsInfo) {
        val parentNews =
            UiCollection(UiSelector().resourceId(newsRecyclerViewId))
                .getChildByInstance(
                    UiSelector().resourceId("${baseId}news_item_material_card_view"),
                    0
                )
        parentNews.getChild(findByText(info.newsTitle).selector).click()

        assertViewIsVisible(parentNews.getChild(findByText(info.publicationDate).selector))
        assertViewIsVisible(parentNews.getChild(findByText(info.publicationDate).selector))
        assertViewIsVisible(parentNews.getChild(findByText(info.description).selector))
    }

    @Step
    fun openCreatedNewsForEdit(newsTitle: String): NewsCreateEditScreen {
        val exactEditBtn =
            UiCollection(UiSelector().resourceId(newsRecyclerViewId))
                .getChildByInstance(
                    UiSelector().resourceId(newsCardViewId),
                    0
                ).getChild(UiSelector().resourceId("${baseId}edit_news_item_image_view"))
        exactEditBtn.click()
        return NewsCreateEditScreen(this.device)
    }

    @Step
    fun assertCreatedNewsInNewsSection(info: NewsInfo) {
        device.findObject(
            UiSelector().resourceId("${baseId}view_news_item_image_view")
                .fromParent(UiSelector().fromParent(UiSelector().text(info.newsTitle)))
        ).click()
        assertViewIsVisible(findByText(info.publicationDate))
        assertViewIsVisible(findByText(info.description))
    }

    @Step
    fun clickDeleteNews(): Modal {
        val exactDeleteBtn =
            UiCollection(UiSelector().resourceId(newsRecyclerViewId))
                .getChildByInstance(
                    UiSelector().resourceId(newsCardViewId),
                    0
                ).getChild(UiSelector().resourceId("${baseId}delete_news_item_image_view"))
        exactDeleteBtn.click()
        return Modal(this.device)
    }

    @Step
    fun assertDeletedNewsNotExist(newsTitle: String) {
        assertFalse(
            findByText(newsTitle).exists()
        )
    }

    @Step
    fun assertDeactivatedNewsNotVisibleInNewsSection(newsTitle: String) {
        assertFalse(
            findByText(newsTitle).exists()
        )
    }

    @Step
    fun scrollToExactNews(newsTitle: String, publicationDate: String) {
        findByResId("filter_news_material_button").click()
        findByResId("news_item_publish_date_start_text_input_edit_text").text = publicationDate
        findByResId("news_item_publish_date_end_text_input_edit_text").text = publicationDate
        findByResId("filter_news_inactive_material_check_box").click()
        findByResId("filter_button").click()

        UiScrollable(UiSelector().resourceId(newsRecyclerViewId))
            .scrollIntoView(UiSelector().text(newsTitle))
    }
}
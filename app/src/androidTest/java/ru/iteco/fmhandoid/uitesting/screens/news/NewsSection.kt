package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertFalse
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.screens.common.Modal
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class NewsSection(private val device: UiDevice) : BaseScreen(device) {
    private val controlPanelBtn = findByResId("edit_news_material_button")
    private val addNewsBtn = findByResId("add_news_image_view")
    private val newsRecyclerViewId = "${baseId}news_list_recycler_view"
    private val newsCardViewId = "${baseId}news_item_material_card_view"

    fun assertIsNewsSection() {
        Allure.step("Должен быть открыт раздел News")

        assertViewIsVisible(findByText("News"))
    }

    fun assertIsControlPanel() {
        Allure.step("Должна быть открыта Control panel раздела News")

        assertViewIsVisible(findByText("Control panel"))
    }

    fun clickControlPanelBtn(): NewsSection {
        Allure.step("Нажать кнопку Control panel")

        controlPanelBtn.click()
        assertViewIsVisible(findByText("Control panel"))
        return NewsSection(this.device)
    }

    fun clickAddNewsBtn(): NewsCreateEditScreen {
        Allure.step("Нажать кнопку \"Добавить новость\"")

        addNewsBtn.click()
        return NewsCreateEditScreen(this.device)
    }

    fun assertCreatedNewsInfoInControlPanel(info: NewsInfo) {
        Allure.step("Созданная новость должна отображаться в Control panel")

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

    fun openCreatedNewsForEdit(): NewsCreateEditScreen {
        Allure.step("Открыть новость для редактирования")

        val exactEditBtn =
            UiCollection(UiSelector().resourceId(newsRecyclerViewId))
                .getChildByInstance(
                    UiSelector().resourceId(newsCardViewId),
                    0
                ).getChild(UiSelector().resourceId("${baseId}edit_news_item_image_view"))
        exactEditBtn.click()
        return NewsCreateEditScreen(this.device)
    }

    fun assertCreatedNewsInNewsSection(info: NewsInfo) {
        Allure.step("Созданная новость должна отображаться в разделе News")

        device.findObject(
            UiSelector().resourceId("${baseId}view_news_item_image_view")
                .fromParent(UiSelector().fromParent(UiSelector().text(info.newsTitle)))
        ).click()
        assertViewIsVisible(findByText(info.publicationDate))
        assertViewIsVisible(findByText(info.description))
    }

    fun clickDeleteNews(): Modal {
        Allure.step("Удалить новость")

        val exactDeleteBtn =
            UiCollection(UiSelector().resourceId(newsRecyclerViewId))
                .getChildByInstance(
                    UiSelector().resourceId(newsCardViewId),
                    0
                ).getChild(UiSelector().resourceId("${baseId}delete_news_item_image_view"))
        exactDeleteBtn.click()
        return Modal(this.device)
    }

    fun assertDeletedNewsNotExist(newsTitle: String) {
        Allure.step("Удаленная новость не должна отображаться")

        assertFalse(
            findByText(newsTitle).exists()
        )
    }

    fun assertDeactivatedNewsNotVisibleInNewsSection(newsTitle: String) {
        Allure.step("Неактивная новость не должна отображаться")

        assertFalse(
            findByText(newsTitle).exists()
        )
    }

    fun scrollToExactNews(newsTitle: String, publicationDate: String) {
        Allure.step("Проскроллить к созданной новости")

        findByResId("filter_news_material_button").click()
        findByResId("news_item_publish_date_start_text_input_edit_text").text = publicationDate
        findByResId("news_item_publish_date_end_text_input_edit_text").text = publicationDate
        findByResId("filter_news_inactive_material_check_box").click()
        findByResId("filter_button").click()

        UiScrollable(UiSelector().resourceId(newsRecyclerViewId))
            .scrollIntoView(UiSelector().text(newsTitle))
    }
}
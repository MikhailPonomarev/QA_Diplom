package ru.iteco.fmhandoid.uitesting.test

import org.junit.Test
import ru.iteco.fmhandoid.uitesting.screens.news.NewsSection
import ru.iteco.fmhandoid.uitesting.testdata.DataHelper
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo

class NewsTest : BaseTest() {

    //TODO: refactoring - replace all exists() methods with custom assertion

    @Test
    fun createNewsOnCurrentDate() {
        val newsSectionBefore = appBar.openNewsSection()
        newsSectionBefore.assertIsNewsSection()

        val newsControlPanelBefore = newsSectionBefore.clickControlPanelBtn()
        newsControlPanelBefore.assertIsControlPanel()
        val newsCreateScreen = newsControlPanelBefore.clickAddNewsBtn()
        newsCreateScreen.assertIsCreatingNewsScreen()

        val newsInfo = NewsInfo(DataHelper.getCurrentDate())
        val newsControlPanelAfter = newsCreateScreen.createNews(newsInfo)
        newsControlPanelAfter.assertIsControlPanel()

        device.pressBack()
        val newsSectionAfter = NewsSection(device)
        newsSectionAfter.assertIsNewsSection()
        newsSectionAfter.assertCreatedNewsInNewsSection(newsInfo)

        val mainSection = appBar.openMainSection()
        mainSection.assertIsMainScreen()
        mainSection.assertCreatedNewsInMainSection(newsInfo)

        mainSection
            .clickAllNewsBtn()
            .assertIsNewsSection()
    }

    @Test
    fun editCreatedNews() {
        val newsControlPanelBeforeCreate = appBar
            .openNewsSection()
            .clickControlPanelBtn()

        val newsCreateScreen = newsControlPanelBeforeCreate.clickAddNewsBtn()
        newsCreateScreen.assertIsCreatingNewsScreen()

        val newsInfo = NewsInfo("31.12.2023")
        val newsControlPanelAfterCreate = newsCreateScreen.createNews(newsInfo)
        newsControlPanelAfterCreate.assertCreatedNewsInfoInControlPanel(newsInfo)

        val newsEditScreen = newsControlPanelAfterCreate.openCreatedNewsForEdit(
            newsInfo.newsTitle
        )
        newsEditScreen.assertIsEditingNewsScreen()
        val updNewsInfo = NewsInfo(DataHelper.getCurrentDate())
        newsEditScreen.editNews(updNewsInfo)
        NewsSection(device).assertIsControlPanel()

        device.pressBack()
        val newsSection = NewsSection(device)
        newsSection.assertIsNewsSection()
        newsSection.assertCreatedNewsInNewsSection(updNewsInfo)

        val mainSection = appBar.openMainSection()
        mainSection.assertCreatedNewsInMainSection(updNewsInfo)
    }

    @Test
    fun deleteNews() {
        val newsControlPanelBefore = appBar
            .openNewsSection()
            .clickControlPanelBtn()

        val newsCreateScreen = newsControlPanelBefore.clickAddNewsBtn()
        newsCreateScreen.assertIsCreatingNewsScreen()

        val newsInfo = NewsInfo("31.12.2023")
        val newsControlPanelAfter = newsCreateScreen.createNews(newsInfo)
        newsControlPanelAfter.assertCreatedNewsInfoInControlPanel(newsInfo)

        val modal = newsControlPanelAfter.clickDeleteNews(newsInfo.newsTitle)
        modal.assertModalText(
            "Are you sure you want to permanently delete the document? " +
                    "These changes cannot be reversed in the future."
        )
        modal.clickOk()
        newsControlPanelAfter.assertDeletedNewsNotExist(newsInfo.newsTitle)
    }

    @Test
    fun deactivateNews() {
        val newsInfo = NewsInfo(DataHelper.getCurrentDate())
        appBar
            .openNewsSection()
            .clickControlPanelBtn()
            .clickAddNewsBtn()
            .createNews(newsInfo)

        device.pressBack()
        val newsSectionAfterCreate = NewsSection(device)
        newsSectionAfterCreate.assertCreatedNewsInNewsSection(newsInfo)
        val mainSectionAfterCreate = appBar.openMainSection()
        mainSectionAfterCreate.assertCreatedNewsInMainSection(newsInfo)

        val newsControlPanel = appBar
            .openNewsSection()
            .clickControlPanelBtn()
        newsControlPanel.scrollToExactNews(
            newsInfo.newsTitle,
            newsInfo.publicationDate
        )
        val newsEditScreen = newsControlPanel.openCreatedNewsForEdit(newsInfo.newsTitle)
        newsEditScreen.assertIsEditingNewsScreen()
        newsEditScreen.clickActiveSwitcher()
        newsEditScreen.clickSaveBtn()

        device.pressBack()
        val newsSectionAfterDeactivate = NewsSection(device)
        newsSectionAfterDeactivate.assertDeactivatedNewsNotVisibleInNewsSection(
            newsInfo.newsTitle
        )
        val mainSectionAfterDeactivate = appBar.openMainSection()
        mainSectionAfterDeactivate.assertDeactivatedNewsNotVisibleInMainSection(
            newsInfo.newsTitle
        )
    }

    @Test
    fun createNewsWithEmptyFields() {
        val newsCreateScreen = appBar
            .openNewsSection()
            .clickControlPanelBtn()
            .clickAddNewsBtn()

        newsCreateScreen.clickSaveBtn()
        newsCreateScreen.assertEmptyInputAlertsAreVisible()
    }

    @Test
    fun createNewsOnFutureDate() {
        val newsCreateScreen = appBar
            .openNewsSection()
            .clickControlPanelBtn()
            .clickAddNewsBtn()

        val cancelModal = newsCreateScreen.clickCancelBtn()
        cancelModal.assertModalText(
            "The changes won't be saved, do you really want to log out?"
        )
        cancelModal.clickOk()

        NewsSection(device).assertIsControlPanel()
    }
}
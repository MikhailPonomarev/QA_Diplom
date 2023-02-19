package ru.iteco.fmhandoid.uitesting.test

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.iteco.fmhandoid.uitesting.screens.common.AppBar
import ru.iteco.fmhandoid.uitesting.screens.common.AuthScreen
import ru.iteco.fmhandoid.uitesting.screens.news.NewsSection
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.testdata.DataHelper
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo

class NewsTest: BaseTest() {

    @Before
    fun beforeEach() {
        initUiDeviceAndAppBar()
        waitForPackage()
        AuthScreen(device).signIn(Constants.VALID_LOGIN, Constants.VALID_PASS)
        appBar = AppBar(device)
    }

    @After
    fun afterEach() {
        AppBar(device).signOut()
    }

    @Test
    fun createNewsOnCurrentDate() {
        val newsSectionBefore = appBar.openNewsSection()
        newsSectionBefore.assertIsNewsScreen()

        val newsControlPanelBefore = newsSectionBefore.clickControlPanelBtn()
        newsControlPanelBefore.assertIsControlPanel()
        val newsCreateScreen = newsControlPanelBefore.clickAddNewsBtn()
        newsCreateScreen.assertIsCreatingNewsScreen()

        val newsInfo = NewsInfo(DataHelper.getCurrentDate())
        val newsControlPanelAfter = newsCreateScreen.createNews(newsInfo)
        newsControlPanelAfter.assertIsControlPanel()

        device.pressBack()
        val newsSectionAfter = NewsSection(device)
        newsSectionAfter.assertIsNewsScreen()
        newsSectionAfter.assertCreatedNewsInNewsSection(newsInfo)

        val mainSection = appBar.openMainSection()
        mainSection.assertIsMainScreen()
        mainSection.assertCreatedNewsInMainSection(newsInfo)
    }
}
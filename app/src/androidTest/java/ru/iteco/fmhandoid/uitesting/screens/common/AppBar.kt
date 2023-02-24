package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Allure
import ru.iteco.fmhandoid.uitesting.screens.about.AboutScreen
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimsSection
import ru.iteco.fmhandoid.uitesting.screens.loveisall.LoveIsAllScreen
import ru.iteco.fmhandoid.uitesting.screens.news.NewsSection

open class AppBar(private val device: UiDevice) : BaseScreen(device) {
    private val appMenuBtn = findByResId("main_menu_image_button")
    private val loveIsAllScreenBtn = findByResId("our_mission_image_button")
    private val accountMenuBtn = findByResId("authorization_image_button")
    private val logoutOption = findByText("Log out")

    fun openMainSection(): MainSection {
        Allure.step("Открыть раздел Main")

        appMenuBtn.click()
        findByText("Main").click()
        return MainSection(this.device)
    }

    fun openClaimsSection(): ClaimsSection {
        Allure.step("Открыть раздел Claims")

        appMenuBtn.click()
        findByText("Claims").click()
        return ClaimsSection(this.device)
    }

    fun openNewsSection(): NewsSection {
        Allure.step("Открыть раздел News")

        appMenuBtn.click()
        findByText("News").click()
        return NewsSection(this.device)
    }

    fun openAboutScreen(): AboutScreen {
        Allure.step("Открыть экран About")

        appMenuBtn.click()
        findByText("About").click()
        return AboutScreen(this.device)
    }

    fun openLoveIsAllScreen(): LoveIsAllScreen {
        Allure.step("Открыть экран Love is all")

        loveIsAllScreenBtn.click()
        return LoveIsAllScreen(this.device)
    }

    fun signOut(): AuthScreen {
        Allure.step("Выйти из аккаунта в приложении")

        accountMenuBtn.click()
        logoutOption.click()
        return AuthScreen(this.device)
    }
}

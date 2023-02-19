package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Step

open class AppBar(private val device: UiDevice) : BaseScreen(device) {
    private val appMenuBtn = findByResId("main_menu_image_button")
    private val loveIsAllScreenBtn = findByResId("our_mission_image_button")
    private val accountMenuBtn = findByResId("authorization_image_button")
    private val logoutOption = findByText("Log out")

    @Step
    fun openMainSection(): MainSection {
        appMenuBtn.click()
        findByText("Main").click()
        return MainSection(this.device)
    }

    @Step
    fun openClaimsSection(): ClaimsSection {
        appMenuBtn.click()
        findByText("Claims").click()
        return ClaimsSection(this.device)
    }

    @Step
    fun openNewsSection(): NewsSection {
        appMenuBtn.click()
        findByText("News").click()
        return NewsSection(this.device)
    }

    @Step
    fun openAboutSection(): AboutScreen {
        appMenuBtn.click()
        findByText("About").click()
        return AboutScreen(this.device)
    }

    @Step
    fun openLoveIsAllScreen(): LoveIsAllScreen {
        loveIsAllScreenBtn.click()
        return LoveIsAllScreen(this.device)
    }

    @Step
    fun signOut(): AuthScreen {
        accountMenuBtn.click()
        logoutOption.click()
        return AuthScreen(this.device)
    }
}

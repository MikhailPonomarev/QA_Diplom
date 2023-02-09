package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.*
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.Constants

abstract class BaseScreen(private val device: UiDevice) {
    private val baseId = "${Constants.PACKAGE}:id/"

    fun find(by: BySelector): UiObject2 = device.findObject(by)

    fun findByResId(id: String): UiObject {
        return device.findObject(
            UiSelector().resourceId(
                "${baseId}${id}"
            )
        )
    }

    fun findByText(text: String): UiObject = device.findObject(UiSelector().text(text))

    //AppBar //TODO: ?nested class?
    private val appMenuBtn = findByResId("main_menu_image_button")
    private val appLogo = findByResId("trademark_image_view")
    private val loveIsAllScreenBtn = findByResId("our_mission_image_button")
    private val accountMenuBtn = findByResId("authorization_image_button")
    private val logoutOption = findByText("Log out")

    //опции дроп-листа меню разделов
    private val mainSection = findByText("Main")
    private val claimsSection = findByText("Claims")
    private val newsSection = findByText("News")
    private val aboutSection = findByText("About")

    @Step
    fun appBarElementsShouldBeVisible() {
        appMenuBtn.exists()
        appLogo.exists()
        loveIsAllScreenBtn.exists()
        accountMenuBtn.exists()
    }

    @Step
    fun openMainSection(): MainScreen {
        appMenuBtn.click()
        mainSection.click()
        return MainScreen(this.device)
    }

    fun openClaimsSection(): ClaimsSection {
        appMenuBtn.click()
        claimsSection.click()
        return ClaimsSection(this.device)
    }

    fun openNewsSection(): NewsSection {
        appMenuBtn.click()
        newsSection.click()
        return NewsSection(this.device)
    }

    fun openAboutSection(): AboutScreen {
        appMenuBtn.click()
        aboutSection.click()
        return AboutScreen(this.device)
    }

    fun openLoveIsAllScreen(): LoveIsAllScreen {
        loveIsAllScreenBtn.click()
        return LoveIsAllScreen(this.device)
    }

    fun signOut(): AuthScreen {
        accountMenuBtn.click()
        logoutOption.click()
        return AuthScreen(this.device)
    }
}
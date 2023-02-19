package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.*
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.testdata.Constants

open class BaseScreen(private val device: UiDevice) {
    protected val baseId = "${Constants.PACKAGE}:id/"

    fun find(by: BySelector): UiObject2 = device.findObject(by)

    fun findByResId(id: String): UiObject {
        return device.findObject(
            UiSelector().resourceId(
                "${baseId}${id}"
            )
        )
    }

    fun findByText(text: String): UiObject = device.findObject(UiSelector().text(text))

    //AppBar //TODO: вынести в класс-companion obj
//    private val appMenuBtn = findByResId("main_menu_image_button")
//    private val appLogo = findByResId("trademark_image_view")
//    private val loveIsAllScreenBtn = findByResId("our_mission_image_button")
//    private val accountMenuBtn = findByResId("authorization_image_button")
//    private val logoutOption = findByText("Log out")
//
//    @Step
//    fun openMainSection(): MainSection {
//        appMenuBtn.click()
//        findByText("Main").click()
//        return MainSection(this.device)
//    }
//
//    fun openClaimsSection(): ClaimsSection {
//        appMenuBtn.click()
//        findByText("Claims").click()
//        return ClaimsSection(this.device)
//    }
//
//    fun openNewsSection(): NewsSection {
//        appMenuBtn.click()
//        findByText("News").click()
//        return NewsSection(this.device)
//    }
//
//    fun openAboutSection(): AboutScreen {
//        appMenuBtn.click()
//        findByText("About").click()
//        return AboutScreen(this.device)
//    }
//
//    fun openLoveIsAllScreen(): LoveIsAllScreen {
//        loveIsAllScreenBtn.click()
//        return LoveIsAllScreen(this.device)
//    }
//
//    fun signOut(): AuthScreen {
//        accountMenuBtn.click()
//        logoutOption.click()
//        return AuthScreen(this.device)
//    }
}
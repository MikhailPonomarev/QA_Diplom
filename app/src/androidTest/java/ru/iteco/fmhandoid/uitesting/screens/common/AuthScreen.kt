package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import io.qameta.allure.kotlin.Allure
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class AuthScreen(private val device: UiDevice) : BaseScreen(device) {
    private val screenTitle = findByText("Authorization")
    private val loginInput = findByText("Login")
    private val passInput = findByText("Password")
    private val signInBtn = findByResId("enter_button")
    private val emptyFieldAlert = findByText("Login and password cannot be empty")
    private val wrongCredentialsAlert = findByText("Wrong login or password")

    fun getScreenTitle(): UiObject = screenTitle

    fun loadingScreenElementsShouldBeVisible() {
        Allure.step("Должны отображаться элементы загрузочного экрана")

        assertViewIsVisible(findByResId("splashscreen_image_view"))
        assertViewIsVisible(findByResId("splash_screen_circular_progress_indicator"))
        assertViewIsVisible(findByResId("splashscreen_text_view"))
    }

    fun assertIsSignInScreen() {
        Allure.step("Должен быть открыт экран Авторизации")

        assertViewIsVisible(screenTitle)
        assertViewIsVisible(loginInput)
        assertViewIsVisible(passInput)
        assertViewIsVisible(signInBtn)
    }

    fun enterLogin(login: String) {
        Allure.step("Ввести логин")

        loginInput.waitForExists(10000)
        loginInput.text = login
    }

    fun enterPass(pass: String) {
        Allure.step("Ввести пароль")

        passInput.text = pass
    }

    fun clearInputFields(login: String, pass: String) {
        Allure.step("Очистить поля логина и пароля")

        findByText(login).clearTextField()
        findByText(pass).clearTextField()
    }

    fun clickSignInBtn() {
        Allure.step("Нажать кнопку Sign In")

        signInBtn.click()
    }

    fun assertEmptyFieldAlertAppears() {
        Allure.step("Должен появиться алерт 'Login and password cannot be empty'")

        emptyFieldAlert.waitUntilGone(1500)
    }

    fun assertWrongCredentialsAlertAppears() {
        Allure.step("Должен появиться алерт 'Wrong login or password'")

        wrongCredentialsAlert.waitUntilGone(1500)
    }

    fun signIn(login: String, pass: String): MainSection {
        Allure.step("Залогиниться в приложении")

        enterLogin(login)
        enterPass(pass)
        clickSignInBtn()
        return MainSection(this.device)
    }
}
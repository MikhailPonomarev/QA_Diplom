package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class AuthScreen(private val device: UiDevice) : BaseScreen(device) {
    private val screenTitle = findByText("Authorization")
    private val loginInput = findByText("Login")
    private val passInput = findByText("Password")
    private val signInBtn = findByResId("enter_button")
    private val emptyFieldAlert = findByText("Login and password cannot be empty")
    private val wrongCredentialsAlert = findByText("Wrong login or password")

    @Step
    fun loadingScreenElementsShouldBeVisible() {
        assertViewIsVisible(findByResId("splashscreen_image_view"))
        assertViewIsVisible(findByResId("splash_screen_circular_progress_indicator"))
        assertViewIsVisible(findByResId("splashscreen_text_view"))
    }

    @Step
    fun assertIsSignInScreen() {
        assertViewIsVisible(screenTitle)
        assertViewIsVisible(loginInput)
        assertViewIsVisible(passInput)
        assertViewIsVisible(signInBtn)
    }

    @Step
    fun enterLogin(login: String) {
        loginInput.waitForExists(10000)
        loginInput.text = login
    }

    @Step
    fun enterPass(pass: String) {
        passInput.text = pass
    }

    @Step
    fun clearInputFields(login: String, pass: String) {
        findByText(login).clearTextField()
        findByText(pass).clearTextField()
    }

    @Step
    fun clickSignInBtn() {
        signInBtn.click()
    }

    @Step("Должен появиться алерт 'Login and password cannot be empty'")
    fun assertEmptyFieldAlertAppears() {
        emptyFieldAlert.waitUntilGone(1500)
    }

    @Step("Должен появиться алерт 'Wrong login or password'")
    fun assertWrongCredentialsAlertAppears() {
        wrongCredentialsAlert.waitUntilGone(1500)
    }

    @Step
    fun signIn(login: String, pass: String): MainSection {
        enterLogin(login)
        enterPass(pass)
        clickSignInBtn()
        return MainSection(this.device)
    }
}
package ru.iteco.fmhandoid.uitesting.test

import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.screens.common.AuthScreen

class AuthScreenTest : BaseTest() {
    private val validLogin = Constants.VALID_LOGIN
    private val validPass = Constants.VALID_PASS
    private val falseLogin = Constants.FALSE_LOGIN
    private val falsePass = Constants.FALSE_PASS

    @Before
    override fun beforeEach() {
        initUiDeviceAndAppBar()
        waitForPackage()
    }

    @After
    override fun afterEach() {
        //ignore
    }

    @Test
    @DisplayName("Авторизация с валидными данными и выход из аккаунта")
    fun shouldSignInAndSignOut() {
        AuthScreen(device).loadingScreenElementsShouldBeVisible()

        val mainScreen = AuthScreen(device).signIn(validLogin, validPass)
        mainScreen.assertIsMainScreen()

        val signInScreen = appBar.signOut()
        signInScreen.assertIsSignInScreen()
    }

    @Test
    @DisplayName("Авторизация с невалидными и пустыми данными")
    fun signInWithFalseCredentials() {
        val signInScreen = AuthScreen(device)
        signInScreen.loadingScreenElementsShouldBeVisible()

        signInScreen.enterLogin(falseLogin)
        signInScreen.enterPass(falsePass)
        signInScreen.clickSignInBtn()
        signInScreen.assertWrongCredentialsAlertAppears()
        signInScreen.clearInputFields(falseLogin, falsePass)

        signInScreen.enterLogin(falseLogin)
        signInScreen.enterPass(validPass)
        signInScreen.assertWrongCredentialsAlertAppears()
        signInScreen.clearInputFields(falseLogin, validPass)

        signInScreen.enterLogin(validLogin)
        signInScreen.enterPass(falsePass)
        signInScreen.assertWrongCredentialsAlertAppears()
        signInScreen.clearInputFields(validLogin, falsePass)

        signInScreen.clickSignInBtn()
        signInScreen.assertEmptyFieldAlertAppears()
    }
}
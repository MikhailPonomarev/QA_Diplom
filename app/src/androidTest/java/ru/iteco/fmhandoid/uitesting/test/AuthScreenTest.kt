package ru.iteco.fmhandoid.uitesting.test

import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.screens.common.AuthScreen

@RunWith(AllureAndroidJUnit4::class)
class AuthScreenTest : BaseTest() {
    private val validLogin = Constants.VALID_LOGIN
    private val validPass = Constants.VALID_PASS
    private val falseLogin = Constants.FALSE_LOGIN
    private val falsePass = Constants.FALSE_PASS

    @Before
    override fun beforeEach() {
        initUiDeviceAndAppBar()
        waitForPackage()
        AuthScreen(device).loadingScreenElementsShouldBeVisible()
        if (appBar.getAppMenuBtn().waitForExists(10000)) {
            appBar.signOut()
        }
    }

    @Test
    @DisplayName("Авторизация с валидными данными и выход из аккаунта")
    fun shouldSignInAndSignOut() {
        val mainScreen = AuthScreen(device).signIn(validLogin, validPass)
        mainScreen.assertIsMainScreen()

        val authScreen = appBar.signOut()
        authScreen.assertIsSignInScreen()
    }

    @Test
    @DisplayName("Авторизация с невалидными и пустыми данными")
    fun signInWithFalseCredentials() {
        val authScreen = AuthScreen(device)

        authScreen.enterLogin(falseLogin)
        authScreen.enterPass(falsePass)
        authScreen.clickSignInBtn()
        authScreen.assertWrongCredentialsAlertAppears()
        authScreen.clearInputFields(falseLogin, falsePass)

        authScreen.enterLogin(falseLogin)
        authScreen.enterPass(validPass)
        authScreen.clickSignInBtn()
        authScreen.assertWrongCredentialsAlertAppears()
        authScreen.clearInputFields(falseLogin, validPass)

        authScreen.enterLogin(validLogin)
        authScreen.enterPass(falsePass)
        authScreen.clickSignInBtn()
        authScreen.assertWrongCredentialsAlertAppears()
        authScreen.clearInputFields(validLogin, falsePass)

        authScreen.clickSignInBtn()
        authScreen.assertEmptyFieldAlertAppears()
    }
}
package ru.iteco.fmhandoid.uitesting.test

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
    fun beforeEach() {
        initUiDeviceAndAppBar()
        waitForPackage()
    }

    @Test
    fun shouldSignInAndSignOut() {
        AuthScreen(device).loadingScreenElementsShouldBeVisible()

        val mainScreen = AuthScreen(device).signIn(validLogin, validPass)
        mainScreen.assertIsMainScreen()

        val signInScreen = appBar.signOut()
        signInScreen.assertIsSignInScreen()
    }

    @Test
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
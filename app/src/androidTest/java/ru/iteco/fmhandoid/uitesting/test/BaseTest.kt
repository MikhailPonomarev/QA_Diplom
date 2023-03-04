package ru.iteco.fmhandoid.uitesting.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import ru.iteco.fmhandoid.uitesting.screens.common.AboutScreen
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimCreateEditScreen
import ru.iteco.fmhandoid.uitesting.screens.common.AppBar
import ru.iteco.fmhandoid.uitesting.screens.common.AuthScreen
import ru.iteco.fmhandoid.uitesting.screens.news.NewsCreateEditScreen
import ru.iteco.fmhandoid.uitesting.testdata.Constants

open class BaseTest {
    protected lateinit var device: UiDevice
    protected lateinit var appBar: AppBar
    private val timeout = 5000L
    private val packageName = Constants.PACKAGE

    fun waitForPackage() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), timeout)
    }

    fun initUiDeviceAndAppBar() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        appBar = AppBar(device)
    }

    @Before
    open fun beforeEach() {
        initUiDeviceAndAppBar()
        waitForPackage()
        signInIfNotAuthorized()
        setScreenState()
    }

    private fun signInIfNotAuthorized() {
        val authScreen = AuthScreen(device)
        if (authScreen.getScreenTitle().waitForExists(timeout)) {
            authScreen.signIn(Constants.VALID_LOGIN, Constants.VALID_PASS)
        }
    }

    private fun setScreenState() {
        val claimCreateEditScreen = ClaimCreateEditScreen(device)
        if (claimCreateEditScreen.getCreatingTitle().exists() ||
            claimCreateEditScreen.getEditingTitle().exists()
        ) {
            device.pressBack()
        }

        val newsCreateEditScreen = NewsCreateEditScreen(device)
        if (newsCreateEditScreen.getCreatingTitle().exists() ||
            newsCreateEditScreen.getEditingTitle().exists()
        ) {
            device.pressBack()
        }

        val aboutScreen = AboutScreen(device)
        if (aboutScreen.getVersionTitle().exists()) {
            aboutScreen.clickBackBtn()
        }
    }
}
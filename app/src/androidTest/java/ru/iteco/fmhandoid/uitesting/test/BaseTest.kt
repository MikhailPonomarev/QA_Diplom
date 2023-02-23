package ru.iteco.fmhandoid.uitesting.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import ru.iteco.fmhandoid.uitesting.screens.common.AppBar
import ru.iteco.fmhandoid.uitesting.screens.common.AuthScreen
import ru.iteco.fmhandoid.uitesting.testdata.Constants

@RunWith(AllureAndroidJUnit4::class)
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
        AuthScreen(device).signIn(Constants.VALID_LOGIN, Constants.VALID_PASS)
        appBar = AppBar(device)
    }

    @After
    open fun afterEach() {
        AppBar(device).signOut()
    }
}
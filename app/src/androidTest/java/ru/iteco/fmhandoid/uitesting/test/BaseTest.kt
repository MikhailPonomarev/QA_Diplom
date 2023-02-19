package ru.iteco.fmhandoid.uitesting.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.runner.RunWith
import ru.iteco.fmhandoid.uitesting.screens.AppBar
import ru.iteco.fmhandoid.uitesting.testdata.Constants

@RunWith(AndroidJUnit4::class)
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
}
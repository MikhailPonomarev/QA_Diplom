package ru.iteco.fmhandoid.uitesting.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.runner.RunWith
import ru.iteco.fmhandoid.uitesting.Constants

@RunWith(AndroidJUnit4::class)
open class BaseTest {
    lateinit var device: UiDevice
    private val timeout = 5000L
    private val packageName = Constants.PACKAGE

    fun waitForPackage() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), timeout)
    }

    fun initUiDevice() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }


}
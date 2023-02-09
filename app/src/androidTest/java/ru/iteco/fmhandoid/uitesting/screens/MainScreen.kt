package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.UiDevice

class MainScreen(private val device: UiDevice) : BaseScreen(device) {
    private val newsContainer = findByResId("container_list_news_include_on_fragment_main")
    private val claimsContainer = findByResId("container_list_claim_include_on_fragment_main")

    fun assertIsMainScreen() {
        newsContainer.exists()
        claimsContainer.exists()
    }

//    fun signOut(): AuthScreen {
//
//        return AuthScreen(this.device)
//    }
}
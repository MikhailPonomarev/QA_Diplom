package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo

class MainSection(private val device: UiDevice) : BaseScreen(device) {
    private val newsContainer = findByResId("container_list_news_include_on_fragment_main")
    private val claimsContainer = findByResId("container_list_claim_include_on_fragment_main")
    private val allClaimsBtn = findByResId("") //TODO: implement
    private val addClaimBtn = findByResId("add_new_claim_material_button")

    @Step
    fun assertIsMainScreen(): Boolean {
        return newsContainer.exists() && claimsContainer.exists()
    }

    @Step
    fun clickAddClaimBtn(): ClaimCreateEditScreen {
        addClaimBtn.click()
        return ClaimCreateEditScreen(this.device)
    }

    @Step
    fun assertCreatedClaimOnMainScreen(info: ClaimInfo) {
        findByText(info.title).exists()
        findByText(info.planDate).exists()
        findByText(info.time).exists()
    }

    @Step
    fun openExactClaim(claimTitle: String): CreatedClaimScreen {
        UiCollection(UiSelector().resourceId("${baseId}claim_list_recycler_view"))
            .getChildByText(UiSelector().resourceId("${baseId}claim_list_card"), claimTitle)
            .click()
        return CreatedClaimScreen(this.device)
    }
}
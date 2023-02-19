package ru.iteco.fmhandoid.uitesting.screens.claims

import androidx.test.uiautomator.*
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo

class ClaimsSection(private val device: UiDevice) : BaseScreen(device) {
    private val addClaimBtn = findByResId("add_new_claim_material_button")

    @Step
    fun assertIsClaimsSection() {
        findByText("Claims").exists()
    }

    @Step
    fun clickAddClaimBtn(): ClaimCreateEditScreen {
        addClaimBtn.click()
        return ClaimCreateEditScreen(this.device)
    }

    @Step
    fun assertCreatedClaimInClaimsSection(info: ClaimInfo, isExecutorAssigned: Boolean) {
        findByText(info.title).exists()
        if (isExecutorAssigned) {
            UiCollection(UiSelector().text(info.executor))
                .getFromParent(UiSelector().text(info.title))
                .exists()
        }
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
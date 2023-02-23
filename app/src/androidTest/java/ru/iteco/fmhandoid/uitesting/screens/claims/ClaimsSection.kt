package ru.iteco.fmhandoid.uitesting.screens.claims

import androidx.test.uiautomator.*
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class ClaimsSection(private val device: UiDevice) : BaseScreen(device) {
    private val addClaimBtn = findByResId("add_new_claim_material_button")
    private val claimsRecyclerViewId = "${baseId}claim_list_recycler_view"
    private val claimListCardId = "${baseId}claim_list_card"

    @Step
    fun assertIsClaimsSection() {
        assertViewIsVisible(findByText("Claims"))
    }

    @Step
    fun clickAddClaimBtn(): ClaimCreateEditScreen {
        addClaimBtn.click()
        return ClaimCreateEditScreen(this.device)
    }

    @Step
    fun assertCreatedClaimInClaimsSection(info: ClaimInfo, isExecutorAssigned: Boolean) {
        val parentClaim = UiCollection(UiSelector().resourceId(claimsRecyclerViewId))
            .getChildByInstance(UiSelector().resourceId(claimListCardId), 0)

        assertViewIsVisible(
            parentClaim.getChild(
                findByText(info.title).selector
            )
        )

        if (isExecutorAssigned) {
            assertViewIsVisible(
                parentClaim.getChild(
                    findByText(info.executor).selector
                )
            )
        }

        assertViewIsVisible(
            parentClaim.getChild(
                findByText(info.planDate).selector
            )
        )

        assertViewIsVisible(
            parentClaim.getChild(
                findByText(info.time).selector
            )
        )
    }

    @Step
    fun openExactClaim(claimTitle: String): CreatedClaimScreen {
        UiCollection(UiSelector().resourceId(claimsRecyclerViewId))
            .getChildByText(UiSelector().resourceId(claimListCardId), claimTitle)
            .click()
        return CreatedClaimScreen(this.device)
    }
}
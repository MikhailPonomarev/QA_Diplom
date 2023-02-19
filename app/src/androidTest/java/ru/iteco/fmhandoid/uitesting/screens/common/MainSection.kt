package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimCreateEditScreen
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimsSection
import ru.iteco.fmhandoid.uitesting.screens.claims.CreatedClaimScreen
import ru.iteco.fmhandoid.uitesting.screens.news.NewsSection
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo

class MainSection(private val device: UiDevice) : BaseScreen(device) {
    private val newsContainer = findByResId("container_list_news_include_on_fragment_main")
    private val claimsContainer = findByResId("container_list_claim_include_on_fragment_main")
    private val allNewsBtn = findByResId("all_news_text_view")
    private val allClaimsBtn = findByResId("all_claims_text_view")
    private val addClaimBtn = findByResId("add_new_claim_material_button")

    @Step
    fun assertIsMainScreen(): Boolean {
        return newsContainer.exists() && claimsContainer.exists()
    }

    @Step
    fun assertCreatedNewsInMainSection(info: NewsInfo) {
        device.findObject(UiSelector().resourceId("${baseId}view_news_item_image_view")
            .fromParent(UiSelector().fromParent(UiSelector().text(info.newsTitle))))
            .click()
        findByText(info.publicationDate).exists()
        findByText(info.description).exists()
    }

    @Step
    fun clickAllNewsBtn(): NewsSection {
        allNewsBtn.click()
        return NewsSection(this.device)
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

    @Step
    fun clickAllClaimsBtn(): ClaimsSection {
        allClaimsBtn.click()
        return ClaimsSection(this.device)
    }
}
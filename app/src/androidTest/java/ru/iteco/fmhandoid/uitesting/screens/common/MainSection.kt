package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertFalse
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimCreateEditScreen
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimsSection
import ru.iteco.fmhandoid.uitesting.screens.claims.CreatedClaimScreen
import ru.iteco.fmhandoid.uitesting.screens.news.NewsSection
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo
import ru.iteco.fmhandoid.uitesting.testdata.NewsInfo
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class MainSection(private val device: UiDevice) : BaseScreen(device) {
    private val newsContainer = findByResId("container_list_news_include_on_fragment_main")
    private val claimsContainer = findByResId("container_list_claim_include_on_fragment_main")
    private val allNewsBtn = findByResId("all_news_text_view")
    private val allClaimsBtn = findByResId("all_claims_text_view")
    private val addClaimBtn = findByResId("add_new_claim_material_button")

    fun assertIsMainScreen() {
        Allure.step("Должен быть открыт раздел Main")

        assertViewIsVisible(newsContainer)
        assertViewIsVisible(claimsContainer)
    }

    fun assertCreatedNewsInMainSection(info: NewsInfo) {
        Allure.step("Созданная новость должна отображаться разделе Main")

        device.findObject(
            UiSelector().resourceId("${baseId}view_news_item_image_view")
                .fromParent(UiSelector().fromParent(UiSelector().text(info.newsTitle)))
        )
            .click()
        assertViewIsVisible(findByText(info.publicationDate))
        assertViewIsVisible(findByText(info.description))
    }

    fun assertDeactivatedNewsNotVisibleInMainSection(newsTitle: String) {
        Allure.step("Неактивная новость не должна отображаться разделе Main")

        assertFalse(
            findByText(newsTitle).exists()
        )
    }

    fun clickAllNewsBtn(): NewsSection {
        Allure.step("Нажать кнопку All News")

        allNewsBtn.click()
        return NewsSection(this.device)
    }

    fun clickAddClaimBtn(): ClaimCreateEditScreen {
        Allure.step("Нажать кнопку All News")

        addClaimBtn.click()
        return ClaimCreateEditScreen(this.device)
    }

    fun assertCreatedClaimOnMainScreen(info: ClaimInfo, isExecutorAssigned: Boolean) {
        Allure.step("Созданная претензия должна отображаться в разделе Main")

        val parentClaim = UiCollection(
            UiSelector().resourceId("${baseId}claim_list_recycler_view")
        ).getChildByInstance(UiSelector().resourceId("${baseId}claim_list_card"), 0)

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

    fun openExactClaim(claimTitle: String): CreatedClaimScreen {
        Allure.step("Открыть претензию в разделе Main по заголовку")

        UiCollection(UiSelector().resourceId("${baseId}claim_list_recycler_view"))
            .getChildByText(UiSelector().resourceId("${baseId}claim_list_card"), claimTitle)
            .click()
        return CreatedClaimScreen(this.device)
    }

    fun clickAllClaimsBtn(): ClaimsSection {
        Allure.step("Нажать кнопку All Claims")

        allClaimsBtn.click()
        return ClaimsSection(this.device)
    }
}
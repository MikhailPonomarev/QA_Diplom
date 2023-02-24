package ru.iteco.fmhandoid.uitesting.test

import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandoid.uitesting.screens.claims.ClaimsSection
import ru.iteco.fmhandoid.uitesting.screens.common.MainSection
import ru.iteco.fmhandoid.uitesting.screens.common.Modal
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo

@RunWith(AllureAndroidJUnit4::class)
class ClaimsTest : BaseTest() {
    private val openStatus = "Open"
    private val inProgressStatus = "In progress"
    private val executedStatus = "Executed"
    private val comment = "Комментарий к заявке"

    @Test
    @DisplayName("Создать претензию в разделе Claims с выбором исполнителя")
    fun createClaimFromClaimsSectionWithExecutor() {
        val claimsSectionBeforeCreateFirstClaim = appBar.openClaimsSection()
        claimsSectionBeforeCreateFirstClaim.assertIsClaimsSection()
        val createClaimsScreenFromClaimsSection = claimsSectionBeforeCreateFirstClaim
            .clickAddClaimBtn()

        val firstClaimInfo = ClaimInfo(inProgressStatus)
        val claimsSectionAfterCreateFirstClaim = createClaimsScreenFromClaimsSection
            .createClaimFromClaimsSection(
                firstClaimInfo,
                true
            )
        claimsSectionAfterCreateFirstClaim.assertIsClaimsSection()
        claimsSectionAfterCreateFirstClaim.assertCreatedClaimInClaimsSection(
            firstClaimInfo,
            true
        )

        val createdClaimScreenFromClaimsSection = claimsSectionAfterCreateFirstClaim
            .openExactClaim(firstClaimInfo.title)
        createdClaimScreenFromClaimsSection.assertCreatedClaimInfo(
            firstClaimInfo,
            true
        )

        val mainSectionAfterCreateFirstClaim = appBar.openMainSection()
        mainSectionAfterCreateFirstClaim.assertIsMainScreen()
        mainSectionAfterCreateFirstClaim.assertCreatedClaimOnMainScreen(
            firstClaimInfo,
            true
        )

        val createdClaimScreenFromMainSection =
            mainSectionAfterCreateFirstClaim.openExactClaim(firstClaimInfo.title)
        createdClaimScreenFromMainSection.assertCreatedClaimInfo(
            firstClaimInfo,
            true
        )
        createdClaimScreenFromMainSection.returnToMainSection()
        mainSectionAfterCreateFirstClaim.assertIsMainScreen()
    }

    @Test
    @DisplayName("Создать претензию в разделе Claims без выбора исполнителя")
    fun createClaimFromClaimsSectionWithoutExecutor() {
        val claimsSectionBefore = appBar.openClaimsSection()
        claimsSectionBefore.assertIsClaimsSection()
        val createClaimsScreen = claimsSectionBefore.clickAddClaimBtn()

        val firstClaimInfo = ClaimInfo(openStatus)
        val claimsSectionAfter = createClaimsScreen.createClaimFromClaimsSection(
            firstClaimInfo,
            false
        )
        claimsSectionAfter.assertIsClaimsSection()
        claimsSectionAfter.assertCreatedClaimInClaimsSection(
            firstClaimInfo,
            false
        )
    }

    @Test
    @DisplayName("Создать претензию из раздела Main")
    fun createClaimFromMainSection() {
        val createClaimScreen = MainSection(device).clickAddClaimBtn()
        val claimInfo = ClaimInfo(openStatus)
        val mainSectionAfter = createClaimScreen.createClaimFromMainSection(
            claimInfo,
            false
        )
        mainSectionAfter.assertIsMainScreen()
        mainSectionAfter.assertCreatedClaimOnMainScreen(claimInfo, false)

        val createdClaimScreen = mainSectionAfter.openExactClaim(claimInfo.title)
        createdClaimScreen.assertCreatedClaimInfo(claimInfo, false)

        createdClaimScreen
            .returnToMainSection()
            .clickAllClaimsBtn()
            .assertIsClaimsSection()
    }

    @Test()
    @DisplayName("Редактирование претензии в статусе Open")
    fun editCreatedClaimInOpenStatus() {
        val claimsSectionBeforeCreate = appBar.openClaimsSection()

        val claimInfo = ClaimInfo(openStatus)
        val claimCreateScreen = claimsSectionBeforeCreate.clickAddClaimBtn()
        val claimsSectionAfterCreate = claimCreateScreen.createClaimFromClaimsSection(
            claimInfo,
            false
        )
        claimsSectionAfterCreate.assertCreatedClaimInClaimsSection(
            claimInfo,
            false
        )

        val createdClaimScreenBeforeEdit = claimsSectionAfterCreate
            .openExactClaim(claimInfo.title)
        createdClaimScreenBeforeEdit.assertCreatedClaimInfo(claimInfo, false)

        val editClaimScreen = createdClaimScreenBeforeEdit.clickEditClaimBtn()
        val updClaimInfo = ClaimInfo(inProgressStatus)
        val createdClaimScreenAfterEdit = editClaimScreen.editCreatedClaim(
            updClaimInfo,
            true
        )
        createdClaimScreenAfterEdit.assertCreatedClaimInfo(
            updClaimInfo,
            true
        )
    }

    @Test
    @DisplayName("Создание претензии с пустыми полями")
    fun shouldNotCreateClaimWithEmptyFields() {
        val claimCreateScreen = MainSection(device).clickAddClaimBtn()
        claimCreateScreen.clickSaveBtn()

        val emptyModal = Modal(device)
        emptyModal.assertModalText("Fill empty fields")
        emptyModal.clickOk()

        claimCreateScreen.assertEmptyFieldAlertsAreVisible()
        claimCreateScreen.clickCancelBtn().clickOk()
    }

    @Test
    @DisplayName("Отмена создания претензии")
    fun cancelClaimCreate() {
        val claimsSectionBefore = appBar.openClaimsSection()
        val createClaimScreen = claimsSectionBefore.clickAddClaimBtn()

        val cancelModal = createClaimScreen.clickCancelBtn()
        cancelModal.assertModalText(
            "The changes won't be saved, do you really want to log out?"
        )
        cancelModal.clickOk()
        ClaimsSection(device).assertIsClaimsSection()
    }

    @Test
    @DisplayName("Отмена претензии, перевод в статус Canceled")
    fun moveClaimInCanceledStatus() {
        val claimsSectionBeforeCreate = appBar.openClaimsSection()

        val claimCreateScreen = claimsSectionBeforeCreate.clickAddClaimBtn()
        val claimInfo = ClaimInfo(openStatus)
        val claimsSectionAfterCreate = claimCreateScreen.createClaimFromClaimsSection(
            claimInfo,
            false
        )

        val createdClaimBeforeUpdate = claimsSectionAfterCreate.openExactClaim(claimInfo.title)
        val createdClaimAfterUpdate = createdClaimBeforeUpdate.cancelClaim()
        createdClaimAfterUpdate.assertClaimStatus("Canceled")

        createdClaimAfterUpdate.addComment(comment)
        createdClaimAfterUpdate.assertCommentIsPresent(comment)
    }

    @Test
    @DisplayName("Проверка workflow претензии")
    fun checkClaimStatuses() {
        val claimInfo = ClaimInfo(openStatus)

        val claimsSectionAfterCreate = appBar
            .openClaimsSection()
            .clickAddClaimBtn()
            .createClaimFromClaimsSection(claimInfo, false)

        val createdClaimInOpenStatus = claimsSectionAfterCreate.openExactClaim(claimInfo.title)
        createdClaimInOpenStatus.assertClaimStatus(claimInfo.status)

        val createdClaimInProgressStatus = createdClaimInOpenStatus.takeToWork()
        createdClaimInProgressStatus.assertClaimStatus(inProgressStatus)

        val createdClaimInExecutedStatus = createdClaimInProgressStatus.toExecute(comment)
        createdClaimInExecutedStatus.assertClaimStatus(executedStatus)
        createdClaimInExecutedStatus.assertCommentIsPresent(comment)
    }

    @Test
    @DisplayName("Отказ от исполнения претензии, перевод в статус Open")
    fun shouldThrowOffClaim() {
        val claimInfo = ClaimInfo(openStatus)

        val claimsSectionAfterCreate = appBar
            .openClaimsSection()
            .clickAddClaimBtn()
            .createClaimFromClaimsSection(claimInfo, true)

        val createdClaimInProgressStatus = claimsSectionAfterCreate.openExactClaim(claimInfo.title)
        createdClaimInProgressStatus.assertClaimStatus(inProgressStatus)

        val createdClaimInOpenStatus = createdClaimInProgressStatus.throwOff(comment)
        createdClaimInOpenStatus.assertCreatedClaimInfo(claimInfo, false)
        createdClaimInOpenStatus.assertCommentIsPresent(comment)
    }
}
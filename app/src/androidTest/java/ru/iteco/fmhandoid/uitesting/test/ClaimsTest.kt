package ru.iteco.fmhandoid.uitesting.test

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.screens.*
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo

class ClaimsTest : BaseTest() {
    private val openStatus = "Open"
    private val inProgressStatus = "In progress"
    private val executedStatus = "Executed"
    private val comment = "Комментарий к заявке"

    @Before
    fun beforeEach() {
        initUiDevice()
        waitForPackage()
        AuthScreen(device).signIn(Constants.VALID_LOGIN, Constants.VALID_PASS)
    }

    @After
    fun afterEach() {
        BaseScreen(device).signOut()
    }

    @Test
    fun createClaimFromClaimsSectionWithExecutor() {
        val claimsSectionBeforeCreateFirstClaim = MainSection(device).openClaimsSection()
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

        val mainSectionAfterCreateFirstClaim = createdClaimScreenFromClaimsSection
            .openMainSection()
        mainSectionAfterCreateFirstClaim.assertIsMainScreen()
        mainSectionAfterCreateFirstClaim.assertCreatedClaimOnMainScreen(firstClaimInfo)

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
    fun createClaimFromClaimsSectionWithoutExecutor() {
        val claimsSectionBefore = MainSection(device).openClaimsSection()
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
    fun createClaimFromMainSection() {
        val createClaimScreen = MainSection(device).clickAddClaimBtn()
        val claimInfo = ClaimInfo(openStatus)
        val mainSectionAfter = createClaimScreen.createClaimFromMainSection(
            claimInfo,
            false
        )
        mainSectionAfter.assertIsMainScreen()
        mainSectionAfter.assertCreatedClaimOnMainScreen(claimInfo)

        val createdClaimScreen = mainSectionAfter.openExactClaim(claimInfo.title)
        createdClaimScreen.assertCreatedClaimInfo(claimInfo, false)
    }

    @Test
    fun editCreatedClaimInOpenStatus() {
        val claimsSectionBeforeCreate = MainSection(device).openClaimsSection()

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
    fun shouldNotCreateClaimWithEmptyFields() {
        val claimCreateScreen = MainSection(device).clickAddClaimBtn()
        claimCreateScreen.clickSaveBtn()

        val emptyModal = Modal(device)
        emptyModal.assertModalIsOpen("Fill empty fields")
        emptyModal.clickOk()

        claimCreateScreen.assertEmptyFieldAlertsAreVisible()
        claimCreateScreen.clickCancelBtn().clickOk()
    }

    @Test
    fun cancelClaimCreate() {
        val cancelModalText = "The changes won't be saved, do you really want to log out?"

        val claimsSectionBefore = MainSection(device).openClaimsSection()
        val createClaimScreen = claimsSectionBefore.clickAddClaimBtn()

        val cancelModal = createClaimScreen.clickCancelBtn()
        cancelModal.assertModalIsOpen(cancelModalText)
        cancelModal.clickOk()
        ClaimsSection(device).assertIsClaimsSection()
    }

    @Test
    fun moveClaimInCanceledStatus() {
        val claimsSectionBeforeCreate = MainSection(device).openClaimsSection()

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
    fun checkClaimStatuses() {
        val claimInfo = ClaimInfo(openStatus)

        val claimsSectionAfterCreate = MainSection(device)
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
    fun shouldThrowOffClaim() {
        val claimInfo = ClaimInfo(openStatus)

        val claimsSectionAfterCreate = MainSection(device)
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
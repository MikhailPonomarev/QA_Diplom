package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.testdata.Constants
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo

class ClaimCreateEditScreen(private val device: UiDevice) : BaseScreen(device) {
    private val titleInput = findByResId("title_edit_text")
    private val executorInput = findByResId("executor_drop_menu_auto_complete_text_view")
    private val dateInput = findByResId("date_in_plan_text_input_edit_text")
    private val timeInput = findByResId("time_in_plan_text_input_edit_text")
    private val descriptionInput = findByResId("description_edit_text")
    private val saveBtn = findByResId("save_button")
    private val cancelBtn = findByResId("cancel_button")

    @Step
    fun createClaimFromClaimsSection(info: ClaimInfo, isWithExecutor: Boolean): ClaimsSection {
        fillClaimFieldsAndSave(info, isWithExecutor)
        return ClaimsSection(this.device)
    }

    @Step
    fun createClaimFromMainSection(info: ClaimInfo, isWithExecutor: Boolean): MainSection {
        fillClaimFieldsAndSave(info, isWithExecutor)
        return MainSection(this.device)
    }

    @Step
    fun editCreatedClaim(info: ClaimInfo, isWithExecutor: Boolean): CreatedClaimScreen {
        fillClaimFieldsAndSave(info, isWithExecutor)
        return CreatedClaimScreen(this.device)
    }

    @Step
    private fun fillClaimFieldsAndSave(info: ClaimInfo, isExecutor: Boolean) {
        titleInput.text = info.title
        if (isExecutor) {
            executorInput.click()
            executorInput.text = info.executor
            val rect = executorInput.bounds
            device.waitForWindowUpdate(Constants.PACKAGE, 2000)
            device.click(rect.left + 64, rect.bottom + 72)
        }
        dateInput.text = info.planDate
        timeInput.text = info.time
        descriptionInput.text = info.description
        saveBtn.click()
    }

    @Step
    fun clickSaveBtn() {
        saveBtn.click()
    }

    @Step
    fun clickCancelBtn(): Modal {
        cancelBtn.click()
        return Modal(this.device)
    }

    @Step
    fun assertEmptyFieldAlertsAreVisible() {
        val alertIconId = "${baseId}text_input_end_icon"
        device
            .findObject(UiSelector().resourceId(alertIconId).fromParent(titleInput.selector))
            .exists()
        device
            .findObject(UiSelector().resourceId(alertIconId).fromParent(dateInput.selector))
            .exists()
        device
            .findObject(UiSelector().resourceId(alertIconId).fromParent(timeInput.selector))
            .exists()
        device
            .findObject(UiSelector().resourceId(alertIconId).fromParent(descriptionInput.selector))
            .exists()
    }
}
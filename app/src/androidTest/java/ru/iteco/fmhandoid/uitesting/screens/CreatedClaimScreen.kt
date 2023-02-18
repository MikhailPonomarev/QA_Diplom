package ru.iteco.fmhandoid.uitesting.screens

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo
import org.junit.Assert.assertEquals

class CreatedClaimScreen(private val device: UiDevice) : BaseScreen(device) {
    private val addCommentBtn = findByResId("add_comment_image_button")
    private val closeClaimBtn = findByResId("close_image_button")
    private val actionsDropListBtn = findByResId("status_processing_image_button")
    private val editBtn = findByResId("edit_processing_image_button")

    @Step
    fun assertCreatedClaimInfo(info: ClaimInfo, isExecutorAssigned: Boolean) {
        val actualTitle = findByResId("title_text_view").text
        assertEquals(info.title, actualTitle)

        val actualExecutor = findByResId("executor_name_text_view").text
        if (isExecutorAssigned) {
            assertEquals(info.executor, actualExecutor)
        } else {
            assertEquals("NOT ASSIGNED", actualExecutor)
        }

        val actualPlanDate = findByResId("plane_date_text_view").text
        assertEquals(info.planDate, actualPlanDate)

        val actualPlanTime = findByResId("plan_time_text_view").text
        assertEquals(info.time, actualPlanTime)

        assertClaimStatus(info.status)

        val actualDescription = findByResId("description_text_view").text
        assertEquals(info.description, actualDescription)

        val actualAuthor = findByResId("author_name_text_view").text
        assertEquals(info.executor, actualAuthor)
    }

    @Step
    fun returnToClaimsSection(): ClaimsSection {
        closeClaimBtn.click()
        return ClaimsSection(this.device)
    }

    @Step
    fun returnToMainSection(): MainSection {
        closeClaimBtn.click()
        return MainSection(this.device)
    }

    @Step
    fun clickEditClaimBtn(): ClaimCreateEditScreen {
        editBtn.click()
        return ClaimCreateEditScreen(this.device)
    }

    @Step
    fun assertClaimStatus(expectedStatus: String) {
        val actualStatus = findByResId("status_label_text_view").text
        assertEquals(expectedStatus, actualStatus)
    }

    @Step
    fun takeToWork(): CreatedClaimScreen {
        actionsDropListBtn.click()
        findByText("take to work").click()
        return CreatedClaimScreen(this.device)
    }

    @Step
    fun toExecute(comment: String): CreatedClaimScreen {
        actionsDropListBtn.click()
        findByText("To execute").click()
        leaveCommentOnStatusChange(comment)
        return CreatedClaimScreen(this.device)
    }

    @Step
    fun throwOff(comment: String): CreatedClaimScreen {
        actionsDropListBtn.click()
        findByText("Throw off").click()
        leaveCommentOnStatusChange(comment)
        return CreatedClaimScreen(this.device)
    }

    @Step
    private fun leaveCommentOnStatusChange(comment: String) {
        findByResId("editText").text = comment
        findByText("OK").click()
    }

    @Step
    fun cancelClaim(): CreatedClaimScreen {
        actionsDropListBtn.click()
        findByText("Cancel").click()
        return CreatedClaimScreen(this.device)
    }

    @Step
    fun addComment(comment: String): CreatedClaimScreen {
        addCommentBtn.click()
        find(By.clazz("android.widget.EditText")).text = comment
        findByResId("save_button").click()
        return CreatedClaimScreen(this.device)
    }

    @Step
    fun assertCommentIsPresent(expectedComment: String) {
        val actualComment = findByResId("comment_description_text_view").text
        assertEquals(expectedComment, actualComment)
    }
}
package ru.iteco.fmhandoid.uitesting.screens.claims

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import io.qameta.allure.kotlin.Allure
import ru.iteco.fmhandoid.uitesting.testdata.ClaimInfo
import org.junit.Assert.assertEquals
import ru.iteco.fmhandoid.uitesting.screens.common.MainSection
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen

class CreatedClaimScreen(private val device: UiDevice) : BaseScreen(device) {
    private val addCommentBtn = findByResId("add_comment_image_button")
    private val closeClaimBtn = findByResId("close_image_button")
    private val actionsDropListBtn = findByResId("status_processing_image_button")
    private val editBtn = findByResId("edit_processing_image_button")

    fun assertCreatedClaimInfo(info: ClaimInfo, isExecutorAssigned: Boolean) {
        Allure.step("Проверить созданную претензию")

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

    fun returnToClaimsSection(): ClaimsSection {
        Allure.step("Закрыть претензию и вернуться в раздел Claims")

        closeClaimBtn.click()
        return ClaimsSection(this.device)
    }

    fun returnToMainSection(): MainSection {
        Allure.step("Закрыть претензию и вернуться в раздел Main")

        closeClaimBtn.click()
        return MainSection(this.device)
    }

    fun clickEditClaimBtn(): ClaimCreateEditScreen {
        Allure.step("Нажать кнопку \"Редактировать претензию\"")

        editBtn.click()
        return ClaimCreateEditScreen(this.device)
    }

    fun assertClaimStatus(expectedStatus: String) {
        Allure.step("Проверить статус претензии")

        val actualStatus = findByResId("status_label_text_view").text
        assertEquals(expectedStatus, actualStatus)
    }

    fun takeToWork(): CreatedClaimScreen {
        Allure.step("Перевести претензию в статус \"In progress\"")

        actionsDropListBtn.click()
        findByText("take to work").click()
        return CreatedClaimScreen(this.device)
    }

    fun toExecute(comment: String): CreatedClaimScreen {
        Allure.step("Перевести претензию в статус \"Executed\"")

        actionsDropListBtn.click()
        findByText("To execute").click()
        leaveCommentOnStatusChange(comment)
        return CreatedClaimScreen(this.device)
    }

    fun throwOff(comment: String): CreatedClaimScreen {
        Allure.step("Перевести претензию в статус \"Open\"")

        actionsDropListBtn.click()
        findByText("Throw off").click()
        leaveCommentOnStatusChange(comment)
        return CreatedClaimScreen(this.device)
    }

    private fun leaveCommentOnStatusChange(comment: String) {
        Allure.step("Оставить комментарий при смене статуса заявки")

        findByResId("editText").text = comment
        findByText("OK").click()
    }

    fun cancelClaim(): CreatedClaimScreen {
        Allure.step("Перевести претензию в статус \"Canceled\"")

        actionsDropListBtn.click()
        findByText("Cancel").click()
        return CreatedClaimScreen(this.device)
    }

    fun addComment(comment: String): CreatedClaimScreen {
        Allure.step("Добавить комментарий к заявке")

        addCommentBtn.click()
        find(By.clazz("android.widget.EditText")).text = comment
        findByResId("save_button").click()
        return CreatedClaimScreen(this.device)
    }

    fun assertCommentIsPresent(expectedComment: String) {
        Allure.step("Проверить добавленный комментарий")

        val actualComment = findByResId("comment_description_text_view").text
        assertEquals(expectedComment, actualComment)
    }
}
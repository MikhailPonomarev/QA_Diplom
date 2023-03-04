package ru.iteco.fmhandoid.uitesting.screens.common

import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertEquals
import ru.iteco.fmhandoid.uitesting.utils.CustomAssertions.Companion.assertViewIsVisible

class LoveIsAllScreen(private val device: UiDevice) : BaseScreen(device) {
    private val quotesRecyclerView = findByResId("our_mission_item_list_recycler_view")

    fun assertIsLoveIsAllScreen() {
        Allure.step("Должен быть открыт экран Love is all")

        assertEquals(
            "Love is all",
            findByResId("our_mission_title_text_view").text
        )
        assertViewIsVisible(quotesRecyclerView)
    }

    fun assertFirstQuoteContent() {
        Allure.step("Проверить содержание цитаты")

        val firstQuote = UiCollection(quotesRecyclerView.selector)
            .getChildByInstance(
                UiSelector().resourceId("${baseId}our_mission_item_material_card_view"),
                0
            )

        assertViewIsVisible(
            firstQuote.getChild(
                UiSelector().resourceId("${baseId}our_mission_item_image_view")
            )
        )

        assertViewIsVisible(
            firstQuote.getChild(
                UiSelector().resourceId("${baseId}our_mission_item_title_text_view")
            )
        )

        firstQuote.getChild(UiSelector().resourceId(
            "${baseId}our_mission_item_open_card_image_button")
        ).click()

        assertViewIsVisible(
            firstQuote.getChild(UiSelector().resourceId(
                "${baseId}our_mission_item_description_text_view")
            )
        )
    }
}
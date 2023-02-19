package ru.iteco.fmhandoid.uitesting.screens.news

import androidx.test.uiautomator.UiDevice
import ru.iteco.fmhandoid.uitesting.screens.common.BaseScreen

class NewsSection(private val device: UiDevice) : BaseScreen(device) {
    private val controlPanelBtn = findByResId("edit_news_material_button")
    private val addNewsBtn = findByResId("add_news_image_view")
}
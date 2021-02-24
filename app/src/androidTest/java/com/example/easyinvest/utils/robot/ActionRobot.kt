package com.example.easyinvest.utils.robot

import androidx.annotation.LayoutRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.not

object ActionRobot {


    fun typeText(@LayoutRes viewID: Int, text: String) {
        onView(withId(viewID))
            .perform(ViewActions.typeText(text))

    }

    fun performClick(@LayoutRes viewID: Int) {
        onView(withId(viewID))
            .perform(ViewActions.closeSoftKeyboard(),ViewActions.click())
    }

    fun performClick(text: String) {
        onView(withText(text)).perform(ViewActions.click(), ViewActions.closeSoftKeyboard())
    }

}
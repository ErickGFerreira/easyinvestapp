package com.example.easyinvest.utils.robot

import androidx.annotation.LayoutRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

object AssertRobot {

    fun compareText(@LayoutRes viewID: Int, text: String) {
        Espresso.onView(ViewMatchers.withId(viewID))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.endsWith(text))))
    }

    fun compareHint(@LayoutRes viewID: Int, text: String) {
        Espresso.onView(ViewMatchers.withId(viewID))
            .check(ViewAssertions.matches(ViewMatchers.withHint(CoreMatchers.endsWith(text))))
    }

    fun checkIfViewIsEnabled(@LayoutRes viewID: Int) {
        Espresso.onView(ViewMatchers.withId(viewID))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }

    fun checkIfViewIsDisabled(@LayoutRes viewID: Int) {
        Espresso.onView(ViewMatchers.withId(viewID))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isEnabled())))
    }

}
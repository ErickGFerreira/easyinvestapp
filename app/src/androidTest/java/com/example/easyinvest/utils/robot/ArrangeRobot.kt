package com.example.easyinvest.utils.robot

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.example.easyinvest.R

object ArrangeRobot {

    inline fun <reified T : Fragment> launchFragment(
        fragmentArgs: Bundle? = null
    ): FragmentScenario<T> {
        return launchFragmentInContainer<T>(
            fragmentArgs = fragmentArgs
        )
    }

    inline fun <reified T : Fragment> setViewNavConroler(fragmentArgs: Bundle?= null): NavController {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragment<T>(fragmentArgs)
        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.investment_navigation)
        }
        return navController
    }
}
package com.openclassrooms.realestatemanager

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.openclassrooms.realestatemanager.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class SearchTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun searchTest() {
        Espresso.onView(ViewMatchers.withId(R.id.list_property))
            .check(ViewAssertions.matches(ViewMatchers.hasChildCount(2)))
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.search_min_price_edit)).perform(ViewActions.typeText("10000"))
        Espresso.onView(ViewMatchers.withId(R.id.search_min_surface_edit)).perform(ViewActions.typeText("10"))
        Espresso.onView(ViewMatchers.withId(R.id.search_market)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.list_property))
            .check(ViewAssertions.matches(ViewMatchers.hasChildCount(1)))
    }
}
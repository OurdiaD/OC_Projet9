package com.openclassrooms.realestatemanager

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.openclassrooms.realestatemanager.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun list_notEmpty() {
        onView(withId(R.id.list_property))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(2)))
    }

    @Test
    fun map_Open() {
        onView(withId(R.id.nav_map)).perform(click())
        onView(withId(R.id.map)).check(ViewAssertions.matches(withId(R.id.map)))
    }

    @Test
    fun simulatorTest() {
        onView(withId(R.id.nav_simulator)).perform(click())
        onView(withId(R.id.calculate)).perform(click())
        onView(withId(R.id.month_price)).check(ViewAssertions.matches(withText("1122")))
        onView(withId(R.id.deposit_edit)).perform(ViewActions.typeText("10000"))
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.calculate)).perform(click())
        onView(withId(R.id.month_price)).check(ViewAssertions.matches(withText("1035")))
    }

}
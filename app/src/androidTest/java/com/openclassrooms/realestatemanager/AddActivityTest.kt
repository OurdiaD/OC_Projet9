package com.openclassrooms.realestatemanager

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.openclassrooms.realestatemanager.ui.AddActivity
import com.openclassrooms.realestatemanager.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class AddActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addProperty() {
        Espresso.onView(ViewMatchers.withId(R.id.action_add)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.price_property_edit)).perform(ViewActions.typeText("500"))
        Espresso.onView(ViewMatchers.withId(R.id.surface_property_edit)).perform(ViewActions.typeText("5"))
        Espresso.onView(ViewMatchers.withId(R.id.rooms_property_edit)).perform(ViewActions.typeText("5"))
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.add_property)).perform(scrollTo()).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.list_property))
            .check(ViewAssertions.matches(ViewMatchers.hasChildCount(3)))
        Espresso.onView(ViewMatchers.withText("500")).check(ViewAssertions.matches(isDisplayed()))
    }
}
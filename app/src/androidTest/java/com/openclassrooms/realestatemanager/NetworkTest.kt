package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.openclassrooms.realestatemanager.ui.MainActivity
import com.openclassrooms.realestatemanager.utilsTest.WaitFor.Companion.waitFor
import org.junit.Rule
import org.junit.Test


class NetworkTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun isOffline() {
        val wifi: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        onView(withId(R.id.nav_map)).perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(1000))
        if (!wifi.isWifiEnabled) {
            onView(withId(R.id.internet_fail))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        }

    }

    @Test
    fun isOnline() {
        val wifi: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        onView(withId(R.id.nav_map)).perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(1000))
        if (wifi.isWifiEnabled) {
            onView(withId(R.id.internet_fail))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        }
    }
}
package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.openclassrooms.realestatemanager.ui.MainActivity
import org.junit.Rule
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.openclassrooms.realestatemanager.utilsTest.WaitFor.Companion.waitFor
import org.junit.Test


class NetworkTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun isOnline() {
        val wifi: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        Espresso.onView(ViewMatchers.withId(R.id.nav_map)).perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(1000));
        if (!wifi.isWifiEnabled) {
            Espresso.onView(ViewMatchers.withId(R.id.internet_fail))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        }

    }

    @Test
    fun online() {
        val wifi: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        Espresso.onView(ViewMatchers.withId(R.id.nav_map)).perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(1000));
        if (wifi.isWifiEnabled) {
            Espresso.onView(withId(R.id.internet_fail))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        }
    }
}
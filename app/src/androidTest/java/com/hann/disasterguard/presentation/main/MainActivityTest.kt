package com.hann.disasterguard.presentation.main


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.hann.disasterguard.R


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkMapFragmentShowing(){
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

    @Test
    fun checkClickArchiveMap() {
        onView(withId(R.id.btn_date)).perform(click())

        onView(withText(R.string.enter_start_date)).check(matches(isDisplayed()))
        onView(withText(R.string.enter_start)).perform(click())

        onView(withText(R.string.enter_end_date)).check(matches(isDisplayed()))
        onView(withText(R.string.find_archive)).perform(click())
    }

}
package com.moggot.multipreter;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by toor on 16.04.17.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void onCreate() throws Exception {
        onView(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.pager)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void getViewPager() throws Exception {
        onView(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.root_frame)))
                .check(matches(isDisplayed()));
    }
}
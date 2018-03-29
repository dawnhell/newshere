package com.vklochkov.newshere;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NewsSourcesListTests {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkNavigationToNewsSourceActivity() throws Exception {
        Activity mainActivity = mainActivityRule.getActivity();

        String[] newsTitles = mainActivity.getResources().getStringArray(R.array.news_titles);

        for (int i = 0; i < 5; ++i) {
            onView(withText(newsTitles[i]))
                .perform(click())
                .check(matches(withText(newsTitles[i])));
            pressBack();
        }
    }
}

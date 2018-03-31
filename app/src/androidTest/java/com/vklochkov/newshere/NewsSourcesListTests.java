package com.vklochkov.newshere;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NewsSourcesListTests {
    private static final String LAST_ITEM_ID = "item: 10";
    private Activity mainActivity;
    private String[] newsTitles;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        this.mainActivity = mainActivityRule.getActivity();
        this.newsTitles = this.mainActivity.getResources().getStringArray(R.array.news_titles);
    }

    @Test
    public void checkLastItemIsNotDisplayed() {
        onView(withText(LAST_ITEM_ID)).check(doesNotExist());
    }

    @Test
    public void checkNavigationToNewsSourceActivity() {
        for (int i = 0; i < 5; ++i) {
            onView(withText(this.newsTitles[i]))
                .perform(click())
                .check(matches(withText(this.newsTitles[i])));
            pressBack();
        }
    }
}

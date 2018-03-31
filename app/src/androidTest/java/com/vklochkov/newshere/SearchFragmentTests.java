package com.vklochkov.newshere;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchFragmentTests {
    private Activity mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        this.mainActivity = mainActivityRule.getActivity();
    }

    @Test
    public void checkArticleSearch() {
        onView(withText(this.mainActivity.getResources().getString(R.string.choose_news_source_text)))
            .perform(swipeLeft());
        onView(withId(R.id.searchText))
            .perform(click())
            .perform(typeText("Norway sea"), closeSoftKeyboard());
        onView(withId(R.id.searchBtn))
            .perform(click());

            pressMenuKey();

            onView(withId(R.id.article_list))
                .check(matches(isDisplayed()));
    }
}

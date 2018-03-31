package com.vklochkov.newshere;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.web.internal.deps.guava.base.Preconditions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTests {
    private AppCompatActivity mainActivity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View newsSourcesListFragment;
    private View searchBarFragment;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void checkCorrectContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.vklochkov.newshere", appContext.getPackageName());
    }

    @Test
    public void checkMainActivityPresence() {
        this.mainActivity = mainActivityRule.getActivity();
        assertNotNull(this.mainActivity);
    }

    @Test
    public void checkMainActivityUIElements() {
        this.mainActivity = mainActivityRule.getActivity();
        this.tabLayout = this.mainActivity.findViewById(R.id.tab_layout);
        this.viewPager = this.mainActivity.findViewById(R.id.pager);

        assertNotNull(this.tabLayout);
        assertNotNull(this.viewPager);
    }

    @Test
    public void checkFragmentsPresence() {
        this.mainActivity = mainActivityRule.getActivity();
        this.newsSourcesListFragment = this.mainActivity.findViewById(R.id.news_source_list_fragment);
        this.searchBarFragment = this.mainActivity.findViewById(R.id.search_bar_fragment);

        assertNotNull(this.newsSourcesListFragment);
        assertNotNull(this.searchBarFragment);
    }

    @Test
    public void swipeTest() {
        this.mainActivity = mainActivityRule.getActivity();
        onView(withText(this.mainActivity.getResources().getString(R.string.choose_news_source_text)))
            .perform(swipeLeft());
        Preconditions.checkNotNull(this.mainActivity.findViewById(R.id.searchBtn));
    }
}

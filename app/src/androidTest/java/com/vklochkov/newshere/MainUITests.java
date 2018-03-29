package com.vklochkov.newshere;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainUITests {
    private AppCompatActivity mainActivity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View newsSourcesListFragment;
    private View searchBarFragment;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void checkCorrectContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.vklochkov.newshere", appContext.getPackageName());
    }

    @Test
    public void checkMainActivityPresence() throws Exception {
        this.mainActivity = mainActivityRule.getActivity();
        assertNotNull(this.mainActivity);
    }

    @Test
    public void checkMainActivityUIElements() throws Exception {
        this.mainActivity = mainActivityRule.getActivity();
        this.tabLayout = this.mainActivity.findViewById(R.id.tab_layout);
        this.viewPager = this.mainActivity.findViewById(R.id.pager);

        assertNotNull(this.tabLayout);
        assertNotNull(this.viewPager);
    }

    @Test
    public void checkFragmentsPresence() throws Exception {
        this.mainActivity = mainActivityRule.getActivity();
        this.newsSourcesListFragment = this.mainActivity.findViewById(R.id.news_source_list_fragment);
        this.searchBarFragment = this.mainActivity.findViewById(R.id.search_bar_fragment);

        assertNotNull(this.newsSourcesListFragment);
        assertNotNull(this.searchBarFragment);
    }
}

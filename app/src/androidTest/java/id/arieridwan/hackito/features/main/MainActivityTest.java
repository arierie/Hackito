package id.arieridwan.hackito.features.main;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import id.arieridwan.hackito.R;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;

/**
 * Created by arieridwan on 28/06/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testToolbarDesign() {
        // check toolbar displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        // check toolbar name
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
        // check toolbar color
        onView(withId(R.id.toolbar)).check(matches(withToolbarBackGroundColor()));
    }

    // function for checking toolbar ui color
    private Matcher<? super View> withToolbarBackGroundColor() {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View view) {
                final ColorDrawable buttonColor = (ColorDrawable) view.getBackground();

                return ContextCompat
                        .getColor(activityTestRule.getActivity(), R.color.colorPrimary) ==
                        buttonColor.getColor();
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    @Test
    public void testSwipeRefresh() throws Exception {
        // swipe down to refresh the data
        onView(withId(R.id.swipeLayout))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));
        // check recyclerview visibility
        onView(withId(R.id.rv_stories)).check(matches(isDisplayed()));
        // check if the recyclerview has 10 items
        onView(withId(R.id.rv_stories)).check(new RecyclerViewItemCountAssertion(10));
    }

    // function for custom action view
    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }

    // class for checking item count recyclerview
    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    @Test
    public void testScrollDownAndLoadMore() throws Exception {
        // check recyclerview visibility
        onView(withId(R.id.rv_stories)).check(matches(isDisplayed()));
        // scroll to position 10
        onView(withId(R.id.rv_stories)).perform(scrollToPosition(10));
    }

    @Test
    public void testOnClickItem() throws Exception {
        // check recyclerview visibility
        onView(withId(R.id.rv_stories)).check(matches(isDisplayed()));
        // click on the item at position 8
        onView(withId(R.id.rv_stories)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
    }

    @Test
    public void testCommentAndReplyDetailActivity() throws Exception {
        // click item 0 on recyclerview main
        onView(withId(R.id.rv_stories)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check recyclerview visibility
        onView(withId(R.id.rv_comment)).check(matches(isDisplayed()));
        // click textview reply on item 0
        onView(withId(R.id.rv_comment)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.tv_reply)));
    }

    // class for custom click recyclerview item
    public static class MyViewAction {
        static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }
    }

    @Test
    public void testClickWebDetailActivity() throws Exception {
        // click item 0 on recyclerview main
        onView(withId(R.id.rv_stories)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // open finest webview after click fab
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void testOnBackDetailActivity() throws Exception {
        // click item 0 on recyclerview main
        onView(withId(R.id.rv_stories)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // navigate up after click on home menu item
        onView(withContentDescription("Navigate up")).perform(click());
    }

    @Test
    public void testScrollDownDetailActivity() throws Exception {
        // click item 0 on recyclerview main
        onView(withId(R.id.rv_stories)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check recyclerview visibility
        onView(withId(R.id.rv_comment)).check(matches(isDisplayed()));
        // scroll to position 1
        onView(withId(R.id.rv_comment)).perform(scrollToPosition(1));
    }

}
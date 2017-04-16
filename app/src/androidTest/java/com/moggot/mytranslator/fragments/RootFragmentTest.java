package com.moggot.mytranslator.fragments;

import android.content.ClipData;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.MainActivity;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.translator.Translator;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by toor on 16.04.17.
 */
public class RootFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void onViewCreated() throws Exception {
        clearDB();
        clickInputLang();
        clickOutputLang();
        clickChangeLang();
        checkTranslation();
        clickClearText();
        checkAddedItemsCount();
    }

    private void clearDB() {
        DataBase db = new DataBase(mActivityRule.getActivity());
        db.deleteAll();
    }

    private void clickChangeLang() {
        String inputLang = getText(withId(R.id.tvInputLang));
        String outputLang = getText(withId(R.id.tvOutputLang));

        onView(withId(R.id.btnChangeLang)).perform(click());

        onView(withId(R.id.tvInputLang)).check(matches(withText(outputLang)));
        onView(withId(R.id.tvOutputLang)).check(matches(withText(inputLang)));
    }

    private void clickInputLang() {
        onView(withId(R.id.tvInputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(6).perform(click());

        onView(withId(R.id.tvInputLang)).check(matches(withText(mActivityRule.getActivity().getString(R.string.am))));
    }

    private void clickOutputLang() {
        onView(withId(R.id.tvOutputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(5).perform(click());

        onView(withId(R.id.tvOutputLang)).check(matches(withText(mActivityRule.getActivity().getString(R.string.sq))));
    }

    private void checkTranslation() {
        onView(withId(R.id.tvInputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(0).perform(click());
        onView(withId(R.id.tvOutputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(1).perform(click());
        onView(withId(R.id.etText)).perform(typeText("time"));
        onView(withId(R.id.rlFragmentTranslation)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTranslation)).check(matches(withText("время")));
    }

    private void clickClearText() {
        onView(withId(R.id.etText)).perform(clearText());
        onView(withId(R.id.btnClearText)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.etText)).perform(typeText("Hello, World!"));
        onView(withId(R.id.btnClearText)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.btnClearText)).perform(click());
        onView(withId(R.id.btnClearText)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    private void checkAddedItemsCount() {
        clearDB();
        onView(withId(R.id.etText)).perform(typeText("my"));
        onView(withId(R.id.btnClearText)).perform(click());
        onView(withId(R.id.etText)).perform(typeText("code"));
        onView(withId(R.id.btnClearText)).perform(click());
        onView(withId(R.id.etText)).perform(typeText("is"));
        onView(withId(R.id.btnClearText)).perform(click());
        onView(withId(R.id.etText)).perform(typeText("awesome"));
        onView(withId(R.id.btnClearText)).perform(click());

        onData(instanceOf(Translator.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(3)
                .check(matches(isDisplayed()));

    }

    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

}
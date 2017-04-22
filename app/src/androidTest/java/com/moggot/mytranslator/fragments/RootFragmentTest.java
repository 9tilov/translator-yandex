package com.moggot.mytranslator.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.MainActivity;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.translator.Translator;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
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
import static org.hamcrest.Matchers.not;

/**
 * Created by toor on 16.04.17.
 */
public class RootFragmentTest {

    private static final String LOG_TAG = "RootFragmentTest";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void onViewCreated() throws Exception {
        clearDB();
        checkWithoutInternet();
        if (!checkWithoutInternet())
            return;
        clickInputLang();
        clickOutputLang();
        clickChangeLang();
        checkTranslation();
        clickClearText();
        checkAddedItemsCount();
        clickBackButtonToSave();
        clickHistoryItem();
        addItemToFavorites();
        deleteFavoritesItem();
    }

    private boolean checkWithoutInternet() {
        if (isNetworkAvailable())
            return true;
        else {
            onView(withId(R.id.etText)).perform(typeText("test connection"));
            onView(withId(R.id.tvErrorConnection)).check(matches(withText(mActivityRule.getActivity().getString(R.string.connection_error))));
            onView(withId(R.id.tvNoInternet)).check(matches(withText(mActivityRule.getActivity().getString(R.string.no_internet))));
            return false;
        }
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

        onView(withId(R.id.tvInputLang)).check(matches(withText(mActivityRule.getActivity().getString(R.string.hy))));
    }

    private void clickOutputLang() {
        onView(withId(R.id.tvOutputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(5).perform(click());

        onView(withId(R.id.tvOutputLang)).check(matches(withText(mActivityRule.getActivity().getString(R.string.ar))));
    }

    private void checkTranslation() {
        rotateScreen();
        onView(withId(R.id.tvInputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(4).perform(click());
        onView(withId(R.id.tvOutputLang)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.lvLanguages)).atPosition(61).perform(click());
        onView(withId(R.id.etText)).perform(typeText("time"));
        onView(withId(R.id.flFragmentTranslation)).check(matches(isDisplayed()));
        onView(withId(R.id.btnClearText)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAddFavorites)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCopyTranslation)).check(matches(isDisplayed()));
        onView(withId(R.id.tvYandexTranslatorLink)).check(matches(isDisplayed()));
        rotateScreen();
        onView(withId(R.id.etText)).perform(clearText());
        onView(withId(R.id.btnClearText)).check(matches(not(isDisplayed())));
        onView(withId(R.id.llFragmentHistory)).check(matches(isDisplayed()));
    }

    private void clickClearText() {
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

    private void clickBackButtonToSave() {
        clearDB();
        onView(withId(R.id.etText)).perform(typeText("Hello,"), pressImeActionButton());
        onView(withId(R.id.etText)).perform(typeText(" World"));
        onView(withId(R.id.etText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_BACK));
        onView(withId(R.id.btnClearText)).perform(click());
        onData(instanceOf(Translator.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(1)
                .check(matches(isDisplayed()));
    }

    private void clickHistoryItem() {
        clearDB();
        onView(withId(R.id.etText)).perform(typeText("Hello, World!"));
        onView(withId(R.id.btnClearText)).perform(click());
        onData(instanceOf(Translator.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(0).perform(click());
        onView(withId(R.id.tvTranslation)).check(matches(withText("Привет, Мир!")));
        onView(withId(R.id.btnClearText)).perform(click());
    }

    private void addItemToFavorites() {
        clearDB();
        onView(withId(R.id.etText)).perform(typeText("one"));
        onView(withId(R.id.btnClearText)).perform(click());
        rotateScreen();
        onView(withId(R.id.etText)).perform(typeText("two"));
        onView(withId(R.id.etText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_BACK));
        onView(withId(R.id.btnClearText)).perform(click());
        onData(instanceOf(Translator.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(1).onChildView(withId(R.id.adapterIwFavorites)).perform(click());
        onView(withId(R.id.pager)).perform(swipeLeft());
        rotateScreen();
        onView(withId(R.id.pager)).perform(swipeRight());
    }

    private void deleteFavoritesItem() {
        clearDB();
        onView(withId(R.id.etText)).perform(typeText("Hello!"));
        onView(withId(R.id.btnClearText)).perform(click());
        onData(instanceOf(Translator.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(0).onChildView(withId(R.id.adapterIwFavorites)).perform(click());
        onData(instanceOf(Translator.class))
                .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                .atPosition(0).perform(click());
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withId(R.id.btnClearFavorites)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        rotateScreen();
        onView(withId(R.id.pager)).perform(swipeRight());
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

    private void clearDB() {
        DataBase db = new DataBase(mActivityRule.getActivity());
        db.deleteAll();
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mActivityRule.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = mActivityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
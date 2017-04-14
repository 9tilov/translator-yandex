package com.moggot.mytranslator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.moggot.mytranslator.fragments.TranslatorFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslationDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOn extends State {

    private static final String LOG_TAG = "TranslationOn";

    private Fragment parentFragment;

    public TranslationOn(Fragment parentFragment, Long translatorID) {
        super(parentFragment);
        this.parentFragment = parentFragment;
        FragmentTransaction ft = parentFragment.getChildFragmentManager().beginTransaction();
        ft.replace(R.id.root_frame, TranslatorFragment.newInstance(translatorID), Consts.TAG_FRAGMENT_TRANSLATOR);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commitAllowingStateLoss();

        parentFragment.getChildFragmentManager().executePendingTransactions();

    }

    public void show(Translator translator) {
        super.show(translator);

        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null || fragment.getView() == null)
            return;

        DataBase db = new DataBase(parentFragment.getContext());
        Translator foundRecord = db.findRecord(translator);

        if (foundRecord == null) {
            if (!isNetworkAvailable()) {
                TextView tvTranslator = (TextView) fragment.getView().findViewById(R.id.tvDetails);
                tvTranslator.setText(parentFragment.getContext().getString(R.string.connection_error));
                tvTranslator.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                tvTranslator.setGravity(Gravity.CENTER);
                return;
            }

            TranslationTask translationTask = new TranslationTask(parentFragment);
            translationTask.execute(translator);
            DictionaryTask dictionaryTask = new DictionaryTask(parentFragment);
            dictionaryTask.execute(translator);
        } else {
            translator.setTranslator(foundRecord);
        }

        TranslatorData translatorData = new TranslatorData();
        Display display = new TranslationDisplay(fragment, translatorData);
        translatorData.setTranslator(translator);
        display.display();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) parentFragment.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
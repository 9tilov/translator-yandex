package com.moggot.mytranslator.state;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.translate.DictionaryTask;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.translate.TranslationTask;
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

    public TranslationOn(Fragment parentFragment) {
        super(parentFragment);
        this.parentFragment = parentFragment;
        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null) {
            FragmentTransaction ft = parentFragment.getChildFragmentManager().beginTransaction();
            ft.replace(R.id.root_frame, TranslatorFragment.newInstance(), Consts.TAG_FRAGMENT_TRANSLATOR);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            parentFragment.getChildFragmentManager().executePendingTransactions();
        }

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
                TextView tvError = (TextView) fragment.getView().findViewById(R.id.tvErrorConnection);
                tvError.setVisibility(View.VISIBLE);
                TextView tvNoInternet = (TextView) fragment.getView().findViewById(R.id.tvNoInternet);
                tvNoInternet.setVisibility(View.VISIBLE);
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
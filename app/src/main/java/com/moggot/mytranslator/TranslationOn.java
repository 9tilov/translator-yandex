package com.moggot.mytranslator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
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

    public TranslationOn(Context context) {
        super(context);
        Fragment fragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null) {
            FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root_frame, TranslatorFragment.newInstance(), Consts.TAG_FRAGMENT_TRANSLATOR);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            ((FragmentActivity) context).getSupportFragmentManager().executePendingTransactions();
        }
    }

    public void show(Translator translator) {
        super.show(translator);
        TranslatorData translatorData = new TranslatorData();
        Fragment fragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null)
            return;
        View view = fragment.getView();
        if (view == null)
            return;
        if (!isNetworkAvailable()) {
            TextView tvTranslator = (TextView) fragment.getView().findViewById(R.id.tvDetails);
            tvTranslator.setText(context.getString(R.string.connection_error));
            tvTranslator.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            tvTranslator.setGravity(Gravity.CENTER);
            return;
        }

        Display display = new TranslationDisplay(context, view, translatorData);
        DataBase db = new DataBase(context);
        Translator foundRecord = db.findRecord(translator);

        if (foundRecord == null) {
            TranslationTask translationTask = new TranslationTask(context);
            translationTask.execute(translator);
            DictionaryTask dictionaryTask = new DictionaryTask(context);
            dictionaryTask.execute(translator);
        } else {
            translator.setTranslator(foundRecord);
        }

        translatorData.setTranslator(translator);
        display.display();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
package com.moggot.mytranslator;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.moggot.mytranslator.fragments.FragmentHistory;
import com.moggot.mytranslator.observer.ActivityDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOff extends State {

    private static final String LOG_TAG = "TranslationOff";

    public TranslationOff(Context context) {
        super(context);

        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment fragment = new FragmentHistory();
        ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_HISTORY);
        ft.commit();
        ((Activity) context).getFragmentManager().executePendingTransactions();
    }

    @Override
    public void show(Translator translator) {
        TranslatorData translatorData = new TranslatorData();
        Display display = new ActivityDisplay(context, translatorData);
        translatorData.setTranslator(translator);
        display.display();
    }
}

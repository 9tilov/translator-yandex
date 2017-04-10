package com.moggot.mytranslator;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.HistoryDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOff extends State {

    private static final String LOG_TAG = "TranslationOff";

    public TranslationOff(Context context) {
        super(context);
        Fragment fragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        if (fragment == null) {
            FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root_frame, HistoryFragment.newInstance(), Consts.TAG_FRAGMENT_HISTORY);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            ((Activity) context).getFragmentManager().executePendingTransactions();
        }
    }

    public void show(Translator translator) {
        super.show(translator);
        TranslatorData translatorData = new TranslatorData();
        Fragment fragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        if (fragment == null)
            return;
        View view = fragment.getView();
        if (view == null)
            return;
        Display display = new HistoryDisplay(context, view, translatorData);
        translatorData.setTranslator(translator);
        display.display();
    }
}

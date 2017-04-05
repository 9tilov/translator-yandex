package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;

import com.moggot.mytranslator.fragments.TranslationFragment;
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
        Fragment fragment = new TranslationFragment();
        FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
        ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_TRANSLATOR);
        ft.commit();
        ((Activity) context).getFragmentManager().executePendingTransactions();

    }

    @Override
    public void show(Translator translator) {
        DataBase db = new DataBase(context);
        Translator foundRecord = db.findRecord(translator);
        if (foundRecord == null) {
            TranslationTask translationTask = new TranslationTask(context);
            translationTask.execute(translator);
            DictionaryTask dictionaryTask = new DictionaryTask(context);
            dictionaryTask.execute(translator);
        } else {
            Fragment translatorFragment = ((Activity) context).getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
            if (translatorFragment != null && translatorFragment.isVisible()) {
                Translator.getInstance().setTranslator(foundRecord);
                TranslatorData translatorData = new TranslatorData();
                Display traslatorDisplay = new TranslationDisplay(context, translatorFragment.getView(), translatorData);
                translatorData.setTranslator(foundRecord);
                traslatorDisplay.display();
            }
        }
    }
}

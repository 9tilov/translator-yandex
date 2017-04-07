package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

import com.moggot.mytranslator.fragments.FragmentTranslator;
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
        Fragment fragment = new FragmentTranslator();
        FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
        ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_TRANSLATOR);
        ft.commit();
        ((Activity) context).getFragmentManager().executePendingTransactions();
    }

    @Override
    public void show(Translator translator) {
        TranslatorData translatorData = new TranslatorData();
        Fragment fragment = ((Activity) context).getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        Display display = new TranslationDisplay(context, fragment.getView(), translatorData);

        if (translator.getText().isEmpty())
            return;
        DataBase db = new DataBase(context);
        Translator foundRecord = db.findRecord(translator);

        if (foundRecord == null) {
            TranslationTask translationTask = new TranslationTask(context);
            translationTask.execute(translator);
            DictionaryTask dictionaryTask = new DictionaryTask(context);
            dictionaryTask.execute(translator);
            translatorData.setTranslator(translator);
        } else {
            translatorData.setTranslator(foundRecord);
        }
        display.display();
    }
}
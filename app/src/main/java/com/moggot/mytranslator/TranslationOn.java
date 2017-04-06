package com.moggot.mytranslator;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.moggot.mytranslator.fragments.FragmentTranslator;
import com.moggot.mytranslator.observer.ActivityDisplay;
import com.moggot.mytranslator.observer.Display;
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
        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_TRANSLATOR);
        ft.commit();
        ((Activity) context).getFragmentManager().executePendingTransactions();
    }

    @Override
    public void show(Translator translator) {
        TranslatorData translatorData = new TranslatorData();
        Display display = new ActivityDisplay(context, translatorData);

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
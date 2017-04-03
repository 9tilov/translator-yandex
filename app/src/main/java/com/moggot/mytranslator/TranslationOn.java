package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

import com.moggot.mytranslator.fragments.TranslationFragment;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOn extends State {

    public TranslationOn(Context context) {
        super(context);

        if (!isTranslationStarted) {
            Fragment fragment = new TranslationFragment();
            FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
            ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_TRANSLATOR);
            ft.commit();
            isTranslationStarted = true;
        }
    }

    @Override
    public void show(Translator translator) {
        TranslationTask task = new TranslationTask(context);
        task.execute(translator);
    }
}

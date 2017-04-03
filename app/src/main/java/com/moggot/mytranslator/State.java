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

public abstract class State {

    protected Context context;
    protected boolean isTranslationStarted = false;

    public State(Context context) {
        this.context = context;
    }

    abstract void show(Translator translator);

}

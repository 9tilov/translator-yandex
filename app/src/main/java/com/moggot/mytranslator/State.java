package com.moggot.mytranslator;

import android.content.Context;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public abstract class State {

    protected Context context;

    public State(Context context) {
        this.context = context;
    }

    abstract void show(Translator translator);
}

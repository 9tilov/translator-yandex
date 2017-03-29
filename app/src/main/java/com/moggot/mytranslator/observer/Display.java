package com.moggot.mytranslator.observer;

import android.content.Context;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 29.03.17.
 */

public abstract class Display implements Observer {

    protected Context context;
    protected Translator translator;

    public Display(Context context) {
        this.context = context;
    }

    @Override
    public void update(Translator record) {
        this.translator = record;
        display();
    }

    abstract public void display();
}

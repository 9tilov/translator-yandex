package com.moggot.mytranslator.state;

import android.content.Context;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslatorContext {

    private State state;
    private Translator translator;
    private Context context;

    public TranslatorContext(Context context, Translator translator) {
        this.context = context;
        this.translator = translator;
        this.state = null;
    }

    public Translator getTranslator() {
        return this.translator;
    }

    public Context getContext() {
        return context;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public void show() {
        this.state.show(translator);
    }
}

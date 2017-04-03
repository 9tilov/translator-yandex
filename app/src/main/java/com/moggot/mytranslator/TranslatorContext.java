package com.moggot.mytranslator;

import android.content.Context;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslatorContext {

    private State state;
    private Context context;
    private Translator translator;

    public TranslatorContext(Context context, Translator translator) {
        this.context = context;
        this.translator = translator;
        this.state = null;
    }

    public Context getAppContext() {
        return this.context;
    }

    public Translator getTranslator() {
        return this.translator;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public void show(Translator translator) {
        this.state.show(translator);
    }
}

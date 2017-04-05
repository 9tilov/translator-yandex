package com.moggot.mytranslator;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslatorContext {

    private State state;
    private Translator translator;

    public TranslatorContext(Translator translator) {
        this.translator = translator;
        this.state = null;
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

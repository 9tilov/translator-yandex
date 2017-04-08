package com.moggot.mytranslator;

import android.content.Context;

import com.moggot.mytranslator.observer.ActivityDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class State {

    protected Context context;

    public State(Context context) {
        this.context = context;
    }

    void show(Translator translator) {
        TranslatorData translatorData = new TranslatorData();
        Display display = new ActivityDisplay(context, translatorData);
        translatorData.setTranslator(translator);
        display.display();
    }
}

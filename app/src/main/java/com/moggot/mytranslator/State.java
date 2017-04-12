package com.moggot.mytranslator;

import android.support.v4.app.Fragment;

import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.RootFragmentDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class State {

    protected Fragment parentFragment;

    public State(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    void show(Translator translator){
        TranslatorData translatorData = new TranslatorData();
        if (parentFragment != null) {
            Display display = new RootFragmentDisplay(parentFragment, translatorData);
            translatorData.setTranslator(translator);
            display.display();
        }
    }
}

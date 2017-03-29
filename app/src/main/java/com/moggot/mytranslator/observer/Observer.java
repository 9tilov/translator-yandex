package com.moggot.mytranslator.observer;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 22.02.17.
 */

public interface Observer {

    void update(Translator record);
}

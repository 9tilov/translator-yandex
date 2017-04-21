package com.moggot.mytranslator.translation;

import android.support.v4.app.Fragment;

import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 19.04.17.
 */

public class TranslateManager {

    private DictionaryResponse dictionaryResponse;
    private TranslatorResponse translatorResponse;

    public TranslateManager(final Fragment parentFragment) {
        this.translatorResponse = new TranslatorResponse(parentFragment);
        this.dictionaryResponse = new DictionaryResponse(parentFragment);
    }

    public void translate(final Translator translator) {
        try {
            translatorResponse.translate(translator);
            dictionaryResponse.translate(translator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

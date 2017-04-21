package com.moggot.mytranslator.translate;

import com.moggot.mytranslator.translator.Translator;

import java.net.URLEncoder;

/**
 * Created by toor on 19.04.17.
 */

abstract class Translation {

    abstract void translate(final Translator translator) throws Exception;

    String getLangStr(Translator translator) throws Exception {
        String ENCODING = "UTF-8";
        return URLEncoder.encode(translator.getInputLanguage(), ENCODING) + URLEncoder.encode("-", ENCODING) + URLEncoder.encode(translator.getOutputLanguage(), ENCODING);
    }

}

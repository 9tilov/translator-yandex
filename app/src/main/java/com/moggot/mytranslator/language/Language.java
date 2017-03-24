package com.moggot.mytranslator.language;

import com.moggot.mytranslator.Consts;

/**
 * Lang - an enum of name codes supported by the Yandex API
 */

public class Language {

    private String name;
    private Consts.LANG_TYPE type;

    public Language(String name, Consts.LANG_TYPE type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Consts.LANG_TYPE getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Consts.LANG_TYPE type) {
        this.type = type;
    }
}


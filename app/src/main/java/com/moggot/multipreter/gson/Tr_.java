package com.moggot.multipreter.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс с данными тега "tr" в json файле для детального перевода (перевод примера)
 */
public class Tr_ {

    /**
     * Текст примера переведенного слова
     */
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * Получение текста примера переведенного слова
     */
    public String getText() {
        return text;
    }

    /**
     * Установка текста примера переведенного слова
     */
    public void setText(String text) {
        this.text = text;
    }

}

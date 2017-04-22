package com.moggot.mytranslator.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс с данными тега "mean" в json файле для детального перевода
 */
public class Mean {

    /**
     * Текст значения перевода
     */
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * Получение текста значения перевода
     */
    public String getText() {
        return text;
    }

    /**
     * Установка текста значения перевода
     */
    public void setText(String text) {
        this.text = text;
    }

}

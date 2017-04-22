package com.moggot.mytranslator.gson;

/**
 * Created by toor on 19.04.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс с данными тега "syn" в json файле для детального перевода
 */
public class Syn {

    /**
     * Текст синонима перевода
     */
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * Часть речи синонима
     */
    @SerializedName("pos")
    @Expose
    private String pos;

    /**
     * Род слова
     */
    @SerializedName("gen")
    @Expose
    private String gen;

    /**
     * Получение текста синонима перевода
     */
    public String getText() {
        return text;
    }

    /**
     * Установка текста синонима перевода
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Получение части речи синонима
     */
    public String getPos() {
        return pos;
    }

    /**
     * Установка части речи синонима
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * Получение рода слова
     */
    public String getGen() {
        return gen;
    }

    /**
     * Установка рода слова
     */
    public void setGen(String gen) {
        this.gen = gen;
    }

}
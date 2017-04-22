package com.moggot.mytranslator.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс с данными тега "def" в json файле для детального перевода
 */
public class Def {

    /**
     * Слово перевода
     */
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * Часть речи
     */
    @SerializedName("pos")
    @Expose
    private String pos;

    /**
     * Транскрипция
     */
    @SerializedName("ts")
    @Expose
    private String ts;

    /**
     * Список синонимов перервода
     */
    @SerializedName("tr")
    @Expose
    private List<Tr> tr = null;

    /**
     * Получение перевода
     */
    public String getText() {
        return text;
    }

    /**
     * Установка текста перевода
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Получение части речи
     */
    public String getPos() {
        return pos;
    }

    /**
     * Установка части речи
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * Получение транскрипции
     */
    public String getTs() {
        return ts;
    }

    /**
     * Установка транскрипции
     */
    public void setTs(String ts) {
        this.ts = ts;
    }

    /**
     * Получение списка синонимов перевода
     */
    public List<Tr> getTr() {
        return tr;
    }

    /**
     * Установка списка синонимов перевода
     */
    public void setTr(List<Tr> tr) {
        this.tr = tr;
    }

}

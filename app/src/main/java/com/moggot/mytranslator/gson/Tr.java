package com.moggot.mytranslator.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс с данными тега "tr" в json файле для детального перевода
 */
public class Tr {

    /**
     * Текст перевода
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
     * Род слова перевода
     */
    @SerializedName("gen")
    @Expose
    private String gen;

    /**
     * Список синонимов слова перевода
     */
    @SerializedName("syn")
    @Expose
    private List<Syn> syn = null;

    /**
     * Список значений слова перевода
     */
    @SerializedName("mean")
    @Expose
    private List<Mean> mean = null;

    /**
     * Список примеров переведенного слова
     */
    @SerializedName("ex")
    @Expose
    private List<Ex> ex = null;

    /**
     * Вид
     * совершенный
     * несовершенный
     */
    @SerializedName("asp")
    @Expose
    private String asp;

    /**
     * Получение текста перевода
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
     * Получение части речи перевода
     */
    public String getPos() {
        return pos;
    }

    /**
     * Установка части речи перевода
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

    /**
     * Получение списка синонимов перевода
     */
    public List<Syn> getSyn() {
        return syn;
    }

    /**
     * Установка списка синонимов перевода
     */
    public void setSyn(List<Syn> syn) {
        this.syn = syn;
    }

    /**
     * Получение списка значний перевода
     */
    public List<Mean> getMean() {
        return mean;
    }

    /**
     * Установка списка значений перевода
     */
    public void setMean(List<Mean> mean) {
        this.mean = mean;
    }

    /**
     * Получение списка примеров перевода
     */
    public List<Ex> getEx() {
        return ex;
    }

    /**
     * Установка списка примеров перевода
     */
    public void setEx(List<Ex> ex) {
        this.ex = ex;
    }

    /**
     * Получение вида
     */
    public String getAsp() {
        return asp;
    }

    /**
     * Установка вида
     */
    public void setAsp(String asp) {
        this.asp = asp;
    }

}
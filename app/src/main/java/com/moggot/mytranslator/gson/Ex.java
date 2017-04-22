package com.moggot.mytranslator.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс с данными тега "Ex" в json файле для детального перевода
 */
public class Ex {

    /**
     * Текст примера со словом перевода
     */
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * Список примеров переведенного текста
     */
    @SerializedName("tr")
    @Expose
    private List<Tr_> tr = null;

    /**
     * Получение текста примера со словом перевода
     */
    public String getText() {
        return text;
    }

    /**
     * Установка текста примера со словом перевода
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Получение списка примеров переведенного текста
     */
    public List<Tr_> getTr() {
        return tr;
    }

    /**
     * Установка списка примеров переведенного текста
     */
    public void setTr(List<Tr_> tr) {
        this.tr = tr;
    }

}
package com.moggot.multipreter.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс с данными в json файле для обычного перевода слова
 */
public class WordTranslator {

    /**
     * Код ответа сервера
     */
    @SerializedName("code")
    @Expose
    private Integer code;

    /**
     * Направление перевода
     */
    @SerializedName("lang")
    @Expose
    private String lang;

    /**
     * Список переводов слова
     */
    @SerializedName("text")
    @Expose
    private List<String> text = null;

    /**
     * Получение кода ответа сервера
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Установка кода ответа сервера
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Получение направления перевода
     */
    public String getLang() {
        return lang;
    }

    /**
     * Установка направления перевода
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Получение списка переводов слова
     */
    public List<String> getText() {
        return text;
    }

    /**
     * Установка списка переводов слова
     */
    public void setText(List<String> text) {
        this.text = text;
    }

}

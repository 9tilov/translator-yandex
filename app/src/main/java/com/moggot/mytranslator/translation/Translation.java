package com.moggot.mytranslator.translation;

import com.moggot.mytranslator.translator.Translator;

/**
 * Класс, использущий интерфейс стратегии и запускащий тот или иной алгоритм перевода
 */
public class Translation {

    /**
     * Алгоритм перевода
     */
    private TranslationAlgorithm algorithm;

    /**
     * Конструктор
     *
     * @param algorithm - алгоритм перевода
     */
    public Translation(TranslationAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Установка алгоритма перевода
     *
     * @param algorithm - алгоритм перевода
     */
    public void setAlgorithm(TranslationAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Перевод слова
     *
     * @param translator - транслятор
     */
    public void translate(final Translator translator) {
        algorithm.translate(translator);
    }
}

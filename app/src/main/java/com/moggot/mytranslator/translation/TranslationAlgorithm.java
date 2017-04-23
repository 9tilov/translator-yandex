package com.moggot.mytranslator.translation;

import com.moggot.mytranslator.translator.Translator;

/**
 * Интерфейс для стратегии перевода.
 * Класс, реализущий конкретную стратегию, должен реализовывать данный интерфейс
 * Класс {@link Translation} использует данный интерфейс для вызова конкретной стратегии перевода
 */
public interface TranslationAlgorithm {

    /**
     * Перевод слова
     *
     * @param translator - транслятор
     */
    void translate(final Translator translator);
}

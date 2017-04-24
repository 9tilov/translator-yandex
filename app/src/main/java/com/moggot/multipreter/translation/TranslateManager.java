package com.moggot.multipreter.translation;

import android.support.v4.app.Fragment;

import com.moggot.multipreter.translator.Translator;

/**
 * Класс, управляющий переводом
 * Здесь производятся запросы на обычный и детальный перевод слова.
 * Было принято решение использовать библиотеку Retrofit, а не пользоваться
 * AsyncTask'ом напрямую, так как она позволяет удобно работать с ответами
 * сервера. В данном случае ответ представляется в виде объекта, что значительно
 * упрощает парсинг json файла.
 */
public class TranslateManager {

    /**
     * Родительский фрагмент
     */
    private Fragment parentFragment;

    /**
     * Конструктор
     *
     * @param parentFragment - родительский фрагмент
     */
    public TranslateManager(final Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    /**
     * Перевод слова
     * @param translator - транслятор
     */
    public void translate(Translator translator) {
        Translation translation = new Translation(new TranslatorResponse(parentFragment));
        translation.translate(translator);
        translation.setAlgorithm(new DictionaryResponse(parentFragment));
        translation.translate(translator);
    }
}

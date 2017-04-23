package com.moggot.mytranslator.translation;

import android.support.v4.app.Fragment;

import com.moggot.mytranslator.translator.Translator;

/**
 * Класс, управляющий переводом
 * Здесь производятся запросы на обычный и детальный перевод слова.
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
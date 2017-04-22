package com.moggot.mytranslator.state;

import android.support.v4.app.Fragment;

import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.RootFragmentDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Абстрактный класс состояния переводчика
 * Реализовани паттерн "Состояние"
 * Состояние может быть либо Off, либо On
 * Off - перевод слова не осуществляется, на главном экране отображается история переводов
 * On - происходит перевод. Переводчик переходит в состояние On при наборе текста, либо
 * при загрузке слова из истории/избранного переводов.
 */
public abstract class State {

    /**
     * Родительский фрагмент
     */
    protected Fragment parentFragment;

    /**
     * Конструктор
     * @param parentFragment - родительский фрагмент
     */
    public State(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    /**
     * Отображение данных родительского фрагмента
     * @param translator - родительский фрагмент
     */
    void show(Translator translator){
        TranslatorData translatorData = new TranslatorData();
        if (parentFragment != null) {
            Display display = new RootFragmentDisplay(parentFragment, translatorData);
            translatorData.setTranslator(translator);
            display.display();
        }
    }
}

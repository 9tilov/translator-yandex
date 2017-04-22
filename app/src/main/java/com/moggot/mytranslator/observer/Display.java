package com.moggot.mytranslator.observer;

import android.content.Context;

import com.moggot.mytranslator.translator.Translator;

/**
 * Абстрактный класс для отображения данных, реализующий интерфейс {@link Observer}
 */
public abstract class Display implements Observer {

    /**
     * Контекст Activity
     */
    protected Context context;

    /**
     * Транслятор
     */
    protected Translator translator;

    /**
     * Конструктор класса
     *
     * @param context - контекст Activity
     */
    public Display(Context context) {
        this.context = context;
    }

    /**
     * Обновление наблдателя
     *
     * @param translator - транслятор
     */
    @Override
    public void update(Translator translator) {
        this.translator = translator;
    }

    /**
     * Абстрактный метод для отображения данных
     */
    abstract public void display();
}

package com.moggot.multipreter.observer;

import com.moggot.multipreter.translator.Translator;

/**
 * Абстрактный класс для отображения данных, реализующий интерфейс {@link Observer}
 */
public abstract class Display implements Observer {

    /**
     * Транслятор
     */
    protected Translator translator;

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

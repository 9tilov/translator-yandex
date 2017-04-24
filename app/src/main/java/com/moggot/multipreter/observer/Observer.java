package com.moggot.multipreter.observer;

import com.moggot.multipreter.translator.Translator;

/**
 * Интерфейс, с помощью которого наблюдатель получает оповещение
 * Реализован паттерн "Наблюдатель"
 */
public interface Observer {

    /**
     * Обновление наблюдателя
     * @param translator - транслятор
     */
    void update(Translator translator);
}

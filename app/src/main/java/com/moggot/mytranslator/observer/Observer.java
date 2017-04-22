package com.moggot.mytranslator.observer;

import com.moggot.mytranslator.translator.Translator;

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

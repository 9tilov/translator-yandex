package com.moggot.mytranslator.observer;

/**
 * Интерфейс, определяющий метода для добавления, удаления и оповещение наблюдателей
 */
public interface Observable {

    /**
     * Регистрация наблюдателя
     *
     * @param observer - наблюдатель
     */
    void registerObserver(Observer observer);

    /**
     * Удаление наблюдателя
     *
     * @param observer - наблюдатель
     */
    void removeObserver(Observer observer);

    /**
     * Оповещение всех наблюдателей
     */
    void notifyObservers();

}

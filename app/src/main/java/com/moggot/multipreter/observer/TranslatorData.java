package com.moggot.multipreter.observer;

import com.moggot.multipreter.translator.Translator;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс с данными транслятора, реализующий интерфейс {@link Observable}
 */
public class TranslatorData implements Observable {

    /**
     * Список наблюдателей
     */
    private List<Observer> observers;

    /**
     * Транслятор
     */
    private Translator translator;

    /**
     * Конструктор
     * Здесь выделяем память под наблюдателей
     */
    public TranslatorData() {
        observers = new LinkedList<>();
    }

    /**
     * Регистрация наблюдателя
     *
     * @param observer - регистрируемый наблюдатель
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Удаление наблюдателя
     *
     * @param observer - удаляемый наблюдатель
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Оповещение наблюдателей
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(translator);
        }
    }

    /**
     * Установка транслятора
     *
     * @param translator - транслятор
     */
    public void setTranslator(Translator translator) {
        this.translator = translator;
        notifyObservers();
    }
}

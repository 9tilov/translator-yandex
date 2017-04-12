package com.moggot.mytranslator.observer;

import com.moggot.mytranslator.translator.Translator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by toor on 22.02.17.
 */

public class TranslatorData implements Observable {

    private List<Observer> observers;

    private Translator record;

    public TranslatorData() {
        observers = new LinkedList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(record);
        }
    }

    public void setTranslator(Translator record) {
        this.record = record;
        notifyObservers();
    }
}

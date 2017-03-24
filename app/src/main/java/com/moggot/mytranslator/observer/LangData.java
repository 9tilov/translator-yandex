package com.moggot.mytranslator.observer;

import com.moggot.mytranslator.language.Language;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by toor on 22.02.17.
 */

public class LangData implements Observable {

    private List<Observer> observers;

    private Language language;

    public LangData() {
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
            observer.update(language);
        }
    }

    public void setLanguage(Language language) {
        this.language = language;
        notifyObservers();
    }
}

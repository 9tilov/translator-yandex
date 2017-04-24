package com.moggot.multipreter.state;

import android.content.Context;

import com.moggot.multipreter.translator.Translator;

/**
 * Класс контекста транслятора
 */
public class TranslatorContext {

    /**
     * Состояние транслятора
     */
    private State state;

    /**
     * Транслятор
     */
    private Translator translator;

    /**
     * Контекст Activity
     */
    private Context context;

    /**
     * Конструктор
     *
     * @param context    - контекст Activity
     * @param translator - транслятор
     */
    public TranslatorContext(Context context, Translator translator) {
        this.context = context;
        this.translator = translator;
        this.state = null;
    }

    /**
     * Получение транслятора
     *
     * @return транслятор
     */
    public Translator getTranslator() {
        return this.translator;
    }

    /**
     * Получение контекста
     *
     * @return контекст Activity
     */
    public Context getContext() {
        return context;
    }

    /**
     * Установка состояния
     *
     * @param state - состояние транслятора
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Получение состояния
     *
     * @return state - состояние транслятора
     */
    public State getState() {
        return this.state;
    }

    /**
     * Отображение данных в зависимости от текущего состояния транслятора
     */
    public void show() {
        this.state.show(translator);
    }
}

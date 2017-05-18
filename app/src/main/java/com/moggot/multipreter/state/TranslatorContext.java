package com.moggot.multipreter.state;

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
     * Конструктор
     *
     * @param translator - транслятор
     */
    public TranslatorContext(Translator translator) {
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

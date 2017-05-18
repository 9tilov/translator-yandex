package com.moggot.multipreter.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.multipreter.conversation.LanguageConvertor;
import com.moggot.multipreter.LangSharedPreferences;
import com.moggot.multipreter.R;

/**
 * Класс для отображения данных на родительском фрагменте {@link com.moggot.multipreter.fragments.RootFragment}
 */
public class RootFragmentDisplay extends Display {

    private static final String LOG_TAG = RootFragmentDisplay.class.getSimpleName();

    /**
     * Фрагмент, в котором необходимо отобразить данные
     */
    private Fragment fragment;

    /**
     * Преобразование кодов языков в названия
     */
    private LanguageConvertor languageConvertor;

    /**
     * Конструктор
     *
     * @param fragment       - фрагмент, в котором необходимо отобразить данные
     * @param translatorData - данные транслятора
     */
    public RootFragmentDisplay(Fragment fragment, TranslatorData translatorData) {
        this.fragment = fragment;
        this.languageConvertor = new LanguageConvertor(fragment.getResources());
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        try {
            displayInputLang();
            displayOutputLang();
            displayClearButton();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отображение входного языка
     */
    private void displayInputLang() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");
        ((TextView) fragment.getView().findViewById(R.id.tvInputLang)).setText(languageConvertor.langCodeToLangName(translator.getInputLanguage()));
        LangSharedPreferences.saveInputLanguage(fragment.getContext(), translator.getInputLanguage());
    }

    /**
     * Отображение выходного языка
     */
    private void displayOutputLang() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        ((TextView) fragment.getView().findViewById(R.id.tvOutputLang)).setText(languageConvertor.langCodeToLangName(translator.getOutputLanguage()));
        LangSharedPreferences.saveOutputLanguage(fragment.getContext(), translator.getOutputLanguage());
    }

    /**
     * Отображение кнопки очистки поля ввода текста
     */
    private void displayClearButton() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        if (translator.getText().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearText)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearText)).setVisibility(View.VISIBLE);
    }
}

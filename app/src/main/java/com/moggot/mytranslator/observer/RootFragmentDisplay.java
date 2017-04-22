package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;

/**
 * Класс для отображения данных на родительском фрагменте {@link com.moggot.mytranslator.fragments.RootFragment}
 */
public class RootFragmentDisplay extends Display {

    private static final String LOG_TAG = "RootFragmentDisplay";

    /**
     * Фрагмент, в котором необходимо отобразить данные
     */
    private Fragment fragment;

    /**
     * Конструктор
     *
     * @param fragment       - фрагмент, в котором необходимо отобразить данные
     * @param translatorData - данные транслятора
     */
    public RootFragmentDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        displayInputLang();
        displayOutputLang();
        displayClearButton();
    }

    /**
     * Отображение входного языка
     */
    private void displayInputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) fragment.getView().findViewById(R.id.tvInputLang)).setText(conversation.getLongLangName(translator.getInputLanguage()));
        LangSharedPreferences.saveInputLanguage(context, translator.getInputLanguage());
    }

    /**
     * Отображение выходного языка
     */
    private void displayOutputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) fragment.getView().findViewById(R.id.tvOutputLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
        LangSharedPreferences.saveOutputLanguage(context, translator.getOutputLanguage());
    }

    /**
     * Отображение кнопки очистки поля ввода текста
     */
    private void displayClearButton() {
        if (translator.getText().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearText)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearText)).setVisibility(View.VISIBLE);
    }
}

package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.LangSharedPreferences;

/**
 * Класс для отображения данных адаптера списка входных языков
 */
public class AdapterInputLanguageDisplay extends Display {

    /**
     * Родительское view адаптера
     */
    private View view;

    /**
     * Конструктор
     *
     * @param context        - контекст Activity
     * @param view           - view адаптера
     * @param translatorData - данные транслятора
     */
    public AdapterInputLanguageDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        displayLang();
        displayCheck();
    }

    /**
     * Отображение языка
     */
    private void displayLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) view.findViewById(R.id.tvLang)).setText(conversation.getLongLangName(translator.getInputLanguage()));
    }

    /**
     * Отображение галочки, выбран данный язык или нет
     */
    private void displayCheck() {
        String inputLang = LangSharedPreferences.loadInputLanguage(context);
        if (translator.getInputLanguage().equals(inputLang))
            ((RadioButton) view.findViewById(R.id.check)).setChecked(true);
        else
            ((RadioButton) view.findViewById(R.id.check)).setChecked(false);
    }
}

package com.moggot.multipreter.observer;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moggot.multipreter.Conversation;
import com.moggot.multipreter.LangSharedPreferences;
import com.moggot.multipreter.R;

/**
 * Класс для отображения данных адаптера списка выходных языков
 */
public class AdapterOutputLanguageDisplay extends Display {

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
    public AdapterOutputLanguageDisplay(Context context, View view, TranslatorData translatorData) {
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
        ((TextView) view.findViewById(R.id.tvLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
    }

    /**
     * Отображение галочки, выбран данный язык или нет
     */
    private void displayCheck() {
        String outputLang = LangSharedPreferences.loadOutputLanguage(context);
        if (translator.getOutputLanguage().equals(outputLang))
            ((RadioButton) view.findViewById(R.id.check)).setChecked(true);
        else
            ((RadioButton) view.findViewById(R.id.check)).setChecked(false);
    }
}

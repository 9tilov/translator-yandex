package com.moggot.multipreter.observer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moggot.multipreter.R;

/**
 * Класс для отображения данных адаптера списка избранных слов
 */
public class AdapterFavoritesDisplay extends Display {

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
    public AdapterFavoritesDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        displayText();
        displayTranslation();
        displayFavoritesFlag();
        displayInputLang();
        displayOutputLang();
    }

    /**
     * Отображение текста, который нужно перевести
     */
    private void displayText() {
        ((TextView) view.findViewById(R.id.adapterTvText)).setText(translator.getText());
    }

    /**
     * Отображение текста перевода
     */
    private void displayTranslation() {
        ((TextView) view.findViewById(R.id.adapterTvTranslation)).setText(translator.getTranslation());
    }

    /**
     * Отображение флага, отвечащего, избранное слово или нет
     * Флаг всегда активен, так как это список избранных слов
     */
    private void displayFavoritesFlag() {
        ((ImageView) view.findViewById(R.id.adapterIwFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
    }

    /**
     * Отображение входного языка
     */
    private void displayInputLang() {
        ((TextView) view.findViewById(R.id.adapterTvInputLang)).setText(translator.getInputLanguage());
    }

    /**
     * Отображение выходного языка
     */
    private void displayOutputLang() {
        ((TextView) view.findViewById(R.id.adapterTvOutputLang)).setText(translator.getOutputLanguage());
    }
}

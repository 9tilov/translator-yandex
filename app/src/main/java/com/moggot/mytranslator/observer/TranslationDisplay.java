package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.moggot.mytranslator.R;

/**
 * Класс для отображения данных перевода
 */
public class TranslationDisplay extends Display {

    private static final String LOG_TAG = "TranslationDisplay";

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
    public TranslationDisplay(Fragment fragment, TranslatorData translatorData) {
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
        dislpayTranslation();
        displayFavorites();
        displayDetails();

    }

    /**
     * Отображение перевода
     */
    private void dislpayTranslation() {
        (fragment.getView().findViewById(R.id.rlTranslation)).setVisibility(View.VISIBLE);
        (fragment.getView().findViewById(R.id.llError)).setVisibility(View.GONE);

        ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setText(translator.getTranslation());
    }

    /**
     * Отображение флага Избранное
     */
    private void displayFavorites() {
        if (translator.getIsFavorites())
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
        else
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_border_24px);
    }

    /**
     * Отображение детального перевода
     */
    private void displayDetails() {
        ScrollView scrollViewDetails = (ScrollView) fragment.getView().findViewById(R.id.scrollDetails);
        ScrollView scrollViewTranslation = ((ScrollView) fragment.getView().findViewById(R.id.scrollTranslation));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollViewTranslation.getLayoutParams();
        if (!translator.getDetails().isEmpty()) {
            scrollViewDetails.setVisibility(View.VISIBLE);
            params.height = 0;
            ((TextView) fragment.getView().findViewById(R.id.tvDetails)).setText(translator.getDetails());
        } else {
            scrollViewDetails.setVisibility(View.INVISIBLE);
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.getContext().getResources().getDimension(R.dimen.text_size_dictionary));
        }
    }
}

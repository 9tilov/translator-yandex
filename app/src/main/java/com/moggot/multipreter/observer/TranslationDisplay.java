package com.moggot.multipreter.observer;

import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.moggot.multipreter.R;
import com.moggot.multipreter.conversation.StringEscaper;
import com.moggot.multipreter.conversation.TranslationColorizer;

/**
 * Класс для отображения данных перевода
 */
public class TranslationDisplay extends Display {

    private static final String LOG_TAG = TranslationDisplay.class.getSimpleName();

    /**
     * Фрагмент, в котором необходимо отобразить данные
     */
    private Fragment fragment;

    /**
     * Конвертер для украшения перевода
     */
    private TranslationColorizer converter;

    /**
     * Конструктор
     *
     * @param fragment       - фрагмент, в котором необходимо отобразить данные
     * @param translatorData - данные транслятора
     */
    public TranslationDisplay(Fragment fragment, TranslatorData translatorData) {
        this.fragment = fragment;
        this.converter = new TranslationColorizer(fragment.getContext());

        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        try {
            dislpayTranslation();
            displayFavorites();
            displayDetailedTranslation();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отображение перевода
     */
    private void dislpayTranslation() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        (fragment.getView().findViewById(R.id.rlTranslation)).setVisibility(View.VISIBLE);
        (fragment.getView().findViewById(R.id.llError)).setVisibility(View.GONE);

        String translation = StringEscaper.escapeResult(translator.getTranslation());
        ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setText(translation);
    }

    /**
     * Отображение флага Избранное
     */
    private void displayFavorites() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        if (translator.getIsFavorites())
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
        else
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_border_24px);
    }

    /**
     * Отображение детального перевода
     */
    private void displayDetailedTranslation() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        SpannableStringBuilder details = converter.getDecoratedDetails(translator);
        if (details.length() > 0)
            showDetails(details);
        else
            hideDetails();
    }

    private void showDetails(SpannableStringBuilder details) throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        ScrollView scrollViewDetails = (ScrollView) fragment.getView().findViewById(R.id.scrollDetails);
        ScrollView scrollViewTranslation = ((ScrollView) fragment.getView().findViewById(R.id.scrollTranslation));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollViewTranslation.getLayoutParams();
        scrollViewDetails.setVisibility(View.VISIBLE);
        params.height = 0;

        ((TextView) fragment.getView().findViewById(R.id.tvDetails)).setText(details);
    }

    private void hideDetails() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");

        ScrollView scrollViewDetails = (ScrollView) fragment.getView().findViewById(R.id.scrollDetails);
        ScrollView scrollViewTranslation = ((ScrollView) fragment.getView().findViewById(R.id.scrollTranslation));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollViewTranslation.getLayoutParams();
        scrollViewDetails.setVisibility(View.INVISIBLE);
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;

        ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.getContext().getResources().getDimension(R.dimen.text_size_dictionary));
    }
}

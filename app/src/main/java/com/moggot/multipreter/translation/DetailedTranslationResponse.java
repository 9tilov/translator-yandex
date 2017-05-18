package com.moggot.multipreter.translation;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.Gson;
import com.moggot.multipreter.api.ApiKeys;
import com.moggot.multipreter.App;
import com.moggot.multipreter.Consts;
import com.moggot.multipreter.R;
import com.moggot.multipreter.conversation.StringEscaper;
import com.moggot.multipreter.gson.WordDetailedTranslation;
import com.moggot.multipreter.observer.Display;
import com.moggot.multipreter.observer.TranslationDisplay;
import com.moggot.multipreter.observer.TranslatorData;
import com.moggot.multipreter.translator.Translator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Класс запроса детального перевода
 */
public class DetailedTranslationResponse implements TranslationAlgorithm {

    private static final String LOG_TAG = DetailedTranslationResponse.class.getSimpleName();

    /**
     * Фрагмент, который отображает перевод
     */
    private Fragment translatorFragment;

    /**
     * {@link ProgressBar} для отображения процесса получения данных с сервера
     */
    private ProgressBar progressBar;

    /**
     * Конструктор
     * Здесь получаем фрагмент, который отображает перевод и контекст Activity
     * Так же запускаем {@link ProgressBar}
     *
     * @param parentFragment - родительский фрагмент
     */
    public DetailedTranslationResponse(final Fragment parentFragment) throws NullPointerException {

        if (parentFragment == null)
            throw new NullPointerException("parentFragment is null");

        translatorFragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        try {
            progressBar = (ProgressBar) translatorFragment.getView().findViewById(R.id.spin_kit);
            ThreeBounce threeBounce = new ThreeBounce();
            progressBar.setIndeterminateDrawable(threeBounce);
            progressBar.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Детальный перевод слова
     *
     * @param translator - транслятор
     */
    @Override
    public void translate(final Translator translator) {
        String langDirection = translator.getInputLanguage() + "-" + translator.getOutputLanguage();
        String text = StringEscaper.escapeResponse(translator.getText());
        App.getDetailedTranslationApi().getDetails(ApiKeys.YANDEX_DICTIONARY_API_KEY, text, langDirection).enqueue(new Callback<WordDetailedTranslation>() {
            @Override
            public void onResponse(Call<WordDetailedTranslation> call, Response<WordDetailedTranslation> response) {

                if (response.body() == null) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (response.isSuccessful()) {
                    WordDetailedTranslation wordDetailedTranslation = response.body();
                    String result = new Gson().toJson(wordDetailedTranslation.getDef());
                    translator.setDetails(result);

                    showDetailedTranslation(translator);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WordDetailedTranslation> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Отображение детального перевода на экран
     *
     * @param translator - транслятор
     */
    private void showDetailedTranslation(Translator translator) {
        TranslatorData translatorData = new TranslatorData();
        Display display = new TranslationDisplay(translatorFragment, translatorData);
        translatorData.setTranslator(translator);
        display.display();
    }
}

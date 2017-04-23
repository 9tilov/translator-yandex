package com.moggot.mytranslator.translation;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.Gson;
import com.moggot.mytranslator.api.ApiKeys;
import com.moggot.mytranslator.App;
import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.gson.WordDictionary;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslationDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Класс запроса детального перевода
 */
public class DictionaryResponse implements TranslationAlgorithm {

    private static final String LOG_TAG = "DictionaryResponse";

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
    public DictionaryResponse(final Fragment parentFragment) {

        if (parentFragment != null) {
            translatorFragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);

            if (translatorFragment != null && translatorFragment.getView() != null) {
                progressBar = (ProgressBar) translatorFragment.getView().findViewById(R.id.spin_kit);
                ThreeBounce threeBounce = new ThreeBounce();
                progressBar.setIndeterminateDrawable(threeBounce);
                progressBar.setVisibility(View.VISIBLE);
            }
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
        App.getYandexDictionaryApi().getDetails(ApiKeys.YANDEX_DICTIONARY_API_KEY, translator.getText(), langDirection).enqueue(new Callback<WordDictionary>() {
            @Override
            public void onResponse(Call<WordDictionary> call, Response<WordDictionary> response) {

                if (response.body() == null) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (response.isSuccessful()) {
                    if (translatorFragment != null) {
                        WordDictionary wordDictionary = response.body();
                        String result = new Gson().toJson(wordDictionary.getDef());
                        translator.setDetails(result);

                        TranslatorData translatorData = new TranslatorData();
                        Display display = new TranslationDisplay(translatorFragment, translatorData);
                        translatorData.setTranslator(translator);
                        display.display();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WordDictionary> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

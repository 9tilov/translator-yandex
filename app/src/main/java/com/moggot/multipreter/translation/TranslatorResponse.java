package com.moggot.multipreter.translation;

import android.support.v4.app.Fragment;

import com.moggot.multipreter.api.APIEror;
import com.moggot.multipreter.api.ApiKeys;
import com.moggot.multipreter.App;
import com.moggot.multipreter.Consts;
import com.moggot.multipreter.gson.WordTranslator;
import com.moggot.multipreter.observer.Display;
import com.moggot.multipreter.observer.NetworkConnectionError;
import com.moggot.multipreter.observer.TranslationDisplay;
import com.moggot.multipreter.observer.TranslatorData;
import com.moggot.multipreter.translator.Translator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Класс запроса обычного перевода
 */
public class TranslatorResponse implements TranslationAlgorithm {

    private static final String LOG_TAG = "TranslatorResponse";

    /**
     * Фрагмент, который отображает перевод
     */
    private Fragment translatorFragment;

    /**
     * Конструктор
     * Здесь получаем фрагмент, который отображает перевод и контекст Activity
     *
     * @param parentFragment - родительский фрагмент
     */
    public TranslatorResponse(final Fragment parentFragment) {

        if (parentFragment != null) {
            translatorFragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        }
    }

    /**
     * Обычный перевод слова
     *
     * @param translator - транслятор
     */
    @Override
    public void translate(final Translator translator) {
        String langDirection = translator.getInputLanguage() + "-" + translator.getOutputLanguage();
        App.getYandexTranslationApi().getTranslation(ApiKeys.YANDEX_API_KEY, translator.getText(), langDirection).enqueue(new Callback<WordTranslator>() {
            @Override
            public void onResponse(Call<WordTranslator> call, Response<WordTranslator> response) {

                if (response.body() == null)
                    return;

                if (response.isSuccessful()) {
                    if (translatorFragment != null) {
                        WordTranslator wordTranslator = response.body();
                        translator.setTranslation(wordTranslator.getText().get(0));

                        TranslatorData translatorData = new TranslatorData();
                        Display display = new TranslationDisplay(translatorFragment, translatorData);
                        translatorData.setTranslator(translator);
                        display.display();
                    }
                } else {
                    try {
                        APIEror.parseError(response.body().getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WordTranslator> call, Throwable t) {
                if (translatorFragment != null) {
                    TranslatorData translatorData = new TranslatorData();
                    Display display = new NetworkConnectionError(translatorFragment, translatorData);
                    display.display();
                }
            }
        });
    }
}

package com.moggot.mytranslator.translation;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.moggot.mytranslator.api.ApiKeys;
import com.moggot.mytranslator.App;
import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.gson.Def;
import com.moggot.mytranslator.gson.Ex;
import com.moggot.mytranslator.gson.Mean;
import com.moggot.mytranslator.gson.Syn;
import com.moggot.mytranslator.gson.Tr;
import com.moggot.mytranslator.gson.Tr_;
import com.moggot.mytranslator.gson.WordDictionary;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslationDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

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
     * Контекст Activity
     */
    private Context context;

    /**
     * Конструктор
     * Здесь получаем фрагмент, который отображает перевод и контекст Activity
     * Так же запускаем {@link ProgressBar}
     *
     * @param parentFragment - родительский фрагмент
     */
    public DictionaryResponse(final Fragment parentFragment) {

        if (parentFragment != null) {
            this.context = parentFragment.getContext();
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
                        String result = parse(wordDictionary);
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

    /**
     * Парсинг json  с детальным переводом слова
     *
     * @param wordDictionary - объект с данными детального перевода с файла json
     * @return строка форматтированным ответом
     */
    private String parse(WordDictionary wordDictionary) {
        StringBuilder strResult = new StringBuilder();
        List<Def> definitions = wordDictionary.getDef();
        if (definitions.isEmpty())
            return "";
        strResult.append(definitions.get(0).getText());
        String transcription = definitions.get(0).getTs();
        if (transcription != null) {
            strResult.append(" [");
            strResult.append(transcription);
            strResult.append("]\n");
        } else
            strResult.append("\n");
        for (Def definition : definitions) {
            String pos = definition.getPos();
            if (pos.equals(context.getString(R.string.noun)))
                pos = context.getString(R.string.noun_short);
            if (pos.equals(context.getString(R.string.verb)))
                pos = context.getString(R.string.verb_short);
            if (pos.equals(context.getString(R.string.adjective)))
                pos = context.getString(R.string.adjective_short);
            if (pos.equals(context.getString(R.string.conjunction)))
                pos = context.getString(R.string.conjunction_short);
            if (pos.equals(context.getString(R.string.preposition)))
                pos = context.getString(R.string.preposition_short);
            if (pos.equals(context.getString(R.string.adverb)))
                pos = context.getString(R.string.adverb_short);
            if (pos.equals(context.getString(R.string.pronoun)))
                pos = context.getString(R.string.pronoun_short);
            if (pos.equals(context.getString(R.string.particle)))
                pos = context.getString(R.string.particle_short);
            if (pos.equals(context.getString(R.string.participle)))
                pos = context.getString(R.string.participle_short);
            if (pos.equals(context.getString(R.string.interjection)))
                pos = context.getString(R.string.interjection_short);
            if (pos.equals(context.getString(R.string.numeral)))
                pos = context.getString(R.string.numeral_short);
            if (pos.equals(context.getString(R.string.predicative)))
                pos = context.getString(R.string.predicative_short);
            if (pos.equals(context.getString(R.string.invariant)))
                pos = context.getString(R.string.invariant_short);
            if (pos.equals(context.getString(R.string.parenthetic)))
                pos = context.getString(R.string.parenthetic_short);
            strResult.append(pos);
            strResult.append("\n");
            List<Tr> translations = definition.getTr();
            if (translations != null) {
                for (int i_tr = 0; i_tr < translations.size(); ++i_tr) {
                    StringBuilder synBuilder = new StringBuilder();
                    StringBuilder meanBuilder = new StringBuilder();
                    StringBuilder exBuilder = new StringBuilder();

                    List<Syn> synonyms = translations.get(i_tr).getSyn();
                    if (synonyms != null) {
                        for (int i_syn = 0; i_syn < synonyms.size(); ++i_syn) {
                            String synText = synonyms.get(i_syn).getText();
                            if (synText != null) {
                                synBuilder.append(synText);
                            }

                            String synGen = synonyms.get(i_syn).getGen();
                            if (synGen != null) {
                                synBuilder.append(" ");
                                synBuilder.append(synonyms.get(i_syn).getGen());
                            }
                            if (i_syn < synonyms.size() - 1)
                                synBuilder.append(", ");
                        }
                    }
                    List<Mean> meanings = translations.get(i_tr).getMean();
                    if (meanings != null) {
                        for (int i_mean = 0; i_mean < meanings.size(); ++i_mean) {
                            if (i_mean == 0)
                                meanBuilder.append("  (");
                            meanBuilder.append(meanings.get(i_mean).getText());
                            if (i_mean < meanings.size() - 1)
                                meanBuilder.append(", ");
                            else
                                meanBuilder.append(")");
                        }
                    }
                    List<Ex> explanations = translations.get(i_tr).getEx();
                    if (explanations != null) {
                        for (int i_ex = 0; i_ex < explanations.size(); ++i_ex) {
                            exBuilder.append("       ");
                            exBuilder.append(explanations.get(i_ex).getText());
                            exBuilder.append(" - \n");
                            List<Tr_> translations_ = explanations.get(i_ex).getTr();
                            if (translations_ == null)
                                continue;
                            for (int i_tr_ = 0; i_tr_ < translations_.size(); ++i_tr_) {
                                exBuilder.append("       ");
                                exBuilder.append(translations_.get(i_tr_).getText());
                                if (i_ex < explanations.size() - 1)
                                    exBuilder.append("\n");
                            }
                        }
                    }

                    StringBuilder strWord = new StringBuilder();
                    strWord.append(String.valueOf(i_tr + 1));
                    strWord.append(" ");
                    strWord.append(translations.get(i_tr).getText());
                    String strGen = translations.get(i_tr).getGen();

                    strResult.append("  ");
                    strResult.append(strWord.toString());

                    if (strGen != null && !strGen.isEmpty()) {
                        strResult.append(" ");
                        strResult.append(strGen);
                    }
                    if (!synBuilder.toString().isEmpty()) {
                        strResult.append(", ");
                        strResult.append(synBuilder.toString());
                    }
                    if (!meanBuilder.toString().isEmpty()) {
                        strResult.append("\n");
                        strResult.append("  ");
                        strResult.append(meanBuilder.toString());
                    }
                    if (!exBuilder.toString().isEmpty()) {
                        strResult.append("\n");
                        strResult.append(exBuilder.toString());
                    }

                    strResult.append("\n");

                }
            }

        }
        return strResult.toString();
    }
}

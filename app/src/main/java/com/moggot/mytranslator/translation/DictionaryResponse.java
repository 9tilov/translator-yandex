package com.moggot.mytranslator.translation;

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
 * Created by toor on 19.04.17.
 */

public class DictionaryResponse extends Translation {

    private static final String LOG_TAG = "DictionaryResponse";

    private Fragment translatorFragment;
    private ProgressBar progressBar;

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

    @Override
    public void translate(final Translator translator) throws Exception {
        App.getYandexDictionaryApi().getDetails(ApiKeys.YANDEX_DICTIONARY_API_KEY, translator.getText(), getLangStr(translator)).enqueue(new Callback<WordDictionary>() {
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

    private String parse(WordDictionary wordDictionary) {
        StringBuilder strResult = new StringBuilder();
        List<Def> definitions = wordDictionary.getDef();
        if (definitions.isEmpty())
            return "";
        strResult.append(definitions.get(0).getText());
        strResult.append(" [");
        strResult.append(definitions.get(0).getTs());
        strResult.append("]\n");
        for (Def definition : definitions) {
            String pos = definition.getPos();
            if (pos.equals(translatorFragment.getString(R.string.noun)))
                pos = translatorFragment.getString(R.string.noun_short);
            if (pos.equals(translatorFragment.getString(R.string.verb)))
                pos = translatorFragment.getString(R.string.verb_short);
            if (pos.equals(translatorFragment.getString(R.string.adjective)))
                pos = translatorFragment.getString(R.string.adjective_short);
            if (pos.equals(translatorFragment.getString(R.string.conjunction)))
                pos = translatorFragment.getString(R.string.conjunction_short);
            if (pos.equals(translatorFragment.getString(R.string.preposition)))
                pos = translatorFragment.getString(R.string.preposition_short);
            if (pos.equals(translatorFragment.getString(R.string.adverb)))
                pos = translatorFragment.getString(R.string.adverb_short);
            if (pos.equals(translatorFragment.getString(R.string.pronoun)))
                pos = translatorFragment.getString(R.string.pronoun_short);
            if (pos.equals(translatorFragment.getString(R.string.particle)))
                pos = translatorFragment.getString(R.string.particle_short);
            if (pos.equals(translatorFragment.getString(R.string.participle)))
                pos = translatorFragment.getString(R.string.participle_short);
            if (pos.equals(translatorFragment.getString(R.string.interjection)))
                pos = translatorFragment.getString(R.string.interjection_short);
            if (pos.equals(translatorFragment.getString(R.string.numeral)))
                pos = translatorFragment.getString(R.string.numeral_short);
            if (pos.equals(translatorFragment.getString(R.string.predicative)))
                pos = translatorFragment.getString(R.string.predicative_short);
            if (pos.equals(translatorFragment.getString(R.string.invariant)))
                pos = translatorFragment.getString(R.string.invariant_short);
            if (pos.equals(translatorFragment.getString(R.string.parenthetic)))
                pos = translatorFragment.getString(R.string.parenthetic_short);
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

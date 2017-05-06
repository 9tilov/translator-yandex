package com.moggot.multipreter.observer;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moggot.multipreter.R;
import com.moggot.multipreter.gson.Def;
import com.moggot.multipreter.gson.Ex;
import com.moggot.multipreter.gson.Mean;
import com.moggot.multipreter.gson.Syn;
import com.moggot.multipreter.gson.Tr;
import com.moggot.multipreter.gson.Tr_;
import com.moggot.multipreter.translator.Translator;

import java.lang.reflect.Type;
import java.util.List;

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
        try {
            dislpayTranslation();
            displayFavorites();
            displayDetails();
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

        String translation = parseTranslation(translator);
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
    private void displayDetails() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");
        ScrollView scrollViewDetails = (ScrollView) fragment.getView().findViewById(R.id.scrollDetails);
        ScrollView scrollViewTranslation = ((ScrollView) fragment.getView().findViewById(R.id.scrollTranslation));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollViewTranslation.getLayoutParams();

        Type type = new TypeToken<List<Def>>() {
        }.getType();
        List<Def> defs = new Gson().fromJson(translator.getDetails(), type);
        if (defs != null && !defs.isEmpty()) {
            SpannableStringBuilder result = parseDetails(defs);
            scrollViewDetails.setVisibility(View.VISIBLE);
            params.height = 0;
            ((TextView) fragment.getView().findViewById(R.id.tvDetails)).setText(result);
        } else {
            scrollViewDetails.setVisibility(View.INVISIBLE);
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.getContext().getResources().getDimension(R.dimen.text_size_dictionary));
        }
    }

    private String parseTranslation(Translator translator) {
        String translation = translator.getTranslation();
        translation = translation.replace("%3B", ";");
        translation = translation.replace("%2B", "+");
        return translation;
    }

    /**
     * Парсинг json  с детальным переводом слова
     *
     * @param definitions - список объектов с детальным переводом
     * @return распарсенный ответ
     */
    private SpannableStringBuilder parseDetails(List<Def> definitions) {
        SpannableStringBuilder strResult = new SpannableStringBuilder();
        strResult.append(definitions.get(0).getText());
        String transcription = definitions.get(0).getTs();
        if (transcription != null && !transcription.isEmpty()) {
            strResult.append(" ");
            transcription = "[" + transcription + "]";
            Spannable transcriptionSpannable = getSpannableString(transcription, R.color.text_tr);
            strResult.append(transcriptionSpannable);
            strResult.append("\n");
        } else
            strResult.append("\n");
        for (Def definition : definitions) {
            String pos = definition.getPos();
            if (pos != null && !pos.isEmpty()) {
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

                Spannable posSpannable = getSpannableString(pos, R.color.text_pos);
                strResult.append(posSpannable);
                strResult.append("\n");
            }
            List<Tr> translations = definition.getTr();
            if (translations != null) {
                for (int i_tr = 0; i_tr < translations.size(); ++i_tr) {
                    SpannableStringBuilder synBuilder = new SpannableStringBuilder();
                    SpannableStringBuilder meanBuilder = new SpannableStringBuilder();
                    SpannableStringBuilder exBuilder = new SpannableStringBuilder();

                    List<Syn> synonyms = translations.get(i_tr).getSyn();
                    if (synonyms != null) {
                        for (int i_syn = 0; i_syn < synonyms.size(); ++i_syn) {
                            String synText = synonyms.get(i_syn).getText();
                            if (synText != null && !synText.isEmpty()) {
                                Spannable synSpannable = getSpannableString(synText, R.color.text_syn);
                                synBuilder.append(synSpannable);
                            }

                            String synGen = synonyms.get(i_syn).getGen();
                            if (synGen != null && !synGen.isEmpty()) {
                                Spannable synGenSpannable = getSpannableString(synGen, R.color.text_gen);
                                synBuilder.append(" ");
                                synBuilder.append(synGenSpannable);
                            }
                            if (i_syn < synonyms.size() - 1) {
                                synBuilder.append(getSpannableString(", ", R.color.text_syn));
                            }
                        }
                    }
                    List<Mean> meanings = translations.get(i_tr).getMean();
                    if (meanings != null) {
                        for (int i_mean = 0; i_mean < meanings.size(); ++i_mean) {
                            String meanText = meanings.get(i_mean).getText();
                            if (meanText != null && !meanText.isEmpty()) {
                                if (i_mean == 0)
                                    meanBuilder.append(getSpannableString("  (", R.color.text_mean));
                                meanBuilder.append(getSpannableString(meanText, R.color.text_mean));
                                if (i_mean < meanings.size() - 1)
                                    meanBuilder.append(getSpannableString(", ", R.color.text_mean));
                                else
                                    meanBuilder.append(getSpannableString(")", R.color.text_mean));
                            }
                        }
                    }
                    List<Ex> explanations = translations.get(i_tr).getEx();
                    if (explanations != null) {
                        for (int i_ex = 0; i_ex < explanations.size(); ++i_ex) {
                            String exText = explanations.get(i_ex).getText();
                            if (exText != null && !exText.isEmpty()) {
                                exBuilder.append("       ");
                                exBuilder.append(getSpannableString(exText, R.color.text_ex));
                                exBuilder.append(getSpannableString(" - ", R.color.text_ex));
                                exBuilder.append("\n");
                                List<Tr_> translations_ = explanations.get(i_ex).getTr();
                                if (translations_ != null) {
                                    for (int i_tr_ = 0; i_tr_ < translations_.size(); ++i_tr_) {
                                        String translation_ = translations_.get(i_tr_).getText();
                                        if (translation_ != null && !translation_.isEmpty()) {
                                            exBuilder.append("       ");
                                            exBuilder.append(getSpannableString(translation_, R.color.text_ex));
                                            if (i_ex < explanations.size() - 1)
                                                exBuilder.append("\n");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (translations.size() > 1) {
                        Spannable numberSpannable = getSpannableString(String.valueOf(i_tr + 1), R.color.text_numbers);
                        strResult.append(numberSpannable);
                        strResult.append(" ");
                    } else {
                        strResult.append("  ");
                    }
                    String translation = translations.get(i_tr).getText();
                    if (translation != null && !translation.isEmpty()) {
                        Spannable translationSpannable = getSpannableString(translation, R.color.text_syn);
                        strResult.append(translationSpannable);
                    }

                    String gen = translations.get(i_tr).getGen();

                    if (gen != null && !gen.isEmpty()) {
                        strResult.append(" ");
                        Spannable genSpannable = getSpannableString(gen, R.color.text_gen);
                        strResult.append(genSpannable);
                    }
                    if (!synBuilder.toString().isEmpty()) {
                        strResult.append(", ");
                        strResult.append(synBuilder);
                    }
                    if (!meanBuilder.toString().isEmpty()) {
                        strResult.append("\n");
                        strResult.append("  ");
                        strResult.append(meanBuilder);
                    }
                    if (!exBuilder.toString().isEmpty()) {
                        strResult.append("\n");
                        strResult.append(exBuilder);
                    }

                    strResult.append("\n");
                }
            }
        }
        return strResult;
    }

    /**
     * Получение раскрашенной строки
     *
     * @param text      - обычная строка
     * @param colorCode - код цвета
     * @return раскрашенная строка
     */
    private Spannable getSpannableString(String text, int colorCode) {
        Spanned textSP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textSP = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            textSP = Html.fromHtml(text);
        }
        Spannable textSpannable = new SpannableString(textSP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(fragment.getContext(), colorCode)), 0, textSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            textSpannable.setSpan(new ForegroundColorSpan(fragment.getContext().getResources().getColor(colorCode)), 0, textSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return textSpannable;
    }

}

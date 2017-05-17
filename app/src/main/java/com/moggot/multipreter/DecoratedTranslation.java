package com.moggot.multipreter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * Класс украшенного перевода
 */
public class DecoratedTranslation {

    /**
     * Контекст
     */
    private Context context;

    /**
     * Строковый поток для детального перевода
     */
    private SpannableStringBuilder details;

    /**
     * Конструктор
     *
     * @param context - контекст
     */
    public DecoratedTranslation(Context context) {
        this.context = context;
        this.details = new SpannableStringBuilder();
    }

    /**
     * Получение модифицированного перевода
     *
     * @param translator - транслятор
     * @return модифицированный перевод
     */
    public String getDecoratedTranslation(Translator translator) {
        String translation = translator.getTranslation();
        translation = translation.replace("%3B", ";");
        translation = translation.replace("%2B", "+");
        return translation;
    }

    /**
     * Получение модифицированного детального перевода
     *
     * @param translator - транслятор
     * @return модифицированный детальный перевод
     */
    public SpannableStringBuilder getDecoratedDetails(Translator translator) {
        Type type = new TypeToken<List<Def>>() {
        }.getType();
        List<Def> definitions = new Gson().fromJson(translator.getDetails(), type);
        if (definitions != null && !definitions.isEmpty()) {
            addDefinition(definitions);
            addTranscription(definitions);
            for (Def definition : definitions) {
                addPartsOfSpeech(definition);
                List<Tr> translations = definition.getTr();
                if (translations != null) {
                    for (int i_tr = 0; i_tr < translations.size(); ++i_tr) {
                        addTranslationNumber(i_tr);
                        addTranslation(translations.get(i_tr));
                        addGender(translations.get(i_tr));
                        addSynonyms(translations.get(i_tr));
                        addMeaninig(translations.get(i_tr));
                        addExplanations(translations.get(i_tr));
                    }
                }
            }
        }
        return details;
    }

    /**
     * Добавление слова на языке оригинала
     *
     * @param definitions - список объектов с детальным переводом
     */
    private void addDefinition(List<Def> definitions) {
        details.append(definitions.get(0).getText());
    }

    /**
     * Добавление транскрипции
     *
     * @param definitions - список объектов с детальным переводом
     */
    private void addTranscription(List<Def> definitions) {
        SpannableStringBuilder synBuilder = new SpannableStringBuilder();
        String transcription = definitions.get(0).getTs();
        if (transcription != null && !transcription.isEmpty()) {
            details.append(" ");
            transcription = "[" + transcription + "]";
            Spannable transcriptionSpannable = stringToSpannableString(transcription, R.color.text_tr);
            details.append(transcriptionSpannable);
            details.append("\n");
        } else
            details.append("\n");
        if (!synBuilder.toString().isEmpty()) {
            details.append(", ");
            details.append(synBuilder);
        }

        details.append("\n");
    }

    /**
     * Добавление части речи
     *
     * @param definition - объект с детальным переводом
     */
    private void addPartsOfSpeech(Def definition) {
        String pos = definition.getPos();
        if (pos != null && !pos.isEmpty()) {
            Spannable posSpannable = stringToSpannableString(longToShortPartOfSpeech(pos), R.color.text_pos);
            details.append(posSpannable);
            details.append("\n");
        }
    }

    /**
     * Преобразование части речи в короткий вариант
     *
     * @param longStr - полное название части речи
     * @return укороченное название части речи
     */
    private String longToShortPartOfSpeech(String longStr) {
        if (longStr.equals(context.getString(R.string.noun)))
            return context.getString(R.string.noun_short);
        if (longStr.equals(context.getString(R.string.verb)))
            return context.getString(R.string.verb_short);
        if (longStr.equals(context.getString(R.string.adjective)))
            return context.getString(R.string.adjective_short);
        if (longStr.equals(context.getString(R.string.conjunction)))
            return context.getString(R.string.conjunction_short);
        if (longStr.equals(context.getString(R.string.preposition)))
            return context.getString(R.string.preposition_short);
        if (longStr.equals(context.getString(R.string.adverb)))
            return context.getString(R.string.adverb_short);
        if (longStr.equals(context.getString(R.string.pronoun)))
            return context.getString(R.string.pronoun_short);
        if (longStr.equals(context.getString(R.string.particle)))
            return context.getString(R.string.particle_short);
        if (longStr.equals(context.getString(R.string.participle)))
            return context.getString(R.string.participle_short);
        if (longStr.equals(context.getString(R.string.interjection)))
            return context.getString(R.string.interjection_short);
        if (longStr.equals(context.getString(R.string.numeral)))
            return context.getString(R.string.numeral_short);
        if (longStr.equals(context.getString(R.string.predicative)))
            return context.getString(R.string.predicative_short);
        if (longStr.equals(context.getString(R.string.invariant)))
            return context.getString(R.string.invariant_short);
        if (longStr.equals(context.getString(R.string.parenthetic)))
            return context.getString(R.string.parenthetic_short);
        else
            return "";
    }

    /**
     * Добавление порядкового номера к переводу
     *
     * @param number - порядковый номер варианта перевода
     */
    private void addTranslationNumber(int number) {
        Spannable numberSpannable = stringToSpannableString(String.valueOf(number + 1), R.color.text_numbers);
        details.append(numberSpannable);
        details.append(" ");
    }

    /**
     * Добавление варианта перевода
     *
     * @param translation - вариант перевода
     */
    private void addTranslation(Tr translation) {
        String translationStr = translation.getText();
        if (translationStr != null && !translationStr.isEmpty()) {
            Spannable translationSpannable = stringToSpannableString(translationStr, R.color.text_syn);
            details.append(translationSpannable);
        }
    }

    /**
     * Добавление рода
     *
     * @param translation - вариант перевода
     */
    private void addGender(Tr translation) {
        String gen = translation.getGen();
        if (gen != null && !gen.isEmpty()) {
            details.append(" ");
            Spannable genSpannable = stringToSpannableString(gen, R.color.text_gen);
            details.append(genSpannable);
        }
    }

    /**
     * Добавление синонимов
     *
     * @param translation - вариант перевода
     */
    private void addSynonyms(Tr translation) {
        SpannableStringBuilder synBuilder = new SpannableStringBuilder();
        List<Syn> synonyms = translation.getSyn();
        if (synonyms != null) {
            for (int i_syn = 0; i_syn < synonyms.size(); ++i_syn) {
                String synText = synonyms.get(i_syn).getText();
                if (synText != null && !synText.isEmpty()) {
                    Spannable synSpannable = stringToSpannableString(synText, R.color.text_syn);
                    synBuilder.append(synSpannable);
                }

                String synGen = synonyms.get(i_syn).getGen();
                if (synGen != null && !synGen.isEmpty()) {
                    Spannable synGenSpannable = stringToSpannableString(synGen, R.color.text_gen);
                    synBuilder.append(" ");
                    synBuilder.append(synGenSpannable);
                }
                if (i_syn < synonyms.size() - 1) {
                    synBuilder.append(stringToSpannableString(", ", R.color.text_syn));
                }
            }
        }

        if (!synBuilder.toString().isEmpty()) {
            details.append(", ");
            details.append(synBuilder);
        }
    }

    /**
     * Добавление перевода синонимов
     *
     * @param translation - вариант перевода
     */
    private void addMeaninig(Tr translation) {
        SpannableStringBuilder meanBuilder = new SpannableStringBuilder();
        List<Mean> meanings = translation.getMean();
        if (meanings != null) {
            for (int i_mean = 0; i_mean < meanings.size(); ++i_mean) {
                String meanText = meanings.get(i_mean).getText();
                if (meanText != null && !meanText.isEmpty()) {
                    if (i_mean == 0)
                        meanBuilder.append(stringToSpannableString("  (", R.color.text_mean));
                    meanBuilder.append(stringToSpannableString(meanText, R.color.text_mean));
                    if (i_mean < meanings.size() - 1)
                        meanBuilder.append(stringToSpannableString(", ", R.color.text_mean));
                    else
                        meanBuilder.append(stringToSpannableString(")", R.color.text_mean));
                }
            }
        }

        if (!meanBuilder.toString().isEmpty()) {
            details.append("\n");
            details.append("  ");
            details.append(meanBuilder);
        }
    }

    /**
     * Добавление примеров
     *
     * @param translation - вариант перевода
     */
    private void addExplanations(Tr translation) {
        SpannableStringBuilder exBuilder = new SpannableStringBuilder();
        List<Ex> explanations = translation.getEx();
        if (explanations != null) {
            for (int i_ex = 0; i_ex < explanations.size(); ++i_ex) {
                String exText = explanations.get(i_ex).getText();
                if (exText != null && !exText.isEmpty()) {
                    exBuilder.append("       ");
                    exBuilder.append(stringToSpannableString(exText, R.color.text_ex));
                    exBuilder.append(stringToSpannableString(" - ", R.color.text_ex));
                    exBuilder.append("\n");
                    List<Tr_> translations_ = explanations.get(i_ex).getTr();
                    if (translations_ != null) {
                        for (int i_tr_ = 0; i_tr_ < translations_.size(); ++i_tr_) {
                            String translation_ = translations_.get(i_tr_).getText();
                            if (translation_ != null && !translation_.isEmpty()) {
                                exBuilder.append("       ");
                                exBuilder.append(stringToSpannableString(translation_, R.color.text_ex));
                                if (i_ex < explanations.size() - 1)
                                    exBuilder.append("\n");
                            }
                        }
                    }
                }
            }
        }
        if (!exBuilder.toString().isEmpty()) {
            details.append("\n");
            details.append(exBuilder);
        }

        details.append("\n\n");
    }

    /**
     * Раскрашивание строки
     *
     * @param text      - обычная строка
     * @param colorCode - код цвета
     * @return раскрашенная строка
     */
    private Spannable stringToSpannableString(String text, int colorCode) {
        Spanned textSP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textSP = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            textSP = Html.fromHtml(text);
        }
        Spannable textSpannable = new SpannableString(textSP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorCode)), 0, textSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            textSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorCode)), 0, textSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return textSpannable;
    }
}

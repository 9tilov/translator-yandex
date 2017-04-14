package com.moggot.mytranslator.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.observer.AdapterInputLanguageDisplay;
import com.moggot.mytranslator.observer.AdapterOutputLanguageDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toor on 24.03.17.
 */

public class AdapterLanguage extends BaseAdapter {

    private static final String LOG_TAG = "AdapterLanguage";

    private Context context;
    private LayoutInflater inflater;
    private List<String> languages;
    private Consts.LANG_TYPE type;

    public AdapterLanguage(Context context, Consts.LANG_TYPE type) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.languages = new ArrayList<>();
        this.type = type;

        fillLangList();
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return languages.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return languages.get(position);
    }

    private String getLang(int position) {
        return ((String) getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.lang_item, parent, false);

        final String language = getLang(position);
        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay;
        Translator translator;
        if (type == Consts.LANG_TYPE.INPUT) {
            translator = new Translator(null, null, null, language, null, false, null);
            adapterDisplay = new AdapterInputLanguageDisplay(context, view, translatorData);
        } else {
            translator = new Translator(null, null, null, null, language, false, null);
            adapterDisplay = new AdapterOutputLanguageDisplay(context, view, translatorData);
        }

        translatorData.setTranslator(translator);
        adapterDisplay.display();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();

                if (type == Consts.LANG_TYPE.INPUT) {
                    String oppositeLang = LangSharedPreferences.loadOutputLanguage(context);
                    if (language.equals(oppositeLang)) {
                        String currentLanguage = LangSharedPreferences.loadInputLanguage(context);
                        LangSharedPreferences.saveOutputLanguage(context, currentLanguage);
                    }
                    LangSharedPreferences.saveInputLanguage(context, language);
                } else {
                    String oppositeLang = LangSharedPreferences.loadInputLanguage(context);
                    if (language.equals(oppositeLang)) {
                        String currentLanguage = LangSharedPreferences.loadOutputLanguage(context);
                        LangSharedPreferences.saveInputLanguage(context, currentLanguage);
                    }
                    LangSharedPreferences.saveOutputLanguage(context, language);
                }
                ((Activity) context).finish();

            }

        });
        return view;
    }

    private void fillLangList() {

        languages.add(context.getString(R.string.en_short));
        languages.add(context.getString(R.string.ru_short));
        languages.add(context.getString(R.string.xh_short));
        languages.add(context.getString(R.string.az_short));
        languages.add(context.getString(R.string.de_short));
        languages.add(context.getString(R.string.sq_short));
        languages.add(context.getString(R.string.am_short));
        languages.add(context.getString(R.string.ar_short));
        languages.add(context.getString(R.string.af_short));
        languages.add(context.getString(R.string.eu_short));
        languages.add(context.getString(R.string.ba_short));
        languages.add(context.getString(R.string.be_short));
        languages.add(context.getString(R.string.bn_short));
        languages.add(context.getString(R.string.bg_short));
        languages.add(context.getString(R.string.bs_short));
        languages.add(context.getString(R.string.cy_short));
        languages.add(context.getString(R.string.hu_short));
        languages.add(context.getString(R.string.vi_short));
        languages.add(context.getString(R.string.ht_short));
        languages.add(context.getString(R.string.gl_short));
        languages.add(context.getString(R.string.nl_short));
        languages.add(context.getString(R.string.mrj_short));
        languages.add(context.getString(R.string.el_short));
        languages.add(context.getString(R.string.ka_short));
        languages.add(context.getString(R.string.gu_short));
        languages.add(context.getString(R.string.da_short));
        languages.add(context.getString(R.string.he_short));
        languages.add(context.getString(R.string.yi_short));
        languages.add(context.getString(R.string.id_short));
        languages.add(context.getString(R.string.ga_short));
        languages.add(context.getString(R.string.it_short));
        languages.add(context.getString(R.string.is_short));
        languages.add(context.getString(R.string.es_short));
        languages.add(context.getString(R.string.kk_short));
        languages.add(context.getString(R.string.kn_short));
        languages.add(context.getString(R.string.ca_short));
        languages.add(context.getString(R.string.ky_short));
        languages.add(context.getString(R.string.zh_short));
        languages.add(context.getString(R.string.ko_short));
        languages.add(context.getString(R.string.la_short));
        languages.add(context.getString(R.string.lv_short));
        languages.add(context.getString(R.string.lt_short));
        languages.add(context.getString(R.string.lb_short));
        languages.add(context.getString(R.string.mg_short));
        languages.add(context.getString(R.string.ms_short));
        languages.add(context.getString(R.string.ml_short));
        languages.add(context.getString(R.string.mt_short));
        languages.add(context.getString(R.string.mk_short));
        languages.add(context.getString(R.string.mi_short));
        languages.add(context.getString(R.string.mr_short));
        languages.add(context.getString(R.string.mhr_short));
        languages.add(context.getString(R.string.de_short));
        languages.add(context.getString(R.string.ne_short));
        languages.add(context.getString(R.string.no_short));
        languages.add(context.getString(R.string.pa_short));
        languages.add(context.getString(R.string.pap_short));
        languages.add(context.getString(R.string.fa_short));
        languages.add(context.getString(R.string.pl_short));
        languages.add(context.getString(R.string.pt_short));
        languages.add(context.getString(R.string.ro_short));
        languages.add(context.getString(R.string.ceb_short));
        languages.add(context.getString(R.string.sr_short));
        languages.add(context.getString(R.string.si_short));
        languages.add(context.getString(R.string.sk_short));
        languages.add(context.getString(R.string.sl_short));
        languages.add(context.getString(R.string.sw_short));
        languages.add(context.getString(R.string.su_short));
        languages.add(context.getString(R.string.tg_short));
        languages.add(context.getString(R.string.th_short));
        languages.add(context.getString(R.string.tl_short));
        languages.add(context.getString(R.string.ta_short));
        languages.add(context.getString(R.string.tt_short));
        languages.add(context.getString(R.string.te_short));
        languages.add(context.getString(R.string.tr_short));
        languages.add(context.getString(R.string.udm_short));
        languages.add(context.getString(R.string.uz_short));
        languages.add(context.getString(R.string.uk_short));
        languages.add(context.getString(R.string.ur_short));
        languages.add(context.getString(R.string.fi_short));
        languages.add(context.getString(R.string.hi_short));
        languages.add(context.getString(R.string.hr_short));
        languages.add(context.getString(R.string.cs_short));
        languages.add(context.getString(R.string.sv_short));
        languages.add(context.getString(R.string.gd_short));
        languages.add(context.getString(R.string.et_short));
        languages.add(context.getString(R.string.eo_short));
        languages.add(context.getString(R.string.jv_short));
        languages.add(context.getString(R.string.ja_short));
    }
}

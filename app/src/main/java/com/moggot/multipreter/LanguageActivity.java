package com.moggot.multipreter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.moggot.multipreter.adapter.RecyclerViewLanguageAdapter;
import com.moggot.multipreter.animation.AnimationBounce;
import com.moggot.multipreter.animation.BackButtonAnimationBounce;
import com.moggot.multipreter.conversation.LanguageConvertor;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс Activity с выбором языка
 */
public class LanguageActivity extends AppCompatActivity implements RecyclerViewLanguageAdapter.SingleClickListener {

    private static final String LOG_TAG = LanguageActivity.class.getSimpleName();

    /**
     * Адаптер RecyclerView
     */
    private RecyclerViewLanguageAdapter adapter;

    /**
     * Тип языка
     * # язык оригинала
     * # язык перевода
     */
    private Consts.LANG_TYPE langType;

    /**
     * Список кодов языков
     */
    private List<String> languageCodes;

    /**
     * Создание Activity
     *
     * @param savedInstanceState - Bundle для хранения данных Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        this.langType = Consts.LANG_TYPE.fromInteger(getIntent().getIntExtra(Consts.EXTRA_LANG, 0));

        initAnalytics();
        fillLangCodeList();
        setTitle();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.langRecyclerView);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        int adapterPosition = getAdapterPosition();
        adapter = new RecyclerViewLanguageAdapter(getFullNameLanguages(), adapterPosition);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter.setOnItemClickListener(this);
        recyclerView.smoothScrollToPosition(adapterPosition);
    }

    /**
     * Обработка нажатия кнопки Назад
     *
     * @param view - view кнопки Назад
     */
    public void onClickBack(View view) {
        AnimationBounce animationBounce = new BackButtonAnimationBounce(this);
        animationBounce.animate(view);
    }

    /**
     * Обработка нажатия на элемент списка
     */
    @Override
    public void onItemClickListener(int position, View view) {
        adapter.selectedItem();
        saveLanguage(position);
        finish();
    }

    /**
     * Инициализация аналитики
     */
    private void initAnalytics() {
        FirebaseAnalysis firebaseAnalytics = new FirebaseAnalysis(this);
        firebaseAnalytics.init();

        Tracker tracker = ((App) getApplication()).getDefaultTracker();
        tracker.enableAdvertisingIdCollection(true);
    }

    /**
     * Заполнение списка поддерживаемых языков
     */
    private void fillLangCodeList() {
        this.languageCodes = new ArrayList<>();
        languageCodes.add(getString(R.string.az_short));
        languageCodes.add(getString(R.string.sq_short));
        languageCodes.add(getString(R.string.xh_short));
        languageCodes.add(getString(R.string.am_short));
        languageCodes.add(getString(R.string.en_short));
        languageCodes.add(getString(R.string.ar_short));
        languageCodes.add(getString(R.string.hy_short));
        languageCodes.add(getString(R.string.af_short));
        languageCodes.add(getString(R.string.eu_short));
        languageCodes.add(getString(R.string.ba_short));
        languageCodes.add(getString(R.string.be_short));
        languageCodes.add(getString(R.string.bn_short));
        languageCodes.add(getString(R.string.bg_short));
        languageCodes.add(getString(R.string.bs_short));
        languageCodes.add(getString(R.string.cy_short));
        languageCodes.add(getString(R.string.hu_short));
        languageCodes.add(getString(R.string.vi_short));
        languageCodes.add(getString(R.string.ht_short));
        languageCodes.add(getString(R.string.gl_short));
        languageCodes.add(getString(R.string.nl_short));
        languageCodes.add(getString(R.string.mrj_short));
        languageCodes.add(getString(R.string.el_short));
        languageCodes.add(getString(R.string.ka_short));
        languageCodes.add(getString(R.string.gu_short));
        languageCodes.add(getString(R.string.da_short));
        languageCodes.add(getString(R.string.he_short));
        languageCodes.add(getString(R.string.yi_short));
        languageCodes.add(getString(R.string.id_short));
        languageCodes.add(getString(R.string.ga_short));
        languageCodes.add(getString(R.string.it_short));
        languageCodes.add(getString(R.string.is_short));
        languageCodes.add(getString(R.string.es_short));
        languageCodes.add(getString(R.string.kk_short));
        languageCodes.add(getString(R.string.kn_short));
        languageCodes.add(getString(R.string.ca_short));
        languageCodes.add(getString(R.string.ky_short));
        languageCodes.add(getString(R.string.zh_short));
        languageCodes.add(getString(R.string.ko_short));
        languageCodes.add(getString(R.string.xh_short));
        languageCodes.add(getString(R.string.la_short));
        languageCodes.add(getString(R.string.lv_short));
        languageCodes.add(getString(R.string.lt_short));
        languageCodes.add(getString(R.string.lb_short));
        languageCodes.add(getString(R.string.mg_short));
        languageCodes.add(getString(R.string.ms_short));
        languageCodes.add(getString(R.string.ml_short));
        languageCodes.add(getString(R.string.mt_short));
        languageCodes.add(getString(R.string.mk_short));
        languageCodes.add(getString(R.string.mi_short));
        languageCodes.add(getString(R.string.mr_short));
        languageCodes.add(getString(R.string.mhr_short));
        languageCodes.add(getString(R.string.mn_short));
        languageCodes.add(getString(R.string.de_short));
        languageCodes.add(getString(R.string.ne_short));
        languageCodes.add(getString(R.string.no_short));
        languageCodes.add(getString(R.string.pa_short));
        languageCodes.add(getString(R.string.pap_short));
        languageCodes.add(getString(R.string.fa_short));
        languageCodes.add(getString(R.string.pl_short));
        languageCodes.add(getString(R.string.pt_short));
        languageCodes.add(getString(R.string.ro_short));
        languageCodes.add(getString(R.string.ru_short));
        languageCodes.add(getString(R.string.ceb_short));
        languageCodes.add(getString(R.string.sr_short));
        languageCodes.add(getString(R.string.si_short));
        languageCodes.add(getString(R.string.sk_short));
        languageCodes.add(getString(R.string.sl_short));
        languageCodes.add(getString(R.string.sw_short));
        languageCodes.add(getString(R.string.su_short));
        languageCodes.add(getString(R.string.tg_short));
        languageCodes.add(getString(R.string.th_short));
        languageCodes.add(getString(R.string.tl_short));
        languageCodes.add(getString(R.string.ta_short));
        languageCodes.add(getString(R.string.tt_short));
        languageCodes.add(getString(R.string.te_short));
        languageCodes.add(getString(R.string.tr_short));
        languageCodes.add(getString(R.string.udm_short));
        languageCodes.add(getString(R.string.uz_short));
        languageCodes.add(getString(R.string.uk_short));
        languageCodes.add(getString(R.string.ur_short));
        languageCodes.add(getString(R.string.fi_short));
        languageCodes.add(getString(R.string.fr_short));
        languageCodes.add(getString(R.string.hi_short));
        languageCodes.add(getString(R.string.hr_short));
        languageCodes.add(getString(R.string.cs_short));
        languageCodes.add(getString(R.string.sv_short));
        languageCodes.add(getString(R.string.gd_short));
        languageCodes.add(getString(R.string.et_short));
        languageCodes.add(getString(R.string.eo_short));
        languageCodes.add(getString(R.string.jv_short));
        languageCodes.add(getString(R.string.ja_short));
    }

    /**
     * Получение списка полных имен языков
     */
    private List<String> getFullNameLanguages() {
        LanguageConvertor convertor = new LanguageConvertor(getResources());
        List<String> fullNameLanguages = new ArrayList<>();
        for (String lang : languageCodes) {
            fullNameLanguages.add(convertor.langCodeToLangName(lang));
        }
        return fullNameLanguages;
    }

    /**
     * Установка названия окна активити
     */
    private void setTitle() {
        if (langType == Consts.LANG_TYPE.INPUT)
            ((TextView) findViewById(R.id.tvLanguage)).setText(getString(R.string.language_input));
        else
            ((TextView) findViewById(R.id.tvLanguage)).setText(getString(R.string.language_output));
    }

    /**
     * Получение позиции установленного языка в списке
     */
    private int getAdapterPosition() {
        String curLang;
        if (langType == Consts.LANG_TYPE.INPUT)
            curLang = LangSharedPreferences.loadInputLanguage(this);
        else
            curLang = LangSharedPreferences.loadOutputLanguage(this);
        return languageCodes.indexOf(curLang);
    }

    /**
     * Сохранение выбранного языка в Preferences
     */
    private void saveLanguage(int position) {
        if (langType == Consts.LANG_TYPE.INPUT)
            LangSharedPreferences.saveInputLanguage(this, languageCodes.get(position));
        else
            LangSharedPreferences.saveOutputLanguage(this, languageCodes.get(position));
    }
}

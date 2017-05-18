package com.moggot.multipreter.conversation;

import android.content.res.Resources;

import com.moggot.multipreter.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс преобразования кода языка в читаемый вид
 */
public class LanguageConvertor {

    private static final String LOG_TAG = LanguageConvertor.class.getSimpleName();

    /**
     * Map для преобразования
     */
    private Map<String, String> shortToLong = new HashMap<>();

    /**
     * Конструктор
     *
     * @param res - ресурсы
     */
    public LanguageConvertor(Resources res) {

        shortToLong.put(res.getString(R.string.az_short), res.getString(R.string.az));
        shortToLong.put(res.getString(R.string.sq_short), res.getString(R.string.sq));
        shortToLong.put(res.getString(R.string.am_short), res.getString(R.string.am));
        shortToLong.put(res.getString(R.string.en_short), res.getString(R.string.en));
        shortToLong.put(res.getString(R.string.ar_short), res.getString(R.string.ar));
        shortToLong.put(res.getString(R.string.hy_short), res.getString(R.string.hy));
        shortToLong.put(res.getString(R.string.af_short), res.getString(R.string.af));
        shortToLong.put(res.getString(R.string.eu_short), res.getString(R.string.eu));
        shortToLong.put(res.getString(R.string.ba_short), res.getString(R.string.ba));
        shortToLong.put(res.getString(R.string.be_short), res.getString(R.string.be));
        shortToLong.put(res.getString(R.string.bn_short), res.getString(R.string.bn));
        shortToLong.put(res.getString(R.string.bg_short), res.getString(R.string.bg));
        shortToLong.put(res.getString(R.string.bs_short), res.getString(R.string.bs));
        shortToLong.put(res.getString(R.string.cy_short), res.getString(R.string.cy));
        shortToLong.put(res.getString(R.string.hu_short), res.getString(R.string.hu));
        shortToLong.put(res.getString(R.string.vi_short), res.getString(R.string.vi));
        shortToLong.put(res.getString(R.string.ht_short), res.getString(R.string.ht));
        shortToLong.put(res.getString(R.string.gl_short), res.getString(R.string.gl));
        shortToLong.put(res.getString(R.string.nl_short), res.getString(R.string.nl));
        shortToLong.put(res.getString(R.string.mrj_short), res.getString(R.string.mrj));
        shortToLong.put(res.getString(R.string.el_short), res.getString(R.string.el));
        shortToLong.put(res.getString(R.string.ka_short), res.getString(R.string.ka));
        shortToLong.put(res.getString(R.string.gu_short), res.getString(R.string.gu));
        shortToLong.put(res.getString(R.string.da_short), res.getString(R.string.da));
        shortToLong.put(res.getString(R.string.he_short), res.getString(R.string.he));
        shortToLong.put(res.getString(R.string.yi_short), res.getString(R.string.yi));
        shortToLong.put(res.getString(R.string.id_short), res.getString(R.string.id));
        shortToLong.put(res.getString(R.string.ga_short), res.getString(R.string.ga));
        shortToLong.put(res.getString(R.string.it_short), res.getString(R.string.it));
        shortToLong.put(res.getString(R.string.is_short), res.getString(R.string.is));
        shortToLong.put(res.getString(R.string.es_short), res.getString(R.string.es));
        shortToLong.put(res.getString(R.string.kk_short), res.getString(R.string.kk));
        shortToLong.put(res.getString(R.string.kn_short), res.getString(R.string.kn));
        shortToLong.put(res.getString(R.string.ca_short), res.getString(R.string.ca));
        shortToLong.put(res.getString(R.string.ky_short), res.getString(R.string.ky));
        shortToLong.put(res.getString(R.string.zh_short), res.getString(R.string.zh));
        shortToLong.put(res.getString(R.string.ko_short), res.getString(R.string.ko));
        shortToLong.put(res.getString(R.string.xh_short), res.getString(R.string.xh));
        shortToLong.put(res.getString(R.string.la_short), res.getString(R.string.la));
        shortToLong.put(res.getString(R.string.lv_short), res.getString(R.string.lv));
        shortToLong.put(res.getString(R.string.lt_short), res.getString(R.string.lt));
        shortToLong.put(res.getString(R.string.lb_short), res.getString(R.string.lb));
        shortToLong.put(res.getString(R.string.mg_short), res.getString(R.string.mg));
        shortToLong.put(res.getString(R.string.ms_short), res.getString(R.string.ms));
        shortToLong.put(res.getString(R.string.ml_short), res.getString(R.string.ml));
        shortToLong.put(res.getString(R.string.mt_short), res.getString(R.string.mt));
        shortToLong.put(res.getString(R.string.mk_short), res.getString(R.string.mk));
        shortToLong.put(res.getString(R.string.mi_short), res.getString(R.string.mi));
        shortToLong.put(res.getString(R.string.mr_short), res.getString(R.string.mr));
        shortToLong.put(res.getString(R.string.mhr_short), res.getString(R.string.mhr));
        shortToLong.put(res.getString(R.string.mn_short), res.getString(R.string.mn));
        shortToLong.put(res.getString(R.string.de_short), res.getString(R.string.de));
        shortToLong.put(res.getString(R.string.ne_short), res.getString(R.string.ne));
        shortToLong.put(res.getString(R.string.no_short), res.getString(R.string.no));
        shortToLong.put(res.getString(R.string.pa_short), res.getString(R.string.pa));
        shortToLong.put(res.getString(R.string.pap_short), res.getString(R.string.pap));
        shortToLong.put(res.getString(R.string.fa_short), res.getString(R.string.fa));
        shortToLong.put(res.getString(R.string.pl_short), res.getString(R.string.pl));
        shortToLong.put(res.getString(R.string.pt_short), res.getString(R.string.pt));
        shortToLong.put(res.getString(R.string.ro_short), res.getString(R.string.ro));
        shortToLong.put(res.getString(R.string.ru_short), res.getString(R.string.ru));
        shortToLong.put(res.getString(R.string.ceb_short), res.getString(R.string.ceb));
        shortToLong.put(res.getString(R.string.sr_short), res.getString(R.string.sr));
        shortToLong.put(res.getString(R.string.si_short), res.getString(R.string.si));
        shortToLong.put(res.getString(R.string.sk_short), res.getString(R.string.sk));
        shortToLong.put(res.getString(R.string.sl_short), res.getString(R.string.sl));
        shortToLong.put(res.getString(R.string.sw_short), res.getString(R.string.sw));
        shortToLong.put(res.getString(R.string.su_short), res.getString(R.string.su));
        shortToLong.put(res.getString(R.string.tg_short), res.getString(R.string.tg));
        shortToLong.put(res.getString(R.string.th_short), res.getString(R.string.th));
        shortToLong.put(res.getString(R.string.tl_short), res.getString(R.string.tl));
        shortToLong.put(res.getString(R.string.ta_short), res.getString(R.string.ta));
        shortToLong.put(res.getString(R.string.tt_short), res.getString(R.string.tt));
        shortToLong.put(res.getString(R.string.te_short), res.getString(R.string.te));
        shortToLong.put(res.getString(R.string.tr_short), res.getString(R.string.tr));
        shortToLong.put(res.getString(R.string.udm_short), res.getString(R.string.udm));
        shortToLong.put(res.getString(R.string.uz_short), res.getString(R.string.uz));
        shortToLong.put(res.getString(R.string.uk_short), res.getString(R.string.uk));
        shortToLong.put(res.getString(R.string.ur_short), res.getString(R.string.ur));
        shortToLong.put(res.getString(R.string.fi_short), res.getString(R.string.fi));
        shortToLong.put(res.getString(R.string.fr_short), res.getString(R.string.fr));
        shortToLong.put(res.getString(R.string.hi_short), res.getString(R.string.hi));
        shortToLong.put(res.getString(R.string.hr_short), res.getString(R.string.hr));
        shortToLong.put(res.getString(R.string.cs_short), res.getString(R.string.cs));
        shortToLong.put(res.getString(R.string.sv_short), res.getString(R.string.sv));
        shortToLong.put(res.getString(R.string.gd_short), res.getString(R.string.gd));
        shortToLong.put(res.getString(R.string.et_short), res.getString(R.string.et));
        shortToLong.put(res.getString(R.string.eo_short), res.getString(R.string.eo));
        shortToLong.put(res.getString(R.string.jv_short), res.getString(R.string.jv));
        shortToLong.put(res.getString(R.string.ja_short), res.getString(R.string.ja));
    }

    /**
     * Получение строки с полным названием языка
     *
     * @param langCode - код языка
     * @return полное название языка
     */
    public String langCodeToLangName(String langCode) {
        return shortToLong.get(langCode);
    }
}

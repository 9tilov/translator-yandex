package com.moggot.multipreter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс преобразования кода языка в читаемый вид
 */
public class Conversation {

    private static final String LOG_TAG = "Conversation";

    /**
     * Map для преобразования
     */
    private Map<String, String> shortToLong = new HashMap<>();

    /**
     * Конструктор
     *
     * @param context - контекст Activity
     */
    public Conversation(Context context) {

        shortToLong.put(context.getString(R.string.az_short), context.getString(R.string.az));
        shortToLong.put(context.getString(R.string.sq_short), context.getString(R.string.sq));
        shortToLong.put(context.getString(R.string.am_short), context.getString(R.string.am));
        shortToLong.put(context.getString(R.string.en_short), context.getString(R.string.en));
        shortToLong.put(context.getString(R.string.ar_short), context.getString(R.string.ar));
        shortToLong.put(context.getString(R.string.hy_short), context.getString(R.string.hy));
        shortToLong.put(context.getString(R.string.af_short), context.getString(R.string.af));
        shortToLong.put(context.getString(R.string.eu_short), context.getString(R.string.eu));
        shortToLong.put(context.getString(R.string.ba_short), context.getString(R.string.ba));
        shortToLong.put(context.getString(R.string.be_short), context.getString(R.string.be));
        shortToLong.put(context.getString(R.string.bn_short), context.getString(R.string.bn));
        shortToLong.put(context.getString(R.string.bg_short), context.getString(R.string.bg));
        shortToLong.put(context.getString(R.string.bs_short), context.getString(R.string.bs));
        shortToLong.put(context.getString(R.string.cy_short), context.getString(R.string.cy));
        shortToLong.put(context.getString(R.string.hu_short), context.getString(R.string.hu));
        shortToLong.put(context.getString(R.string.vi_short), context.getString(R.string.vi));
        shortToLong.put(context.getString(R.string.ht_short), context.getString(R.string.ht));
        shortToLong.put(context.getString(R.string.gl_short), context.getString(R.string.gl));
        shortToLong.put(context.getString(R.string.nl_short), context.getString(R.string.nl));
        shortToLong.put(context.getString(R.string.mrj_short), context.getString(R.string.mrj));
        shortToLong.put(context.getString(R.string.el_short), context.getString(R.string.el));
        shortToLong.put(context.getString(R.string.ka_short), context.getString(R.string.ka));
        shortToLong.put(context.getString(R.string.gu_short), context.getString(R.string.gu));
        shortToLong.put(context.getString(R.string.da_short), context.getString(R.string.da));
        shortToLong.put(context.getString(R.string.he_short), context.getString(R.string.he));
        shortToLong.put(context.getString(R.string.yi_short), context.getString(R.string.yi));
        shortToLong.put(context.getString(R.string.id_short), context.getString(R.string.id));
        shortToLong.put(context.getString(R.string.ga_short), context.getString(R.string.ga));
        shortToLong.put(context.getString(R.string.it_short), context.getString(R.string.it));
        shortToLong.put(context.getString(R.string.is_short), context.getString(R.string.is));
        shortToLong.put(context.getString(R.string.es_short), context.getString(R.string.es));
        shortToLong.put(context.getString(R.string.kk_short), context.getString(R.string.kk));
        shortToLong.put(context.getString(R.string.kn_short), context.getString(R.string.kn));
        shortToLong.put(context.getString(R.string.ca_short), context.getString(R.string.ca));
        shortToLong.put(context.getString(R.string.ky_short), context.getString(R.string.ky));
        shortToLong.put(context.getString(R.string.zh_short), context.getString(R.string.zh));
        shortToLong.put(context.getString(R.string.ko_short), context.getString(R.string.ko));
        shortToLong.put(context.getString(R.string.xh_short), context.getString(R.string.xh));
        shortToLong.put(context.getString(R.string.la_short), context.getString(R.string.la));
        shortToLong.put(context.getString(R.string.lv_short), context.getString(R.string.lv));
        shortToLong.put(context.getString(R.string.lt_short), context.getString(R.string.lt));
        shortToLong.put(context.getString(R.string.lb_short), context.getString(R.string.lb));
        shortToLong.put(context.getString(R.string.mg_short), context.getString(R.string.mg));
        shortToLong.put(context.getString(R.string.ms_short), context.getString(R.string.ms));
        shortToLong.put(context.getString(R.string.ml_short), context.getString(R.string.ml));
        shortToLong.put(context.getString(R.string.mt_short), context.getString(R.string.mt));
        shortToLong.put(context.getString(R.string.mk_short), context.getString(R.string.mk));
        shortToLong.put(context.getString(R.string.mi_short), context.getString(R.string.mi));
        shortToLong.put(context.getString(R.string.mr_short), context.getString(R.string.mr));
        shortToLong.put(context.getString(R.string.mhr_short), context.getString(R.string.mhr));
        shortToLong.put(context.getString(R.string.mn_short), context.getString(R.string.mn));
        shortToLong.put(context.getString(R.string.de_short), context.getString(R.string.de));
        shortToLong.put(context.getString(R.string.ne_short), context.getString(R.string.ne));
        shortToLong.put(context.getString(R.string.no_short), context.getString(R.string.no));
        shortToLong.put(context.getString(R.string.pa_short), context.getString(R.string.pa));
        shortToLong.put(context.getString(R.string.pap_short), context.getString(R.string.pap));
        shortToLong.put(context.getString(R.string.fa_short), context.getString(R.string.fa));
        shortToLong.put(context.getString(R.string.pl_short), context.getString(R.string.pl));
        shortToLong.put(context.getString(R.string.pt_short), context.getString(R.string.pt));
        shortToLong.put(context.getString(R.string.ro_short), context.getString(R.string.ro));
        shortToLong.put(context.getString(R.string.ru_short), context.getString(R.string.ru));
        shortToLong.put(context.getString(R.string.ceb_short), context.getString(R.string.ceb));
        shortToLong.put(context.getString(R.string.sr_short), context.getString(R.string.sr));
        shortToLong.put(context.getString(R.string.si_short), context.getString(R.string.si));
        shortToLong.put(context.getString(R.string.sk_short), context.getString(R.string.sk));
        shortToLong.put(context.getString(R.string.sl_short), context.getString(R.string.sl));
        shortToLong.put(context.getString(R.string.sw_short), context.getString(R.string.sw));
        shortToLong.put(context.getString(R.string.su_short), context.getString(R.string.su));
        shortToLong.put(context.getString(R.string.tg_short), context.getString(R.string.tg));
        shortToLong.put(context.getString(R.string.th_short), context.getString(R.string.th));
        shortToLong.put(context.getString(R.string.tl_short), context.getString(R.string.tl));
        shortToLong.put(context.getString(R.string.ta_short), context.getString(R.string.ta));
        shortToLong.put(context.getString(R.string.tt_short), context.getString(R.string.tt));
        shortToLong.put(context.getString(R.string.te_short), context.getString(R.string.te));
        shortToLong.put(context.getString(R.string.tr_short), context.getString(R.string.tr));
        shortToLong.put(context.getString(R.string.udm_short), context.getString(R.string.udm));
        shortToLong.put(context.getString(R.string.uz_short), context.getString(R.string.uz));
        shortToLong.put(context.getString(R.string.uk_short), context.getString(R.string.uk));
        shortToLong.put(context.getString(R.string.ur_short), context.getString(R.string.ur));
        shortToLong.put(context.getString(R.string.fi_short), context.getString(R.string.fi));
        shortToLong.put(context.getString(R.string.fr_short), context.getString(R.string.fr));
        shortToLong.put(context.getString(R.string.hi_short), context.getString(R.string.hi));
        shortToLong.put(context.getString(R.string.hr_short), context.getString(R.string.hr));
        shortToLong.put(context.getString(R.string.cs_short), context.getString(R.string.cs));
        shortToLong.put(context.getString(R.string.sv_short), context.getString(R.string.sv));
        shortToLong.put(context.getString(R.string.gd_short), context.getString(R.string.gd));
        shortToLong.put(context.getString(R.string.et_short), context.getString(R.string.et));
        shortToLong.put(context.getString(R.string.eo_short), context.getString(R.string.eo));
        shortToLong.put(context.getString(R.string.jv_short), context.getString(R.string.jv));
        shortToLong.put(context.getString(R.string.ja_short), context.getString(R.string.ja));
    }

    /**
     * Получение строки с полным названием языка
     *
     * @param langCode - код языка
     * @return полное название языка
     */
    public String getLongLangName(String langCode) {
        return shortToLong.get(langCode);
    }
}

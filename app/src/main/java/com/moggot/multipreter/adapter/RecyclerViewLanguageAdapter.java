package com.moggot.multipreter.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moggot.multipreter.Consts;
import com.moggot.multipreter.Conversation;
import com.moggot.multipreter.LangSharedPreferences;
import com.moggot.multipreter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс адаптера списка с поддерживаемыми языками
 */
public class RecyclerViewLanguageAdapter extends RecyclerView.Adapter<RecyclerViewLanguageAdapter.SingleCheckViewHolder> {

    private static final String LOG_TAG = RecyclerViewLanguageAdapter.class.getSimpleName();

    private List<String> languages;
    private Context context;
    private Conversation conversation;
    private Consts.LANG_TYPE type;
    private int selectedItem = -1;

    public RecyclerViewLanguageAdapter(Context context, Consts.LANG_TYPE type) {
        this.context = context;
        this.languages = new ArrayList<>();
        this.type = type;

        fillLangList(languages);

        conversation = new Conversation(context);
        String curLang = "";
        if (type == Consts.LANG_TYPE.INPUT)
            curLang = LangSharedPreferences.loadInputLanguage(context);
        else
            curLang = LangSharedPreferences.loadOutputLanguage(context);

        selectedItem = languages.indexOf(curLang);
    }

    @Override
    public SingleCheckViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lang_item, viewGroup, false);
        return new SingleCheckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SingleCheckViewHolder viewHolder, int position) {
        String lang = languages.get(position);
        try {
            viewHolder.setDateToView(lang, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    class SingleCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RadioButton rbCheck;
        private TextView language;

        public SingleCheckViewHolder(View itemView) {
            super(itemView);

            language = (TextView) itemView.findViewById(R.id.tvLang);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rbCheck);
            itemView.setOnClickListener(this);
        }

        public void setDateToView(String lang, int position) throws Exception {
            this.language.setText(conversation.getLongLangName(lang));
            rbCheck.setChecked(position == selectedItem);
        }

        @Override
        public void onClick(View v) {
            selectedItem = getAdapterPosition();
            notifyItemRangeChanged(0, languages.size());
            if (type == Consts.LANG_TYPE.INPUT)
                LangSharedPreferences.saveInputLanguage(context, languages.get(selectedItem));
            else
                LangSharedPreferences.saveOutputLanguage(context, languages.get(selectedItem));
            ((Activity) context).finish();
        }
    }

    /**
     * Заполнение списка поддерживаемых языков
     */
    private void fillLangList(List<String> languages) {
        languages.add(context.getString(R.string.az_short));
        languages.add(context.getString(R.string.sq_short));
        languages.add(context.getString(R.string.xh_short));
        languages.add(context.getString(R.string.am_short));
        languages.add(context.getString(R.string.en_short));
        languages.add(context.getString(R.string.ar_short));
        languages.add(context.getString(R.string.hy_short));
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
        languages.add(context.getString(R.string.xh_short));
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
        languages.add(context.getString(R.string.mn_short));
        languages.add(context.getString(R.string.de_short));
        languages.add(context.getString(R.string.ne_short));
        languages.add(context.getString(R.string.no_short));
        languages.add(context.getString(R.string.pa_short));
        languages.add(context.getString(R.string.pap_short));
        languages.add(context.getString(R.string.fa_short));
        languages.add(context.getString(R.string.pl_short));
        languages.add(context.getString(R.string.pt_short));
        languages.add(context.getString(R.string.ro_short));
        languages.add(context.getString(R.string.ru_short));
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
        languages.add(context.getString(R.string.fr_short));
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

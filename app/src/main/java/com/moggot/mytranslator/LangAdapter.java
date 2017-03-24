package com.moggot.mytranslator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moggot.mytranslator.language.Language;
import com.moggot.mytranslator.observer.AdapterDisplay;
import com.moggot.mytranslator.observer.LangData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toor on 24.03.17.
 */

public class LangAdapter extends BaseAdapter {

    private static class ViewHolder {
        private TextView tvLang;
        private ImageView iwCheck;
    }

    private Context context;
    private LayoutInflater inflater;
    private List<String> languages;
    private Consts.LANG_TYPE type;

    public LangAdapter(Context context, Consts.LANG_TYPE type) {
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
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.lang_item, parent, false);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvLang = (TextView) view
                .findViewById(R.id.tvLang);
        viewHolder.iwCheck = (ImageView) view.findViewById(R.id.iwChecked);

        viewHolder.tvLang.setTag(languages.get(position));
        viewHolder.iwCheck.setTag(languages.get(position));

        LangData langData = new LangData();
        AdapterDisplay adapterDisplay = new AdapterDisplay(context, view, langData);
        Language language = new Language(getLang(position), type);
        langData.setLanguage(language);
        adapterDisplay.display();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang = Conversation.getKeyByValue(getLang(position));
                switch (type) {
                    case INPUT:
                        SharedPreferences.SaveInputLanguage(context, lang);
                        break;
                    case OUTPUT:
                        SharedPreferences.SaveOutputLanguage(context, lang);
                        break;
                    default:
                        break;
                }
                viewHolder.iwCheck.setVisibility(View.VISIBLE);
            }

        });
        return view;
    }

    private void fillLangList() {
        languages.add(context.getString(R.string.sq));
        languages.add(context.getString(R.string.am));
        languages.add(context.getString(R.string.en));
        languages.add(context.getString(R.string.ar));
        languages.add(context.getString(R.string.af));
        languages.add(context.getString(R.string.eu));
        languages.add(context.getString(R.string.ba));
        languages.add(context.getString(R.string.be));
        languages.add(context.getString(R.string.bn));
        languages.add(context.getString(R.string.bg));
        languages.add(context.getString(R.string.bs));
        languages.add(context.getString(R.string.cy));
        languages.add(context.getString(R.string.hu));
        languages.add(context.getString(R.string.vi));
        languages.add(context.getString(R.string.ht));
        languages.add(context.getString(R.string.gl));
        languages.add(context.getString(R.string.nl));
        languages.add(context.getString(R.string.mrj));
        languages.add(context.getString(R.string.el));
        languages.add(context.getString(R.string.ka));
        languages.add(context.getString(R.string.gu));
        languages.add(context.getString(R.string.da));
        languages.add(context.getString(R.string.he));
        languages.add(context.getString(R.string.yi));
        languages.add(context.getString(R.string.id));
        languages.add(context.getString(R.string.ga));
        languages.add(context.getString(R.string.it));
        languages.add(context.getString(R.string.is));
        languages.add(context.getString(R.string.es));
        languages.add(context.getString(R.string.kk));
        languages.add(context.getString(R.string.kn));
        languages.add(context.getString(R.string.ca));
        languages.add(context.getString(R.string.ky));
        languages.add(context.getString(R.string.zh));
        languages.add(context.getString(R.string.ko));
        languages.add(context.getString(R.string.xh));
        languages.add(context.getString(R.string.la));
        languages.add(context.getString(R.string.lv));
        languages.add(context.getString(R.string.lt));
        languages.add(context.getString(R.string.lb));
        languages.add(context.getString(R.string.mg));
        languages.add(context.getString(R.string.ms));
        languages.add(context.getString(R.string.ml));
        languages.add(context.getString(R.string.mt));
        languages.add(context.getString(R.string.mk));
        languages.add(context.getString(R.string.mi));
        languages.add(context.getString(R.string.mr));
        languages.add(context.getString(R.string.mhr));
        languages.add(context.getString(R.string.de));
        languages.add(context.getString(R.string.ne));
        languages.add(context.getString(R.string.no));
        languages.add(context.getString(R.string.pa));
        languages.add(context.getString(R.string.pap));
        languages.add(context.getString(R.string.fa));
        languages.add(context.getString(R.string.pl));
        languages.add(context.getString(R.string.pt));
        languages.add(context.getString(R.string.ro));
        languages.add(context.getString(R.string.ru));
        languages.add(context.getString(R.string.ceb));
        languages.add(context.getString(R.string.sr));
        languages.add(context.getString(R.string.si));
        languages.add(context.getString(R.string.sk));
        languages.add(context.getString(R.string.sl));
        languages.add(context.getString(R.string.sw));
        languages.add(context.getString(R.string.su));
        languages.add(context.getString(R.string.tg));
        languages.add(context.getString(R.string.th));
        languages.add(context.getString(R.string.tl));
        languages.add(context.getString(R.string.ta));
        languages.add(context.getString(R.string.tt));
        languages.add(context.getString(R.string.te));
        languages.add(context.getString(R.string.tr));
        languages.add(context.getString(R.string.udm));
        languages.add(context.getString(R.string.uz));
        languages.add(context.getString(R.string.uk));
        languages.add(context.getString(R.string.ur));
        languages.add(context.getString(R.string.fi));
        languages.add(context.getString(R.string.hi));
        languages.add(context.getString(R.string.hr));
        languages.add(context.getString(R.string.cs));
        languages.add(context.getString(R.string.sv));
        languages.add(context.getString(R.string.gd));
        languages.add(context.getString(R.string.et));
        languages.add(context.getString(R.string.eo));
        languages.add(context.getString(R.string.jv));
        languages.add(context.getString(R.string.ja));
    }
}

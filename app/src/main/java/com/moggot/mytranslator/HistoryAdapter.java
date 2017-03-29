package com.moggot.mytranslator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.moggot.mytranslator.observer.AdapterHistoryDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 29.03.17.
 */

public class HistoryAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<Translator> records;

    private static final String LOG_TAG = "HistoryAdapter";

    public HistoryAdapter(Context context, List<Translator> records) {
        this.context = context;
        this.records = records;
    }


    private static class ViewHolder {
        private TextView tvText;
        private TextView tvTranslation;
        private ImageView iwFavorites;
        private TextView tvInputLang;
        private TextView tvOutputLang;
    }

    public void update(List<Translator> items) {
        this.records = items;
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, null, false);
        view.setTag(viewHolder);

        viewHolder.tvText = (TextView) view.findViewById(R.id.adapterTvText);
        viewHolder.tvTranslation = (TextView) view.findViewById(R.id.adapterTvTranslation);
        viewHolder.iwFavorites = (ImageView) view.findViewById(R.id.adapterIwFavorites);
        viewHolder.tvInputLang = (TextView) view.findViewById(R.id.adapterTvInputLang);
        viewHolder.tvOutputLang = (TextView) view.findViewById(R.id.adapterTvOutputLang);

        viewHolder.tvText.setTag(records.get(position));
        viewHolder.tvTranslation.setTag(records.get(position));
        viewHolder.iwFavorites.setTag(records.get(position));
        viewHolder.tvInputLang.setTag(records.get(position));
        viewHolder.tvOutputLang.setTag(records.get(position));

        Translator translator = getTranslator(position);
        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay;
        adapterDisplay = new AdapterHistoryDisplay(context, view, translatorData);
        translatorData.setTranslator(translator);
        adapterDisplay.display();

        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        view.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                records.remove(position);
                update(records);
                closeItem(position);
            }
        });
        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Translator getTranslator(int position) {
        return ((Translator) getItem(position));
    }
}

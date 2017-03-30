package com.moggot.mytranslator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
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
        private LinearLayout rlDelete;
    }

    public void update(List<Translator> items) {
        this.records = items;
        notifyDatasetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        view.setTag(viewHolder);

        viewHolder.tvText = (TextView) view.findViewById(R.id.adapterTvText);
        viewHolder.tvTranslation = (TextView) view.findViewById(R.id.adapterTvTranslation);
        viewHolder.iwFavorites = (ImageView) view.findViewById(R.id.adapterIwFavorites);
        viewHolder.tvInputLang = (TextView) view.findViewById(R.id.adapterTvInputLang);
        viewHolder.tvOutputLang = (TextView) view.findViewById(R.id.adapterTvOutputLang);
        viewHolder.rlDelete = (LinearLayout) view.findViewById(R.id.rlDelete);

        viewHolder.tvText.setTag(records.get(position));
        viewHolder.tvTranslation.setTag(records.get(position));
        viewHolder.iwFavorites.setTag(records.get(position));
        viewHolder.tvInputLang.setTag(records.get(position));
        viewHolder.tvOutputLang.setTag(records.get(position));
        viewHolder.rlDelete.setTag(records.get(position));

        final Translator translator = getTranslator(position);

        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.adapterIwDelete));
            }
        });

        viewHolder.iwFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (translator.getIsFavorites())
                    translator.setIsFavorites(false);
                else
                    translator.setIsFavorites(true);
                DataBase db = new DataBase(context);
                db.editRecord(translator);
                update(records);
                Log.v(LOG_TAG, "favorites");
            }
        });
        viewHolder.rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                records.remove(position);
                update(records);
                DataBase db = new DataBase(context);
                db.deleteRecord(translator);
                closeItem(position);
                Log.v(LOG_TAG, "delete");
            }
        });
        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
        final Translator translator = getTranslator(position);
        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay;
        adapterDisplay = new AdapterHistoryDisplay(context, convertView, translatorData);
        translatorData.setTranslator(translator);
        adapterDisplay.display();
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

package com.moggot.mytranslator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.observer.AdapterFavoritesDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 30.03.17.
 */

public class FavoritesAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<Translator> records;
    private DataBase db;

    private static final String LOG_TAG = "FavoritesAdapter";

    public FavoritesAdapter(Context context, List<Translator> records) {
        this.context = context;
        this.records = records;
        this.db = new DataBase(context);
    }

    private void update() {
        records = db.getFavoritesRecords();
        notifyDatasetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, null);

        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.adapterIwTrash));
            }
        });

        return view;
    }

    @Override
    public void fillValues(final int position, View convertView) {

        final Translator translator = getTranslator(position);
        convertView.findViewById(R.id.adapterRlDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translator.setIsFavorites(false);
                db.editRecord(translator);
                closeItem(position);
                update();
            }
        });
        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay = new AdapterFavoritesDisplay(context, convertView, translatorData);
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

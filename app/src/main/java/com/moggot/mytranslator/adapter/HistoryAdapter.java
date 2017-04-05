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
import com.moggot.mytranslator.State;
import com.moggot.mytranslator.TranslationOn;
import com.moggot.mytranslator.TranslatorContext;
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
    private DataBase db;

    private static final String LOG_TAG = "HistoryAdapter";

    public HistoryAdapter(Context context, List<Translator> records) {
        this.context = context;
        this.records = records;
        this.db = new DataBase(context);
    }

    private void update() {
        this.records = db.getAllRecords();
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.history_item, null);
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.adapterIwTrash));
            }
        });

        final Translator translatorAtPosition = getTranslator(position);

        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "click");
                State stateOn = new TranslationOn(context);
                TranslatorContext translatorContext = new TranslatorContext(translatorAtPosition);
                translatorContext.setState(stateOn);
                translatorContext.show(translatorAtPosition);
            }

        });

        convertView.findViewById(R.id.adapterRlDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteRecord(translatorAtPosition);
                closeItem(position);
                update();
            }
        });

        convertView.findViewById(R.id.adapterIwFavorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "position = " + position);
                if (translatorAtPosition.getIsFavorites())
                    translatorAtPosition.setIsFavorites(false);
                else
                    translatorAtPosition.setIsFavorites(true);
                db.editRecord(translatorAtPosition);
                update();
            }
        });

        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay = new AdapterHistoryDisplay(context, convertView, translatorData);
        translatorData.setTranslator(translatorAtPosition);
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

package com.moggot.mytranslator.adapter;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;
import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.observer.AdapterHistoryDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 29.03.17.
 */

public class AdapterHistory extends BaseSwipeAdapter {

    private List<Translator> records;
    private DataBase db;
    private Fragment fragment;

    private static final String LOG_TAG = "AdapterHistory";

    public AdapterHistory(Fragment fragment, List<Translator> records) {
        this.records = records;
        this.fragment = fragment;
        this.db = new DataBase(fragment.getContext());
    }

    private void update() {
        this.records = db.getAllRecords();

        if (records.isEmpty()) {
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
            ((TextView) fragment.getView().findViewById(R.id.tvEmptyHistory)).setVisibility(View.VISIBLE);
        } else {
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.VISIBLE);
            ((TextView) fragment.getView().findViewById(R.id.tvEmptyHistory)).setVisibility(View.GONE);

        }
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.history_item, null);
        Log.v(LOG_TAG, "view = " + view);
        return LayoutInflater.from(fragment.getContext()).inflate(R.layout.history_item, null);
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
                LangSharedPreferences.saveInputLanguage(fragment.getContext(), translatorAtPosition.getInputLanguage());
                LangSharedPreferences.saveOutputLanguage(fragment.getContext(), translatorAtPosition.getOutputLanguage());

                HistoryFragment.HistoryEventListener historyEventListener = ((HistoryFragment) fragment).getHistoryEventListener();
                if (historyEventListener != null) {
                    historyEventListener.loadHistoryTranslator(translatorAtPosition);
                }

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
                AnimationBounce animationBounce = new EmptyAnimationBounce(fragment.getContext());
                animationBounce.animate(view);
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
        Display adapterDisplay = new AdapterHistoryDisplay(fragment.getContext(), convertView, translatorData);
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

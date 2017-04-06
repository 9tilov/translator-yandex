package com.moggot.mytranslator.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.adapter.AdapterHistory;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.FragmentHistoryDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 28.03.17.
 */

public class FragmentHistory extends Fragment {

    private static final String LOG_TAG = "FragmentHistory";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(LOG_TAG, "onViewCreated");
        TranslatorData translatorData = new TranslatorData();
        Display fragmentHistoryDisplay = new FragmentHistoryDisplay(getActivity(), view, translatorData);
        translatorData.setTranslator(Translator.getInstance());
        fragmentHistoryDisplay.display();

        ListView listView = (ListView) view.findViewById(R.id.lvHistory);
        DataBase db = new DataBase(getActivity());
        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(getActivity(), records);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(LOG_TAG, "onAttach");
    }

}

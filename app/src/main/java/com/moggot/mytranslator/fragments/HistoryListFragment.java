package com.moggot.mytranslator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.adapter.AdapterHistory;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 11.04.17.
 */

public class HistoryListFragment extends ListFragment {

    public static Fragment newInstance() {
        return new HistoryListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataBase db = new DataBase(getContext());
        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(this, records);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, null);
    }
}
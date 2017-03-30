package com.moggot.mytranslator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.adapter.HistoryAdapter;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 28.03.17.
 */

public class HistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lvHistory);
        DataBase db = new DataBase(getActivity());
        List<Translator> records = db.getAllRecords();
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), records);
        listView.setAdapter(adapter);
        return view;
    }
}

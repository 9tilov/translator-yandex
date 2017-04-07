package com.moggot.mytranslator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.adapter.AdapterHistory;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 28.03.17.
 */

public class FragmentHistory extends Fragment {

    private static final String LOG_TAG = "FragmentHistory";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

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

        ListView listView = (ListView) view.findViewById(R.id.lvHistory);
        DataBase db = new DataBase(getActivity());
        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(getActivity(), records);
        listView.setAdapter(adapter);

        if (db.getAllRecords().isEmpty())
            ((Button) view.findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
        if (((TextView)getActivity().findViewById(R.id.etText)).getText().toString().isEmpty())
            ((Button)getActivity().findViewById(R.id.btnClearText)).setVisibility(View.GONE);
    }

}

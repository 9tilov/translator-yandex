package com.moggot.mytranslator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.adapter.AdapterFavorites;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 10.04.17.
 */

public class FavoritesFragment extends Fragment {

    private static final String LOG_TAG = "FavoritesFragment";

    public FavoritesFragment() {
    }

    public static Fragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(LOG_TAG, "onViewCreated");

        ListView listView = (ListView) getActivity().findViewById(R.id.lvFavorites);
        DataBase db = new DataBase(getActivity());
        List<Translator> records = db.getFavoritesRecords();
        AdapterFavorites adapter = new AdapterFavorites(getActivity(), records);
        listView.setAdapter(adapter);
    }

}
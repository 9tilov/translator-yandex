package com.moggot.mytranslator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 28.03.17.
 */

public class TranslatorFragment extends Fragment {

    private static final String LOG_TAG = "TranslatorFragment";

    public TranslatorFragment() {
    }

    public static Fragment newInstance() {
        return new TranslatorFragment();
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
        Log.v(LOG_TAG, "onCreateView");

        return inflater.inflate(R.layout.fragment_traslation, container, false);
    }

}

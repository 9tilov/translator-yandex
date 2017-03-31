package com.moggot.mytranslator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 28.03.17.
 */

public class TranslationFragment extends Fragment {


    private static final String LOG_TAG = "TranslationFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_traslation, container, false);
    }

}

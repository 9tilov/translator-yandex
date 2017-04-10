package com.moggot.mytranslator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 10.04.17.
 */
public class RootFragment extends Fragment {

    private static final String LOG_TAG = "RootFragment";

    public RootFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment newInstance() {
        return new RootFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.root_fragment, container, false);
    }

}

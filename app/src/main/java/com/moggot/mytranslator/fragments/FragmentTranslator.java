package com.moggot.mytranslator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 28.03.17.
 */

public class FragmentTranslator extends Fragment {

    private static final String LOG_TAG = "FragmentTranslator";

    private View view;
    private TextView tvTranslation, tvDetails;

    public FragmentTranslator(){}

    public static Fragment newInstance() {
        return new FragmentTranslator();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Consts.EXTRA_FRAGMENT_TRANSLATION, tvTranslation.getText().toString());
        outState.putString(Consts.EXTRA_FRAGMENT_DETAILS, tvDetails.getText().toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String translation = savedInstanceState.getString(Consts.EXTRA_FRAGMENT_TRANSLATION, "");
            tvTranslation.setText(translation);

            String details = savedInstanceState.getString(Consts.EXTRA_FRAGMENT_DETAILS, "");
            tvDetails.setText(details);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");

        view = inflater.inflate(R.layout.fragment_traslation, container, false);
        tvTranslation = (TextView) view.findViewById(R.id.tvTranslation);
        tvDetails = (TextView) view.findViewById(R.id.tvDetails);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        tvTranslation = null;
        tvDetails = null;
    }

}

package com.moggot.mytranslator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moggot.mytranslator.R;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.FragmentTranslationDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 28.03.17.
 */

public class FragmentTranslator extends Fragment {

    private static final String LOG_TAG = "FragmentTranslator";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_traslation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(LOG_TAG, "onViewCreated");
        TranslatorData translatorData = new TranslatorData();
        Display fragmentTranslationDisplay = new FragmentTranslationDisplay(getActivity(), view, translatorData);
        Translator translator = Translator.getInstance();
        translatorData.setTranslator(translator);
        fragmentTranslationDisplay.display();
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

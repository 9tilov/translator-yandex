package com.moggot.mytranslator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 28.03.17.
 */

public class TranslatorFragment extends Fragment {

    public interface TranslatorEventListener {
        void setTranslatorID(Long translatorID);
        void setFavorite(boolean isFavorite);
    }

    private static final String LOG_TAG = "TranslatorFragment";
    private static final String ARG_TRANSLATOR_ID = "translator_id";
    private TranslatorEventListener translatorEventListener;
    private Long translatorID;
    private boolean isFavorite;
    private DataBase db;

    public TranslatorFragment() {
    }

    public static Fragment newInstance(Long translatorID) {
        Bundle arguments = new Bundle();
        Fragment fragment = new TranslatorFragment();
        if (translatorID != null) {
            arguments.putLong(ARG_TRANSLATOR_ID, translatorID);
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //   super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            translatorEventListener = (TranslatorEventListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement TranslatorEventListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBase(getContext());
        if (getArguments() != null)
            translatorID = getArguments().getLong(ARG_TRANSLATOR_ID);
        else
            isFavorite = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");

        return inflater.inflate(R.layout.fragment_translator, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAddFovorites = (Button) view.findViewById(R.id.btnAddFavorites);
        btnAddFovorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(getContext());
                animationBounce.animate(view);
                if (translatorID != null) {
                    Translator translator = db.getTranslator(translatorID);
                    if (translator.getIsFavorites())
                        translator.setIsFavorites(false);
                    else
                        translator.setIsFavorites(true);
                    db.editRecord(translator);
                    translatorEventListener.setTranslatorID(translatorID);

                } else {
                    if (isFavorite)
                        isFavorite = false;
                    else
                        isFavorite = true;
                    translatorEventListener.setFavorite(isFavorite);
                }
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        translatorEventListener = null;
    }

}

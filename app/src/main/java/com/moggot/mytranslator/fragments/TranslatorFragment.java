package com.moggot.mytranslator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;

/**
 * Created by toor on 28.03.17.
 */

public class TranslatorFragment extends Fragment {

    public interface TranslatorEventListener {
        void setFavorites(boolean isFavorites);
    }

    private static final String LOG_TAG = "TranslatorFragment";
    private static final String ARG_IS_FAVORITES = "is_favorites";
    private TranslatorEventListener translatorEventListener;
    private boolean isFavorites = false;

    public TranslatorFragment() {
    }

    public static Fragment newInstance(boolean isFavotites) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_IS_FAVORITES, isFavotites);
        Fragment fragment = new TranslatorFragment();
        fragment.setArguments(arguments);
        return fragment;
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
        if (getArguments() != null) {
            isFavorites = getArguments().getBoolean(ARG_IS_FAVORITES);
        }
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
                if (!isFavorites)
                    isFavorites = true;
                else
                    isFavorites = false;
                translatorEventListener.setFavorites(isFavorites);

            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        translatorEventListener = null;
    }

}

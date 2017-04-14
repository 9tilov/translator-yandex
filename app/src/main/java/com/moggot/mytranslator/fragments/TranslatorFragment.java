package com.moggot.mytranslator.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;
import com.moggot.mytranslator.translator.Translator;

import org.w3c.dom.Text;

/**
 * Created by toor on 28.03.17.
 */

public class TranslatorFragment extends Fragment {

    public interface TranslatorEventListener {
        void setFavorites(boolean isFavorite);
    }

    private static final String LOG_TAG = "TranslatorFragment";
    private static final String ARG_TRANSLATOR_ID = "translator_id";
    private TranslatorEventListener translatorEventListener;
    private Long translatorID;
    private boolean isFavorites;
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
        //      super.onSaveInstanceState(outState);
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
        if (getArguments() != null) {
            translatorID = getArguments().getLong(ARG_TRANSLATOR_ID);
            if (translatorID == 0)
                translatorID = null;
        } else
            isFavorites = false;
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
                    isFavorites = translator.getIsFavorites();

                } else {
                    if (isFavorites)
                        isFavorites = false;
                    else
                        isFavorites = true;
                }
                translatorEventListener.setFavorites(isFavorites);
            }
        });

        final TextView tvTranslation = (TextView) view.findViewById(R.id.tvTranslation);
        Button btnCopyTranslation = (Button) view.findViewById(R.id.btnCopy);
        btnCopyTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(getContext());
                animationBounce.animate(view);
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", tvTranslation.getText().toString());
                clipboard.setPrimaryClip(clip);
                translationCopied();
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        translatorEventListener = null;
    }

    private void translationCopied() {
        Toast toast = Toast.makeText(getContext(),
                getContext().getString(R.string.translation_copied), Toast.LENGTH_SHORT);
        toast.show();
    }

}

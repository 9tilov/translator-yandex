package com.moggot.mytranslator.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;

/**
 * Created by toor on 28.03.17.
 */

public class TranslatorFragment extends Fragment {

    public interface TranslatorEventListener {
        void setFavorites();
    }

    private static final String LOG_TAG = "TranslatorFragment";
    private TranslatorEventListener translatorEventListener;

    public TranslatorFragment() {
    }

    public static Fragment newInstance() {
        return new TranslatorFragment();
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
                translatorEventListener.setFavorites();
            }
        });

        final TextView tvTranslation = (TextView) view.findViewById(R.id.tvTranslation);
        Button btnCopyTranslation = (Button) view.findViewById(R.id.btnCopyTranslation);
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

        TextView tvTranslatorLicense =(TextView)view.findViewById(R.id.tvYandexTranslatorLink);
        tvTranslatorLicense.setClickable(true);
        tvTranslatorLicense.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void translationCopied() {
        Toast toast = Toast.makeText(getContext(),
                getContext().getString(R.string.translation_copied), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onDestroyView() {
        super.onDestroyView();
        translatorEventListener = null;
    }

}

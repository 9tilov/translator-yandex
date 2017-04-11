package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 07.04.17.
 */

public class RootFragmentDisplay extends Display {

    private static final String LOG_TAG = "RootFragmentDisplay";
    private Fragment parentFragment;

    public RootFragmentDisplay(Fragment parentFargment, TranslatorData translatorData) {
        super(parentFargment.getContext());
        this.parentFragment = parentFargment;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        if (parentFragment == null || parentFragment.getView() == null)
            return;
        displayInputLang();
        displayOutputLang();
        displayClearButton();
    }

    private void displayInputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) parentFragment.getView().findViewById(R.id.tvInputLang)).setText(conversation.getLongLangName(translator.getInputLanguage()));
        LangSharedPreferences.saveInputLanguage(context, translator.getInputLanguage());
    }

    private void displayOutputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) parentFragment.getView().findViewById(R.id.tvOutputLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
        LangSharedPreferences.saveOutputLanguage(context, translator.getOutputLanguage());
    }

    private void displayClearButton() {
        if (translator.getText().isEmpty())
            ((Button) parentFragment.getView().findViewById(R.id.btnClearText)).setVisibility(View.GONE);
        else
            ((Button) parentFragment.getView().findViewById(R.id.btnClearText)).setVisibility(View.VISIBLE);
    }
}

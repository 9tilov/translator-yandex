package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.view.View;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 21.04.17.
 */

public class ErrorDisplay extends Display {

    private static final String LOG_TAG = "ErrorDisplay";
    private Fragment fragment;

    public ErrorDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        dislpayError();
    }

    private void dislpayError() {
        (fragment.getView().findViewById(R.id.llError)).setVisibility(View.VISIBLE);
        (fragment.getView().findViewById(R.id.rlTranslation)).setVisibility(View.GONE);
    }
}

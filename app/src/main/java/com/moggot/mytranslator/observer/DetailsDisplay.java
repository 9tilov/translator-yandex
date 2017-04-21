package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 21.04.17.
 */

public class DetailsDisplay extends Display {

    private static final String LOG_TAG = "DetailsDisplay";
    private Fragment fragment;

    public DetailsDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        displayDetails();
    }

    private void displayDetails() {
        (fragment.getView().findViewById(R.id.rlTranslation)).setVisibility(View.VISIBLE);
        (fragment.getView().findViewById(R.id.llError)).setVisibility(View.GONE);

        ScrollView scrollViewDetails = (ScrollView) fragment.getView().findViewById(R.id.scrollDetails);
        ScrollView scrollViewTranslation = ((ScrollView) fragment.getView().findViewById(R.id.scrollTranslation));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollViewTranslation.getLayoutParams();
        if (!translator.getDetails().isEmpty()) {
            scrollViewDetails.setVisibility(View.VISIBLE);
            params.height = 0;
            ((TextView) fragment.getView().findViewById(R.id.tvDetails)).setText(translator.getDetails());
        } else {
            scrollViewDetails.setVisibility(View.INVISIBLE);
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.getContext().getResources().getDimension(R.dimen.text_size_dictionary));
        }
    }

}

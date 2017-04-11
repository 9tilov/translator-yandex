package com.moggot.mytranslator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.HistoryDisplay;
import com.moggot.mytranslator.observer.RootFragmentDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOff extends State {

    private static final String LOG_TAG = "TranslationOff";

    private Fragment parentFragment;

    public TranslationOff(Fragment parentFragment) {
        super(parentFragment.getContext());
        this.parentFragment = parentFragment;
        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        if (fragment == null) {
            FragmentTransaction ft = parentFragment.getChildFragmentManager().beginTransaction();
            ft.replace(R.id.root_frame, HistoryFragment.newInstance(), Consts.TAG_FRAGMENT_HISTORY);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            parentFragment.getChildFragmentManager().executePendingTransactions();
        }
    }

    public void show(Translator translator) {
        TranslatorData translatorData = new TranslatorData();
        Display display = new RootFragmentDisplay(parentFragment, translatorData);
        translatorData.setTranslator(translator);
        display.display();

        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        display = new HistoryDisplay(fragment, translatorData);
        display.display();
    }
}

package com.moggot.mytranslator.state;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.moggot.mytranslator.Consts;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.translation.TranslateManager;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.fragments.TranslatorFragment;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslationDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOn extends State {

    private static final String LOG_TAG = "TranslationOn";

    private Fragment parentFragment;

    public TranslationOn(Fragment parentFragment) {
        super(parentFragment);
        this.parentFragment = parentFragment;
        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null) {
            FragmentTransaction ft = parentFragment.getChildFragmentManager().beginTransaction();
            ft.replace(R.id.root_frame, TranslatorFragment.newInstance(), Consts.TAG_FRAGMENT_TRANSLATOR);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            parentFragment.getChildFragmentManager().executePendingTransactions();
        }

    }

    public void show(Translator translator) {
        super.show(translator);

        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_TRANSLATOR);
        if (fragment == null || fragment.getView() == null)
            return;

        DataBase db = new DataBase(parentFragment.getContext());
        Translator foundRecord = db.findRecord(translator);

        if (foundRecord == null) {
            TranslateManager translateManager = new TranslateManager(parentFragment);
            translateManager.translate(translator);
        } else {
            translator.setTranslator(foundRecord);
            TranslatorData translatorData = new TranslatorData();
            Display translationDisplay = new TranslationDisplay(fragment, translatorData);
            translatorData.setTranslator(translator);
            translationDisplay.display();
        }
    }
}
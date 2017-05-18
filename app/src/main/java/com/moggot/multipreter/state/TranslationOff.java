package com.moggot.multipreter.state;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.moggot.multipreter.Consts;
import com.moggot.multipreter.R;
import com.moggot.multipreter.fragments.HistoryListFragment;
import com.moggot.multipreter.observer.Display;
import com.moggot.multipreter.observer.HistoryDisplay;
import com.moggot.multipreter.observer.TranslatorData;
import com.moggot.multipreter.translator.Translator;

/**
 * Класс состояния Off (выключенного переводчика)
 */
public class TranslationOff extends State {

    private static final String LOG_TAG = TranslationOff.class.getSimpleName();

    /**
     * Фрагмент с историей перевода
     */
    private Fragment fragment;

    /**
     * Конструктор
     *
     * @param parentFragment - родительский фрагмент
     */
    public TranslationOff(Fragment parentFragment) {
        super(parentFragment);

        fragment = parentFragment.getChildFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        if (fragment == null) {
            FragmentTransaction ft = parentFragment.getChildFragmentManager().beginTransaction();
            fragment = HistoryListFragment.newInstance();
            ft.replace(R.id.root_frame, fragment, Consts.TAG_FRAGMENT_HISTORY);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();

            parentFragment.getChildFragmentManager().executePendingTransactions();
        }
    }

    /**
     * Отображение данных при показе истории переводов, когда транслятор выключен
     *
     * @param translator - родительский фрагмент
     */
    public void show(Translator translator) {
        super.show(translator);

        TranslatorData translatorData = new TranslatorData();
        Display display = new HistoryDisplay(fragment, translatorData);
        translatorData.setTranslator(translator);
        display.display();
    }
}

package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.ListView;

import com.moggot.mytranslator.adapter.HistoryAdapter;
import com.moggot.mytranslator.fragments.HistoryFragment;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 02.04.17.
 */

public class TranslationOff extends State {

    public TranslationOff(Context context) {
        super(context);

        FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
        Fragment fragment = new HistoryFragment();
        ft.replace(R.id.frgmCont, fragment, Consts.TAG_FRAGMENT_HISTORY);
        ft.commit();
        ((Activity) context).getFragmentManager().executePendingTransactions();
    }

    @Override
    public void show(Translator translator) {
        Fragment historyFragment = ((Activity) context).getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
        if (historyFragment != null && historyFragment.isVisible()) {
            ListView listView = (ListView) historyFragment.getView().findViewById(R.id.lvHistory);
            DataBase db = new DataBase(context);
            List<Translator> records = db.getAllRecords();
            HistoryAdapter adapter = new HistoryAdapter(context, records);
            listView.setAdapter(adapter);
        }
    }
}

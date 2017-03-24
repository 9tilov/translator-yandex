package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.moggot.mytranslator.R;
import com.moggot.mytranslator.SharedPreferences;
import com.moggot.mytranslator.language.Language;

/**
 * Created by toor on 24.03.17.
 */

public class AdapterDisplay implements Observer {

    private View view;
    private Language language;
    private Context context;

    public AdapterDisplay(Context context, View view, LangData langData) {
        this.view = view;
        this.context = context;
        langData.registerObserver(this);
    }

    @Override
    public void update(Language language) {
        this.language = language;
        display();
    }

    public void display() {
        displayLang();
        displayCheck();
    }

    private void displayLang() {
        ((TextView) view.findViewById(R.id.tvLang)).setText(language.getName());
    }

    private void displayCheck() {
        switch (language.getType()) {
            case INPUT:
                String inputLang = SharedPreferences.LoadInputLanguage(context);
                if (inputLang.equals(language.getName()))
                    (view.findViewById(R.id.iwChecked)).setVisibility(View.VISIBLE);
                else
                    (view.findViewById(R.id.iwChecked)).setVisibility(View.GONE);
                break;
            case OUTPUT:
                String outputLang = SharedPreferences.LoadOutputLanguage(context);
                if (outputLang.equals(language.getName()))
                    (view.findViewById(R.id.iwChecked)).setVisibility(View.VISIBLE);
                else
                    (view.findViewById(R.id.iwChecked)).setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}

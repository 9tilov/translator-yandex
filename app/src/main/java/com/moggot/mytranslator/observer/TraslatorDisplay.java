package com.moggot.mytranslator.observer;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.language.Language;

/**
 * Created by toor on 26.03.17.
 */

public class TraslatorDisplay implements Observer {

    private Language language;
    private Context context;

    public TraslatorDisplay(Context context, LangData langData) {
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
    }

    private void displayLang() {
        Conversation conversation = new Conversation(context);
        switch (language.getType()) {
            case INPUT:
                ((TextView) ((Activity) context).findViewById(R.id.tvInputLang)).setText(conversation.getLongLangName(language.getName()));
                break;
            case OUTPUT:
                ((TextView) ((Activity) context).findViewById(R.id.tvOutputLang)).setText(conversation.getLongLangName(language.getName()));
                break;
            default:
                break;
        }
    }
}

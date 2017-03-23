package com.moggot.mytranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String translation = "";

        TranslationTask feed = new TranslationTask();
        feed.execute();

//        Log.v(LOG_TAG, "start");
//        try {
//            Translate.setKey(ApiKeys.YANDEX_API_KEY);
//            translation = Translate.execute("hello", Language.ENGLISH, Language.RUSSIAN);
//            Log.v(LOG_TAG, "transition 11= " + translation);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//
//        try {
//            String a = YandexTranslate.translate("ru", "hello");
//            Log.v(LOG_TAG, "transition = " + a);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


    }


}

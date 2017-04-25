package com.moggot.multipreter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.analytics.Tracker;
import com.moggot.multipreter.adapter.AdapterLanguage;
import com.moggot.multipreter.animation.AnimationBounce;
import com.moggot.multipreter.animation.BackButtonAnimationBounce;

/**
 * Класс Activity с выбором языка
 */
public class LanguageActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LanguageActivity";

    /**
     * Создание Activity
     *
     * @param savedInstanceState - Bundle для хранения данных Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        FirebaseAnalysis firebaseAnalytics = new FirebaseAnalysis(this);
        firebaseAnalytics.init();

        Tracker tracker = ((App) getApplication()).getDefaultTracker();
        tracker.enableAdvertisingIdCollection(true);

        Consts.LANG_TYPE type = Consts.LANG_TYPE.fromInteger(getIntent().getIntExtra(Consts.EXTRA_LANG, 0));

        ListView listView = (ListView) findViewById(R.id.lvLanguages);
        AdapterLanguage adapter = new AdapterLanguage(this, type);
        listView.setAdapter(adapter);
    }

    /**
     * Обработка нажатия кнопки Назад
     *
     * @param view - view кнопки Назад
     */
    public void onClickBack(View view) {
        AnimationBounce animationBounce = new BackButtonAnimationBounce(this);
        animationBounce.animate(view);
    }
}

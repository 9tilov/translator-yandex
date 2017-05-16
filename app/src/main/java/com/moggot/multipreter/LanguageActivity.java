package com.moggot.multipreter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.moggot.multipreter.adapter.RecyclerViewLanguageAdapter;
import com.moggot.multipreter.animation.AnimationBounce;
import com.moggot.multipreter.animation.BackButtonAnimationBounce;


/**
 * Класс Activity с выбором языка
 */
public class LanguageActivity extends AppCompatActivity {

    private static final String LOG_TAG = LanguageActivity.class.getSimpleName();

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

        if (type == Consts.LANG_TYPE.INPUT)
            ((TextView) findViewById(R.id.tvLanguage)).setText(getString(R.string.language_input));
        else
            ((TextView) findViewById(R.id.tvLanguage)).setText(getString(R.string.language_output));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.langRecyclerView);
        RecyclerViewLanguageAdapter adapter = new RecyclerViewLanguageAdapter(this, type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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

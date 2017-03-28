package com.moggot.mytranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class LangActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LangActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);

        Consts.LANG_TYPE type = Consts.LANG_TYPE.fromInteger(getIntent().getIntExtra(Consts.EXTRA_LANG, 0));

        ListView listView = (ListView) findViewById(R.id.lvLanguages);
        LangAdapter adapter = new LangAdapter(this, type);
        listView.setAdapter(adapter);
    }

    public void onClickBack(View view) {
        finish();
    }
}

package com.moggot.mytranslator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.moggot.mytranslator.translate.Translate;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "MainActivity";

    private TextView tvInputLang, tvOutputLang, tvTranslation;

    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInputLang = (TextView) findViewById(R.id.tvInputLang);
        tvOutputLang = (TextView) findViewById(R.id.tvOutputLang);
        tvTranslation = (TextView) findViewById(R.id.tvTranslation);

        etText = (EditText) findViewById(R.id.etText);
        etText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable str) {
                TranslationTask feed = new TranslationTask();
                feed.execute(str.toString(), Consts.Lang.RUSSIAN.toString(), Consts.Lang.ENGLISH.toString());
            }
        });
    }

    public void onClickChangeLang(View view) {
        String tmp = tvInputLang.getText().toString();
        tvInputLang.setText(tvOutputLang.getText().toString());
        tvOutputLang.setText(tmp);
    }

    public void onClickInputLang(View view) {
        Intent intent = new Intent(this, LangActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.INPUT);
        startActivity(intent);
    }

    public void onClickOutputLang(View view) {
        Intent intent = new Intent(this, LangActivity.class);
        intent.putExtra(Consts.EXTRA_LANG, Consts.LANG_TYPE.OUTPUT);
        startActivity(intent);
    }


    private class TranslationTask extends AsyncTask<String, String, String> {

        private static final String LOG_TAG = "TranslationTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Translate.setKey(ApiKeys.YANDEX_API_KEY);
                return Translate.execute(params[0], Consts.Lang.fromString(params[1]), Consts.Lang.fromString(params[2]));
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvTranslation.setText(result);
        }
    }
}

package com.moggot.mytranslator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.moggot.mytranslator.language.Language;
import com.moggot.mytranslator.translate.Translate;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "MainActivity";

    private TextView tvTextLang, tvTranslationLang, tvTranslation;

    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextLang = (TextView) findViewById(R.id.tvTextLang);
        tvTranslationLang = (TextView) findViewById(R.id.tvTranslationLang);
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
                feed.execute(str.toString(), Language.RUSSIAN.toString(), Language.ENGLISH.toString());
            }
        });
    }

    public void onClickChangeLang(View view) {
        String tmp = tvTextLang.getText().toString();
        tvTextLang.setText(tvTranslationLang.getText().toString());
        tvTranslationLang.setText(tmp);
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
                return Translate.execute(params[0], Language.fromString(params[1]), Language.fromString(params[2]));
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

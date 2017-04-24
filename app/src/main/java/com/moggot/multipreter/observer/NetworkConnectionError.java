package com.moggot.multipreter.observer;

import android.support.v4.app.Fragment;
import android.view.View;

import com.moggot.multipreter.R;

/**
 * Класс для отображения данных при отсутствии соединения
 */
public class NetworkConnectionError extends Display {

    private static final String LOG_TAG = "NetworkConnectionError";

    /**
     * Фрагмент, в котором необходимо отобразить данные
     */
    private Fragment fragment;

    /**
     * Конструктор
     *
     * @param fragment       - фрагмент, в котором необходимо отобразить данные
     * @param translatorData - данные транслятора
     */
    public NetworkConnectionError(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        dislpayError();
    }

    /**
     * Отображение ошибки
     */
    private void dislpayError() {
        (fragment.getView().findViewById(R.id.llError)).setVisibility(View.VISIBLE);
        (fragment.getView().findViewById(R.id.rlTranslation)).setVisibility(View.GONE);
    }
}

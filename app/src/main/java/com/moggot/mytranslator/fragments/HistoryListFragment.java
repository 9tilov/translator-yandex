package com.moggot.mytranslator.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.HistoryDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Класс фрагмента истории перевода
 */
public class HistoryListFragment extends ListFragment {

    /**
     * Интерфейс для передачи данных в родительский фрагмент
     */
    public interface HistoryListEventListener {

        /**
         * Загрузка элемента из истории перевода в родительский фрагмент
         *
         * @param translator - транслятор
         */
        void loadHistoryItem(Translator translator);
    }

    /**
     * База данных
     */
    private DataBase db;

    /**
     * Дисплей для отображения данных
     */
    private Display display;

    /**
     * Listener для передачи данных
     */
    private HistoryListEventListener historyListEventListener;

    /**
     * Получение listener
     *
     * @return listener
     */
    public HistoryListEventListener getHistoryListEventListener() {
        return historyListEventListener;
    }

    /**
     * Конструктор
     */
    public HistoryListFragment() {
    }

    /**
     * Метод для создания экземпляра фрагмента
     *
     * @return текущий фрагмент
     */
    public static Fragment newInstance() {
        return new HistoryListFragment();
    }

    /**
     * Вызывается, когда фрагмент связывается с Activity
     * Получаем родительский фрагмент. Данный фрагмент связан с {@link RootFragment} как ребенок-родитель
     *
     * @param context - контекст Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            historyListEventListener = (HistoryListEventListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement HistoryListEventListener");
        }
    }

    /**
     * Подготавливаем БД для работы
     *
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBase(getContext());
    }

    /**
     * Отображаем данные фрагмента
     *
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TranslatorData translatorData = new TranslatorData();
        display = new HistoryDisplay(this, translatorData);
        display.display();
    }

    /**
     * Загрузка интерфейса фрагмента
     *
     * @param inflater           - разметка фрагмента
     * @param container          - родительский контейнер фрагмента
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, null);
    }

    /**
     * Вызывается после создания фрагмента
     *
     * @param view               - коренвое view фрагмента
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnClearHistory = (Button) view.findViewById(R.id.btnClearHistory);
        btnClearHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(getContext());
                animationBounce.animate(v);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_history));
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteAll();
                                display.display();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    /**
     * Удаляем View фрагмента
     * Вместе с этим очищаем память под созданный listener
     */
    public void onDestroyView() {
        super.onDestroyView();
        historyListEventListener = null;
    }
}
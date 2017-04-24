package com.moggot.multipreter.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moggot.multipreter.DataBase;
import com.moggot.multipreter.MainActivity;
import com.moggot.multipreter.R;
import com.moggot.multipreter.animation.AnimationBounce;
import com.moggot.multipreter.animation.EmptyAnimationBounce;
import com.moggot.multipreter.observer.Display;
import com.moggot.multipreter.observer.FavoritesDisplay;
import com.moggot.multipreter.observer.TranslatorData;
import com.moggot.multipreter.translator.Translator;

/**
 * Класс фрагмента списка избранных слов перевода
 */
public class FavoritesListFragment extends ListFragment {

    private static final String LOG_TAG = "FavoritesFragment";

    /**
     * Интерфейс для передачи данных в родительский фрагмент
     */
    public interface FavoritesListEventListener {

        /**
         * Загрузка элемента из Избранного в родительский фрагмент
         *
         * @param translator - транслятор
         */
        void loadFavoriteItem(Translator translator);
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
    private FavoritesListEventListener favoritesListEventListener;

    /**
     * Получение listener
     *
     * @return listener
     */
    public FavoritesListEventListener getFavoritesListEventListener() {
        return favoritesListEventListener;
    }

    /**
     * Конструктор
     */
    public FavoritesListFragment() {
    }

    /**
     * Метод для создания экземпляра фрагмента
     *
     * @return текущий фрагмент
     */
    public static Fragment newInstance() {
        return new FavoritesListFragment();
    }

    /**
     * Вызывается, когда фрагмент связывается с Activity
     * Получаем соседний фрагмент, связываем текущий фрагмент с соседним (они относятся как братья)
     *
     * @param context - контекст Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getTargetFragment() == null) {
            ViewPager pager = ((MainActivity) getActivity()).getViewPager();
            Fragment fragment = (RootFragment) pager.getAdapter().instantiateItem(pager, 0);
            setTargetFragment(fragment, 0);
        }
        try {
            Fragment fragment = getTargetFragment();
            if (fragment != null)
                favoritesListEventListener = (FavoritesListEventListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement FavoritesEventListener");
        }
    }

    /**
     * Фрагмент не будет пересоздан при смене состояния Activity
     * Подготавливаем БД для работы
     *
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        display = new FavoritesDisplay(this, translatorData);
        display.display();
    }

    /**
     * Загрузка интерфейса фрагмента
     *
     * @param inflater           - разметка фрагмента
     * @param container          - родительский контейнер фрагмента
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
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
        Log.v(LOG_TAG, "onViewCreated");

        Button btnClearFavotites = (Button) view.findViewById(R.id.btnClearFavorites);
        btnClearFavotites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(getContext());
                animationBounce.animate(v);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle(getString(R.string.dialog_title_delete_favorites));
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteAllFavorites();
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
     * Вызывается при смене страниц ViewPager
     *
     * @param isVisibleToUser - флаг, показывающий, виден ли фрагмент или нет
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(LOG_TAG, "setUserVisibleHint = " + isVisibleToUser);
        if (isVisibleToUser) {
            display.display();
        }
    }

    /**
     * Удаляем View фрагмента
     * Вместе с этим очищаем память под созданный listener
     */
    public void onDestroyView() {
        super.onDestroyView();
        favoritesListEventListener = null;
    }

}

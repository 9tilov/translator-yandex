package com.moggot.mytranslator.adapter;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;
import com.moggot.mytranslator.fragments.HistoryListFragment;
import com.moggot.mytranslator.observer.AdapterHistoryDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Класс адаптера списка слов в Истории перевода
 */
public class AdapterHistory extends BaseSwipeAdapter {

    private static final String LOG_TAG = "AdapterHistory";

    /**
     * Список слов в Истории перевода
     */
    private List<Translator> records;

    /**
     * База данных
     */
    private DataBase db;

    /**
     * ListFragment с Историей переводов
     */
    private Fragment fragment;

    /**
     * Конструктор
     *
     * @param fragment - фрагмент со списком
     * @param records  - записи в истории
     */
    public AdapterHistory(Fragment fragment, List<Translator> records) {
        this.records = records;
        this.db = new DataBase(fragment.getContext());
        this.fragment = fragment;
    }

    /**
     * Обновления адаптера при добавлении и удалении записи
     * Так же обновляется видимость кнопки "Удалить историю перевода" после того,
     * как вручную удаляется последняя запись
     */
    private void update() {
        this.records = db.getAllRecords();

        if (fragment.getView() == null)
            return;
        if (records.isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.VISIBLE);

        notifyDataSetChanged();
    }

    /**
     * Получение id кнопки удаления при свайпе по позиции элемента
     *
     * @param position - позиция view в списке
     * @return id кнопки удаления
     */
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    /**
     * Создание нового элемента view из xml
     * Здесь нельзя объявлять никакие listeners.
     * Все listeners объявляются в методе fillValues
     *
     * @param position - позиция view в списке
     * @param parent   - родительский элемент
     * @return новый элемент view
     */
    @Override
    public View generateView(final int position, ViewGroup parent) {
        return LayoutInflater.from(fragment.getContext()).inflate(R.layout.history_item, null);
    }

    /**
     * Создание элементов списка и listeners
     * Объявление listeners, отображение всех элементов
     *
     * @param position    - позиция view в списке
     * @param convertView - уже существующий элемент списка
     */
    @Override
    public void fillValues(final int position, final View convertView) {
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.adapterIwTrash));
            }
        });

        final Translator translatorAtPosition = getTranslator(position);

        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "click");
                LangSharedPreferences.saveInputLanguage(fragment.getContext(), translatorAtPosition.getInputLanguage());
                LangSharedPreferences.saveOutputLanguage(fragment.getContext(), translatorAtPosition.getOutputLanguage());

                HistoryListFragment.HistoryListEventListener historyListEventListener = ((HistoryListFragment) fragment).getHistoryListEventListener();
                if (historyListEventListener != null) {
                    historyListEventListener.loadHistoryItem(translatorAtPosition);
                }
            }

        });

        convertView.findViewById(R.id.adapterRlDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteRecord(translatorAtPosition);
                closeItem(position);
                update();
            }
        });

        convertView.findViewById(R.id.rlFavorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(fragment.getContext());
                animationBounce.animate(view);
                Log.v(LOG_TAG, "position = " + position);
                if (translatorAtPosition.getIsFavorites())
                    translatorAtPosition.setIsFavorites(false);
                else
                    translatorAtPosition.setIsFavorites(true);
                db.editRecord(translatorAtPosition);
                update();
            }
        });

        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay = new AdapterHistoryDisplay(fragment.getContext(), convertView, translatorData);
        translatorData.setTranslator(translatorAtPosition);
        adapterDisplay.display();
    }

    /**
     * Получение количества элементов в списке
     *
     * @return количество элементов в списке
     */
    @Override
    public int getCount() {
        return records.size();
    }

    /**
     * Получение элемента по позиции
     *
     * @param position - позиция элемента в списке
     * @return элемент по позиции
     */
    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    /**
     * Получение id элемента по позиции
     *
     * @param position - позиция элемента в списке
     * @return id элемента
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Получение объекта Translator по позиции
     *
     * @param position - позиция элемента в списке
     * @return элемент по позиции
     */
    private Translator getTranslator(int position) {
        return ((Translator) getItem(position));
    }
}

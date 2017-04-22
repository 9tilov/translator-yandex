package com.moggot.mytranslator.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;

/**
 * Класс фрагмента с экраном перевода
 */
public class TranslatorFragment extends Fragment {

    private static final String LOG_TAG = "TranslatorFragment";

    /**
     * Интерфейс для передачи данных в родительский фрагмент
     */
    public interface TranslatorEventListener {

        /**
         * Установка флага favorites для переведенного слова
         */
        void setFavorites();
    }

    /**
     * Listener для передачи данных
     */
    private TranslatorEventListener translatorEventListener;

    /**
     * Конструктор
     */
    public TranslatorFragment() {
    }

    /**
     * Метод для создания экземпляра фрагмента
     *
     * @return текущий фрагмент
     */
    public static Fragment newInstance() {
        return new TranslatorFragment();
    }

    /**
     * Вызывается, когда фрагмент связывается с Activity
     * Получаем родительский фрагмент, данный фрагмент связан с {@link RootFragment} как ребенок-родитель
     *
     * @param context - контекст Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            translatorEventListener = (TranslatorEventListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement TranslatorEventListener");
        }
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
        return inflater.inflate(R.layout.fragment_translator, container, false);
    }

    /**
     * Вызывается после создания фрагмента
     *
     * @param view               - коренвое view фрагмента
     * @param savedInstanceState - Bundle для загрузки состояния фрагмента
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAddFovorites = (Button) view.findViewById(R.id.btnAddFavorites);
        btnAddFovorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(getContext());
                animationBounce.animate(view);
                translatorEventListener.setFavorites();
            }
        });

        final TextView tvTranslation = (TextView) view.findViewById(R.id.tvTranslation);
        Button btnCopyTranslation = (Button) view.findViewById(R.id.btnCopyTranslation);
        btnCopyTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationBounce animationBounce = new EmptyAnimationBounce(getContext());
                animationBounce.animate(view);
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", tvTranslation.getText().toString());
                clipboard.setPrimaryClip(clip);
                translationCopied();
            }
        });

        TextView tvTranslatorLicense = (TextView) view.findViewById(R.id.tvYandexTranslatorLink);
        tvTranslatorLicense.setClickable(true);
        tvTranslatorLicense.setMovementMethod(LinkMovementMethod.getInstance());

    }

    /**
     * Всплывающее сообщение при копировании слова перевода
     */
    private void translationCopied() {
        Toast toast = Toast.makeText(getContext(),
                getContext().getString(R.string.translation_copied), Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Удаляем View фрагмента
     * Вместе с этим очищаем память под созданный listener
     */
    public void onDestroyView() {
        super.onDestroyView();
        translatorEventListener = null;
    }

}

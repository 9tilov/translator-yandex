package com.moggot.multipreter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * Кастомная Edittext.
 * Создана для того, чтобы можно было одновременно использовать флаги textMultiLine и actionDone
 */
public class BackAwareEditText extends AppCompatEditText {

    /**
     * Listener нажатия кнопки Назад
     */
    private BackPressedListener mOnImeBack;

    /**
     * Конструктор
     *
     * @param context - контекст Activity
     */
    public BackAwareEditText(Context context) {
        super(context);
    }

    /**
     * Конструктор
     *
     * @param context - контекст Activity
     * @param attrs   - набор атрибутов
     */
    public BackAwareEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Конструктор
     *
     * @param context  - контекст Activity
     * @param attrs    - набор атрибутов
     * @param defStyle - стиль
     */
    public BackAwareEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Callback нажатия кнопки Назад
     *
     * @param keyCode - код операции
     * @param event   - событие нажатия
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mOnImeBack != null) mOnImeBack.onImeBack(this);
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * Установка интерфейса нажатия кнопки Назад
     *
     * @param listener - listener кнопки Назад
     */
    public void setBackPressedListener(BackPressedListener listener) {
        mOnImeBack = listener;
    }

    /**
     * Интерфейс нажатия кнопки Назад
     */
    public interface BackPressedListener {
        void onImeBack(BackAwareEditText editText);
    }

    /**
     * Создание соединения для одновременной работы флагов textMultiLine и actionDone
     *
     * @param outAttrs - аттрибуты
     * @return соединение
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }
}

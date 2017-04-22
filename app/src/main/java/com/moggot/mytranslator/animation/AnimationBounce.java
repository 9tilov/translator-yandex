package com.moggot.mytranslator.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.moggot.mytranslator.R;

/**
 * Абстрактный класс анимации
 */
public abstract class AnimationBounce {

    /**
     * Контекст Activity
     */
    protected Context context;

    /**
     * Анимация
     */
    protected final Animation animation;

    /**
     * Конструктор
     * Здесь инициализируется общая анимация
     *
     * @param context - констекст Activity
     */
    public AnimationBounce(Context context) {
        this.context = context;
        this.animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        double animationDuration = 0.5 * 1000;
        animation.setDuration((long) animationDuration);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.50, 15);

        animation.setInterpolator(interpolator);
    }

    /**
     * Инициализация view
     *
     * @param view - view, которую нужно анимировать
     */
    private void initView(View view) {
        view.startAnimation(animation);
    }

    /**
     * Инициализация поведения после анимации
     *
     * @param view - view, которую необходимо анимировать
     */
    protected abstract void initAnimationBehavior(View view);

    /**
     * Запуск анимации
     *
     * @param view - view, которую необходимо анимировать
     */
    public final void animate(final View view) {
        initView(view);
        initAnimationBehavior(view);
    }
}

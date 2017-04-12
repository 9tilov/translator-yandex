package com.moggot.mytranslator.fragments;

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

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.MainActivity;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.animation.AnimationBounce;
import com.moggot.mytranslator.animation.EmptyAnimationBounce;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.FavoritesDisplay;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

/**
 * Created by toor on 12.04.17.
 */

public class FavoritesListFragment extends ListFragment {

    public interface FavoritesListEventListener {
        void loadFavoriteItem(Translator translator);
        void deleteFavoritesFlag(Translator translator);
    }

    private static final String LOG_TAG = "FavoritesFragment";

    private DataBase db;
    private Display display;
    private FavoritesListEventListener favoritesListEventListener;

    public FavoritesListEventListener getFavoritesListEventListener() {
        return favoritesListEventListener;
    }

    public FavoritesListFragment() {
    }

    public static Fragment newInstance() {
        return new FavoritesListFragment();
    }

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        db = new DataBase(getContext());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TranslatorData translatorData = new TranslatorData();
        display = new FavoritesDisplay(this, translatorData);
        display.display();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(LOG_TAG, "setUserVisibleHint = " + isVisibleToUser);
        if (isVisibleToUser) {
            display.display();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        favoritesListEventListener = null;
    }

}

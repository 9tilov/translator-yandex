package com.moggot.multipreter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moggot.multipreter.R;

import java.util.List;

/**
 * Класс адаптера списка с поддерживаемыми языками
 */
public class RecyclerViewLanguageAdapter extends RecyclerView.Adapter<RecyclerViewLanguageAdapter.SingleCheckViewHolder> {

    private static final String LOG_TAG = RecyclerViewLanguageAdapter.class.getSimpleName();

    private List<String> languages;
    private static int selectedItem;
    private static SingleClickListener singleClickListener;

    public RecyclerViewLanguageAdapter(List<String> languages, int position) {
        this.languages = languages;
        selectedItem = position;
    }

    @Override
    public SingleCheckViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lang_item, viewGroup, false);
        return new SingleCheckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SingleCheckViewHolder viewHolder, int position) {
        viewHolder.language.setText(languages.get(position));
        viewHolder.rbCheck.setChecked(selectedItem == position);
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public static class SingleCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RadioButton rbCheck;
        private TextView language;

        public SingleCheckViewHolder(View itemView) {
            super(itemView);

            language = (TextView) itemView.findViewById(R.id.tvLang);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rbCheck);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            selectedItem = getAdapterPosition();
            singleClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        singleClickListener = clickListener;
    }

    public interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }
}

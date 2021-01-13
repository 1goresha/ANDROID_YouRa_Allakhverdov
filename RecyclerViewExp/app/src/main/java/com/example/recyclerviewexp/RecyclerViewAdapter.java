package com.example.recyclerviewexp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    private List<RecyclerViewItem> recyclerViewItems;

    public RecyclerViewAdapter(List<RecyclerViewItem> recyclerViewItems){
        this.recyclerViewItems = recyclerViewItems;
    }

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder{// это обязательное наследование

        private ImageView imageResource;
        private TextView text1;
        private TextView text2;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imageResource = (ImageView)itemView.findViewById(R.id.imageView);
            text1 = (TextView)itemView.findViewById(R.id.text1);
            text2 = (TextView)itemView.findViewById(R.id.text2);
        }
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);// создается вьюшка из recycler_view_item.xml
        return new RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {// наполняет ViewHolder данными из списка элементов
        RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);

        holder.imageResource.setImageResource(recyclerViewItem.getViewResource());
        holder.text1.setText(recyclerViewItem.getText1());
        holder.text2.setText(recyclerViewItem.getText2());
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }
}

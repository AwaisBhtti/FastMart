package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecViewHolder> {

    private List<Product> recommendedList;

    public RecommendedAdapter(List<Product> recommendedList) {
        this.recommendedList = recommendedList;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        Product product = recommendedList.get(position);
        Context context = holder.itemView.getContext();
        holder.tvName.setText(product.getTitle());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.tvModel.setText(product.getDescription());
        holder.imgProduct.setImageResource(product.getImageResource());
        SharedPreferences sp = context.getSharedPreferences("fav", Context.MODE_PRIVATE);
        Set<String> savedIds = sp.getStringSet("FAV_IDS", new HashSet<>());
        product.setFavourite(savedIds.contains(product.getId()));
        if (product.isFavourite()) {
            holder.imgHeart.setColorFilter(Color.RED);
        } else {
            holder.imgHeart.setColorFilter(Color.BLACK);
        }
        holder.imgHeart.setOnClickListener(v -> {
            Set<String> currentSavedIds = sp.getStringSet("FAV_IDS", new HashSet<>());
            Set<String> editableIds = new HashSet<>(currentSavedIds);

            String clickedId = product.getId();

            if (editableIds.contains(clickedId)) {
                editableIds.remove(clickedId);
            } else {
                editableIds.add(clickedId);
            }
            sp.edit().putStringSet("FAV_IDS", editableIds).apply();
            product.setFavourite(!product.isFavourite());
            notifyItemChanged(position);
        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("NAME", product.getTitle());
            intent.putExtra("PRICE", String.format("$%.2f", product.getPrice()));
            intent.putExtra("DESC", product.getDescription());
            intent.putExtra("IMG", product.getImageResource());
            intent.putExtra("CATEGORY", product.getCategory());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, imgHeart;
        TextView tvPrice, tvName, tvModel;

        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgRecProduct);
            tvPrice = itemView.findViewById(R.id.tvRecPrice);
            tvName = itemView.findViewById(R.id.tvRecName);
            tvModel = itemView.findViewById(R.id.tvRecModel);
            imgHeart = itemView.findViewById(R.id.imgRecHeart);
        }
    }
}
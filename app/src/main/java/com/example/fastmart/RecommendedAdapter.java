package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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
        holder.tvName.setText(product.getTitle());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.tvModel.setText(product.getDescription());
        holder.imgProduct.setImageResource(product.getImageResource());

        if (product.isFavourite()) {
            holder.imgHeart.setColorFilter(Color.RED);
        } else {
            holder.imgHeart.setColorFilter(Color.BLACK);
        }

        holder.imgHeart.setOnClickListener(v -> {
            product.setFavourite(!product.isFavourite());
            notifyItemChanged(position);
        });

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("NAME", product.getTitle());
            intent.putExtra("PRICE", String.format("$%.2f", product.getPrice()));
            intent.putExtra("DESC", product.getDescription());
            intent.putExtra("IMG", product.getImageResource());
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
package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.Product;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavViewHolder> {

    private List<Product> favList;
    private OnFavRemovedListener listener;

    public interface OnFavRemovedListener {
        void onRemove(Product product);
        void onAddToCart(Product product);
    }

    public FavouritesAdapter(List<Product> favList, OnFavRemovedListener listener) {
        this.favList = favList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new FavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Product product = favList.get(position);
        holder.tvName.setText(product.getTitle());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.imgProduct.setImageResource(R.drawable.nest_mini); // Placeholder

        holder.imgHeart.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Remove Favourite")
                    .setMessage(R.string.delete_fav_msg)
                    .setPositiveButton("Remove", (dialog, which) -> {
                        listener.onRemove(product);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        holder.imgCart.setOnClickListener(v -> {
            listener.onAddToCart(product);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductActivity.class);
            intent.putExtra("ID", product.getId());
            intent.putExtra("NAME", product.getTitle());
            intent.putExtra("PRICE", String.format("$%.2f", product.getPrice()));
            intent.putExtra("DESC", product.getDescription());
            intent.putExtra("CATEGORY", product.getCategory());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgProduct, imgHeart, imgCart;

        public FavViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvFavName);
            tvPrice = v.findViewById(R.id.tvFavPrice);
            imgProduct = v.findViewById(R.id.imgFavProduct);
        }
    }
}

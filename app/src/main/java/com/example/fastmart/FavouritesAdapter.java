package com.example.fastmart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavViewHolder> {

    private List<Product> favList;
    private Context context;

    public FavouritesAdapter(List<Product> favList, Context context) {
        this.favList = favList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Product product = favList.get(position);
        holder.tvName.setText(product.getTitle());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.tvModel.setText(product.getDescription());
        holder.imgProduct.setImageResource(product.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("ID", product.getId());
            intent.putExtra("NAME", product.getTitle());
            intent.putExtra("PRICE", String.format("$%.2f", product.getPrice()));
            intent.putExtra("ORIGINAL_PRICE", String.format("$%.2f", product.getOriginalPrice()));
            intent.putExtra("DESC", product.getDescription());
            intent.putExtra("IMG", product.getImageResource());
            intent.putExtra("CATEGORY", product.getCategory());
            context.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.addItem(product);
            Toast.makeText(context, context.getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show();
        });

        holder.btnMoreOptions.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(context.getString(R.string.delete_fav_msg));

            builder.setPositiveButton("Yes", (dialog, which) -> {
                SharedPreferences sp = context.getSharedPreferences("fav", Context.MODE_PRIVATE);
                Set<String> savedIds = sp.getStringSet("FAV_IDS", new HashSet<>());
                Set<String> editableIds = new HashSet<>(savedIds);
                editableIds.remove(product.getId());
                sp.edit().putStringSet("FAV_IDS", editableIds).apply();
                favList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, favList.size());
            });

            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnAddToCart, btnMoreOptions;
        TextView tvName, tvPrice, tvModel;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgFavProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);
            tvName = itemView.findViewById(R.id.tvFavName);
            tvPrice = itemView.findViewById(R.id.tvFavPrice);
            tvModel = itemView.findViewById(R.id.tvFavModel);
        }
    }
}
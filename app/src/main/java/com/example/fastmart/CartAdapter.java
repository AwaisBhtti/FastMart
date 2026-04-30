package com.example.fastmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartList;
    private OnCartChangedListener listener;

    public interface OnCartChangedListener {
        void onQuantityChanged(String productId, int newQuantity);
        void onRemoveItem(String productId);
    }

    public CartAdapter(List<CartItem> cartList, OnCartChangedListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        // For image, we would use Glide if imageUrl was available
        holder.imgProduct.setImageResource(R.drawable.nest_mini); // Placeholder

        holder.btnPlus.setOnClickListener(v -> {
            listener.onQuantityChanged(item.getProductId(), item.getQuantity() + 1);
        });

        holder.btnMinus.setOnClickListener(v -> {
            listener.onQuantityChanged(item.getProductId(), item.getQuantity() - 1);
        });

        holder.btnMore.setOnClickListener(v -> {
            listener.onRemoveItem(item.getProductId());
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        ImageView imgProduct, btnMore;
        Button btnPlus, btnMinus;

        public CartViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvCartName);
            tvPrice = v.findViewById(R.id.tvCartPrice);
            tvQuantity = v.findViewById(R.id.tvQuantity);
            imgProduct = v.findViewById(R.id.imgCartProduct);
            btnMore = v.findViewById(R.id.btnCartMore);
            btnPlus = v.findViewById(R.id.btnPlus);
            btnMinus = v.findViewById(R.id.btnMinus);
        }
    }
}

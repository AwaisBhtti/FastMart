package com.example.fastmart;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealViewHolder> {

    private List<Product> dealsList;

    public DealsAdapter(List<Product> dealsList) {
        this.dealsList = dealsList;
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal, parent, false);
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        Product product = dealsList.get(position);

        holder.tvTitle.setText(product.getTitle());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.tvOriginalPrice.setText(String.format("$%.2f", product.getOriginalPrice()));
        holder.tvDesc.setText(product.getDescription());
        holder.imgProduct.setImageResource(product.getImageResource());
        holder.tvCategory.setText(product.getCategory());
        holder.tvOriginalPrice.setPaintFlags(holder.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
        return dealsList.size();
    }

    public static class DealViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvTitle, tvPrice, tvOriginalPrice, tvDesc, tvCategory;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgDealProduct);
            tvTitle = itemView.findViewById(R.id.tvDealTitle);
            tvPrice = itemView.findViewById(R.id.tvDealPrice);
            tvOriginalPrice = itemView.findViewById(R.id.tvDealOriginalPrice);
            tvDesc = itemView.findViewById(R.id.tvDealDesc);
            tvCategory = itemView.findViewById(R.id.tvDealCategory);
        }
    }
}
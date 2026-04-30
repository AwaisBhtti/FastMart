package com.example.fastmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.CartItem;
import com.example.fastmart.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        holder.tvDate.setText(sdf.format(new Date(order.getTimestamp())));
        
        StringBuilder itemsText = new StringBuilder();
        for (CartItem item : order.getItems()) {
            itemsText.append(item.getName()).append(" x").append(item.getQuantity())
                    .append(" ($").append(item.getPrice()).append(")\n");
        }
        holder.tvItems.setText(itemsText.toString().trim());
        holder.tvTotal.setText(String.format("Total: $%.2f", order.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvItems, tvTotal;

        public OrderViewHolder(View v) {
            super(v);
            tvDate = v.findViewById(R.id.tvOrderDate);
            tvItems = v.findViewById(R.id.tvOrderItems);
            tvTotal = v.findViewById(R.id.tvOrderTotal);
        }
    }
}

package com.lugudu.marketplanner.ui.products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.entity.Product;

import java.util.Vector;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context ctx;
    private Vector<Product> products;
    Button buttonBorrar;

    public ProductAdapter(Vector<Product> products, Context ctx) {
        this.products = products;
        this.ctx = ctx;
    }

    public void setProducts(Vector<Product> products){
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.bt_delete);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_product_name.setText(products.get(position).getName());
        holder.tv_product_price.setText("Precio: " + products.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductsForm.class);
                intent.putExtra("id", products.get(position).getId());
                intent.putExtra("name", products.get(position).getName());
                intent.putExtra("market", products.get(position).getMarket());
                intent.putExtra("price", products.get(position).getPrice());
                intent.putExtra("category", products.get(position).getCategory());
                intent.putExtra("position", position);
                intent.putExtra("modificar", "true");

                ctx.startActivity(intent);
            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_product_name, tv_product_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
        }
    }
}

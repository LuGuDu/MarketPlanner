package com.lugudu.marketplanner.ui.shoplists;

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
import com.lugudu.marketplanner.entity.ShopList;

import java.util.ArrayList;
import java.util.Vector;

public class ShopListsAdapter extends RecyclerView.Adapter<ShopListsAdapter.MyViewHolder> {

    private Context ctx;
    private Vector<ShopList> shopLists;
    Button buttonBorrar, buttonShareList;
    private int size = 0;

    public ShopListsAdapter(Vector<ShopList> shopLists, Context ctx) {
        this.shopLists = shopLists;
        this.ctx = ctx;
    }

    public void setShopList(Vector<ShopList> shopLists){
        this.shopLists = shopLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.shoplist_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.bt_delete_shoplist);
        buttonShareList = view.findViewById(R.id.bt_share);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTV.setText(shopLists.get(position).getName());
        if(shopLists.get(position).getItems() == null) {
            size = 0;
        } else {
            size = shopLists.get(position).getItems().size();
        }
        holder.sizeTV.setText("Elementos: " + size);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ShopListsForm.class);
                intent.putExtra("id", shopLists.get(position).getId());
                intent.putExtra("name", shopLists.get(position).getName());
                intent.putParcelableArrayListExtra("items", new ArrayList<>(shopLists.get(position).getItems()));
                intent.putExtra("position", position);
                intent.putExtra("modificar", "true");

                ctx.startActivity(intent);
            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopLists.remove(position);
                notifyItemRemoved(position);
            }
        });

        buttonShareList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ticketText = "Lista de la compra: " + shopLists.get(position).getName();
                for(int i = 0; i<shopLists.get(position).getItems().size(); i++){
                    ticketText += "\n - " + shopLists.get(position).getItems().get(i).getItem();
                }
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, ticketText);
                ctx.startActivity(Intent.createChooser(shareIntent, "Compartir ticket"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, sizeTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tv_shoplistname);
            sizeTV = itemView.findViewById(R.id.tv_size);
        }
    }
}

package com.lugudu.marketplanner.ui.tickets;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.entity.Ticket;

import java.util.Vector;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {

    private Context ctx;
    private Vector<Ticket> ticketList;
    Button buttonBorrar;


    public TicketsAdapter(Vector<Ticket> ticketList, Context ctx) {
        this.ticketList = ticketList;
        this.ctx = ctx;
    }

    public void setTicketList(Vector<Ticket> tickets){
        this.ticketList = tickets;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.ticket_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.bt_delete);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTV.setText(ticketList.get(position).getName());
        holder.marketTV.setText("Market: " + ticketList.get(position).getMarket());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, TicketsForm.class);
                intent.putExtra("id", ticketList.get(position).getId());
                intent.putExtra("name", ticketList.get(position).getName());
                intent.putExtra("market", ticketList.get(position).getMarket());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.putExtra("date", ticketList.get(position).getDate());
                }
                intent.putExtra("location", ticketList.get(position).getLocation());
                intent.putExtra("price", ticketList.get(position).getTotalPrice());
                intent.putExtra("position", position);
                intent.putExtra("modificar", "true");

                ctx.startActivity(intent);
            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, marketTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            marketTV = itemView.findViewById(R.id.marketTV);
        }
    }
}

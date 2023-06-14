package com.lugudu.marketplanner.ui.shoplists;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.entity.ListItem;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Vector;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context ctx;
    private List<ListItem> items;
    Button buttonBorrar;

    public ItemAdapter(List<ListItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items){
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.bt_delete_item);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.et_itemname.setText(items.get(position).getItem());
        boolean done = items.get(position).isDone();
        holder.cb_item.setChecked(done);

        holder.et_itemname.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newName = s.toString();
                ListItem item = items.get(position);
                item.setItem(newName);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        holder.cb_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListItem item = items.get(position);
                item.setDone(isChecked);
            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView et_itemname;
        CheckBox cb_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            et_itemname = itemView.findViewById(R.id.et_itemname);
            cb_item = itemView.findViewById(R.id.cb_item);
        }
    }
}

package com.lugudu.marketplanner.ui.shoplists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.databinding.FragmentShoplistsBinding;
import com.lugudu.marketplanner.databinding.FragmentTicketsBinding;
import com.lugudu.marketplanner.persistence.Items;
import com.lugudu.marketplanner.ui.tickets.TicketsViewModel;

public class ShopListsFragment extends Fragment {

    private FragmentShoplistsBinding binding;
    private RecyclerView rv_shoplist_list;
    private ShopListsAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopListsViewModel shopListsViewModel =
                new ViewModelProvider(this).get(ShopListsViewModel.class);

        binding = FragmentShoplistsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textShoplists;
        shopListsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        rv_shoplist_list = root.findViewById(R.id.rv_shoplist_list);
        adapter = new ShopListsAdapter(Items.getShopLists(), this.getContext());
        rv_shoplist_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_shoplist_list.setAdapter(adapter);

        FloatingActionButton fab_new_shoplist= root.findViewById(R.id.fab_new_shoplist);
        fab_new_shoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext().getApplicationContext(), ShopListsForm.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ShopListsAdapter(Items.getShopLists(), this.getContext());
        adapter.setShopList(Items.getShopLists());
        rv_shoplist_list.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
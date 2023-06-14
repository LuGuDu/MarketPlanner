package com.lugudu.marketplanner.ui.products;

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
import com.lugudu.marketplanner.databinding.FragmentProductsBinding;
import com.lugudu.marketplanner.databinding.FragmentTicketsBinding;
import com.lugudu.marketplanner.persistence.Items;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;
    private RecyclerView productsRV;
    private ProductAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductsViewModel productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProducts;
        productsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        productsRV = root.findViewById(R.id.rv_products_list);
        adapter = new ProductAdapter(Items.getProducts(), this.getContext());
        productsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        productsRV.setAdapter(adapter);

        FloatingActionButton fab_new_product = root.findViewById(R.id.fab_new_product);
        fab_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext().getApplicationContext(), ProductsForm.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ProductAdapter(Items.getProducts(), this.getContext());
        adapter.setProducts(Items.getProducts());
        productsRV.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
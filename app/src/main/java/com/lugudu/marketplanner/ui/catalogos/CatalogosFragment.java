package com.lugudu.marketplanner.ui.catalogos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.databinding.FragmentCatalogosBinding;

public class CatalogosFragment extends Fragment {

    private FragmentCatalogosBinding binding;

    ImageButton bt_dia, bt_carrefour, bt_aldi, bt_lidl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CatalogosViewModel catalogosViewModel =
                new ViewModelProvider(this).get(CatalogosViewModel.class);

        binding = FragmentCatalogosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvTitle;
        catalogosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        bt_dia = root.findViewById(R.id.bt_dia);
        bt_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.dia.es/tiendas/buscador-tiendas-folletos";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        bt_carrefour = root.findViewById(R.id.bt_carrefour);
        bt_carrefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://folleto.carrefour.es/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        bt_aldi = root.findViewById(R.id.bt_aldi);
        bt_aldi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.aldi.es/folleto.html";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        bt_lidl = root.findViewById(R.id.bt_lidl);
        bt_lidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.lidl.es/es/descubre-nuevas-ofertas-cada-semana/s1160";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}





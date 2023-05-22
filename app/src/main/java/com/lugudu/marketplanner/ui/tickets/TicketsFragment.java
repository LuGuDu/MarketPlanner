package com.lugudu.marketplanner.ui.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.databinding.FragmentTicketsBinding;

public class TicketsFragment extends Fragment {

    private FragmentTicketsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketsViewModel ticketsViewModel =
                new ViewModelProvider(this).get(TicketsViewModel.class);

        binding = FragmentTicketsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTickets;
        ticketsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        FloatingActionButton fab_new_ticket = root.findViewById(R.id.fab_new_ticket);
        fab_new_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext().getApplicationContext(), TicketsForm.class);
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
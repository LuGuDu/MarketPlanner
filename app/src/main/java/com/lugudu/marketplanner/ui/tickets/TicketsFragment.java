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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.databinding.FragmentTicketsBinding;
import com.lugudu.marketplanner.entity.Ticket;
import com.lugudu.marketplanner.persistence.Items;

import java.util.Vector;

public class TicketsFragment extends Fragment {

    private FragmentTicketsBinding binding;
    private RecyclerView ticketsRV;
    private TicketsAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketsViewModel ticketsViewModel =
                new ViewModelProvider(this).get(TicketsViewModel.class);

        binding = FragmentTicketsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textTickets;
        ticketsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ticketsRV = root.findViewById(R.id.rv_tickets_list);
        adapter = new TicketsAdapter(Items.getTickets(), this.getContext());
        ticketsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        ticketsRV.setAdapter(adapter);

        //

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
    public void onResume() {
        super.onResume();
        System.out.println(Items.getTickets().size());
        adapter = new TicketsAdapter(Items.getTickets(), this.getContext());
        adapter.setTicketList(Items.getTickets());
        ticketsRV.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.lugudu.marketplanner.ui.tickets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.lugudu.marketplanner.MainActivity;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.databinding.ActivityTicketsGraphicsBinding;
import com.lugudu.marketplanner.entity.Ticket;
import com.lugudu.marketplanner.persistence.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TicketsGraphics extends AppCompatActivity {

    private BarChart barChart;

    private ActivityTicketsGraphicsBinding binding;

    Button btn_regresar;
    TextView tv_gastosTotales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_graphics);

        binding = ActivityTicketsGraphicsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View root = binding.getRoot();

        barChart = findViewById(R.id.bar_chart);
        setupBarChart();
        loadChartData();

        tv_gastosTotales = root.findViewById(R.id.tv_gastos);
        Vector<Ticket> tickets = Items.getTickets();
        double gastos = 0;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            gastos += ticket.getTotalPrice();
        }
        tv_gastosTotales.setText("Gastos totales: " + Double.toString(gastos) + " euros");

        btn_regresar = root.findViewById(R.id.bt_regresar);
        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    public void back() {
        Fragment TicketsFragment = new TicketsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, TicketsFragment);
        fragmentTransaction.commit();

        finish();
    }

    private void setupBarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
    }

    private void loadChartData() {
        Vector<Ticket> tickets = Items.getTickets();
        List<BarEntry> entries = new ArrayList<>();
        List<String> xLabels = new ArrayList<>();


        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            //float xValue = i + 1;
            float yValue = (float) ticket.getTotalPrice();

            entries.add(new BarEntry(i, yValue));
            xLabels.add(ticket.getDate().toString());
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setDrawAxisLine(true); // Mostrar línea del eje X

        BarDataSet dataSet = new BarDataSet(entries, "Tickets");
        dataSet.setColor(Color.DKGRAY);


        BarData data = new BarData(dataSet);
        data.setBarWidth(0.3f);

        data.setBarWidth(0.3f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                for (int i = 0; i < tickets.size(); i++) {
                    Ticket ticket = tickets.get(i);
                    return ticket.getName();
                }
                return "";
            }
        });

        barChart.setData(data);
        barChart.invalidate();
        barChart.getAxisRight().setEnabled(false);

    }
}

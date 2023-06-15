package com.lugudu.marketplanner.ui.shoplists;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lugudu.marketplanner.MainActivity;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.entity.ListItem;
import com.lugudu.marketplanner.entity.ShopList;
import com.lugudu.marketplanner.persistence.Items;

import java.util.ArrayList;
import java.util.List;


public class ShopListsForm extends AppCompatActivity {

    private EditText et_name;

    private Button btnGuardar, bt_agregar_item;

    String listId, listName, modificar;
    private List<ListItem> listItems;
    int position;

    private RecyclerView rv_items;
    private ItemAdapter adapter;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist_form);

        listItems = new ArrayList<>();
        et_name = findViewById(R.id.et_name);

        btnGuardar = findViewById(R.id.button_save);
        bt_agregar_item = findViewById(R.id.bt_agregar_item);
        rv_items = findViewById(R.id.rv_items);

        Intent intent = getIntent();
        listId = intent.getStringExtra("id");
        listName = intent.getStringExtra("name");
        listItems = intent.getParcelableArrayListExtra("items");
        if(listItems == null){
            listItems = new ArrayList<>();
        }
        position = intent.getIntExtra("position", 0);
        modificar = intent.getStringExtra("modificar");

        adapter = new ItemAdapter(listItems, this.getApplicationContext());
        rv_items.setAdapter(adapter);

        if(modificar == null){
            modificar = "false";
        }

        if(modificar.equals("true")){
            //MODIFICAR TICKET
            btnGuardar.setText("Modificar");
            et_name.setText(listName);

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = et_name.getText().toString();

                    ShopList shopList = new ShopList(name, listItems);
                    shopList.setId(listId);

                    //guardar lista en base de datos
                    Items.removeShopList(position);
                    Items.addShopList(shopList);
                    Items.saveShoplists(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ShopListsForm.this, "Lista actualizada", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //CREAR TICKET
            btnGuardar.setText("Crear");

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = et_name.getText().toString();
                    ShopList shopList = new ShopList(name, listItems);

                    //guardar ticket en base de datos
                    Items.addShopList(shopList);
                    Items.saveShoplists(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ShopListsForm.this, "Lista creada", Toast.LENGTH_SHORT).show();
                }
            });
        }

        bt_agregar_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem newItem = new ListItem("Nuevo elemento", false);
                listItems.add(newItem);
                adapter = new ItemAdapter(listItems, getApplicationContext());
                rv_items.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        rv_items.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(listItems, this.getApplicationContext());
        rv_items.setAdapter(adapter);
    }
}
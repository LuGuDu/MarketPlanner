package com.lugudu.marketplanner.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lugudu.marketplanner.MainActivity;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.entity.Product;
import com.lugudu.marketplanner.persistence.Items;

public class ProductsForm extends AppCompatActivity {

    private EditText et_market;
    private EditText et_totalPrice;
    private EditText et_name;
    private EditText et_category;
    private Button btnGuardar;

    String productId, productName, productMarket, productCategory, modificar;
    double productPrice;
    int position;

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
        setContentView(R.layout.activity_products_form);

        et_market = findViewById(R.id.et_product_market);
        et_totalPrice = findViewById(R.id.et_product_price);
        et_name = findViewById(R.id.et_product_name);
        et_category = findViewById(R.id.et_product_category);

        btnGuardar = findViewById(R.id.button_save);

        Intent intent = getIntent();
        productId = intent.getStringExtra("id");
        productName = intent.getStringExtra("name");
        productMarket = intent.getStringExtra("market");
        productCategory = intent.getStringExtra("category");
        productPrice = intent.getDoubleExtra("price", 0.0);
        position = intent.getIntExtra("position", 0);
        modificar = intent.getStringExtra("modificar");

        if(modificar == null){
            modificar = "false";
        }

        if(modificar.equals("true")){
            //MODIFICAR TICKET
            btnGuardar.setText("Modificar");
            et_category.setText(productCategory);
            et_market.setText(productMarket);
            et_totalPrice.setText(Double.toString(productPrice));
            et_name.setText(productName);

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!checkForm()){
                        Toast.makeText(ProductsForm.this, "Formato incorrecto", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String category = et_category.getText().toString();
                    String market = et_market.getText().toString();
                    Double price = Double.parseDouble(et_totalPrice.getText().toString());
                    String name = et_name.getText().toString();

                    Product product = new Product(market, price, name, category);
                    product.setId(productId);

                    //guardar ticket en base de datos
                    Items.removeProduct(position);
                    Items.addProduct(product);
                    Items.saveProducts(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ProductsForm.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //CREAR PRODUCTO
            btnGuardar.setText("Crear");

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!checkForm()){
                        Toast.makeText(ProductsForm.this, "Formato incorrecto", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String category = et_category.getText().toString();
                    String market = et_market.getText().toString();
                    Double price = Double.parseDouble(et_totalPrice.getText().toString());
                    String name = et_name.getText().toString();

                    Product product = new Product(market, price, name, category);

                    //guardar ticket en base de datos
                    Items.addProduct(product);
                    Items.saveProducts(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ProductsForm.this, "Producto guardado", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public boolean checkForm(){
        boolean correct = true;

        if (et_category.getText() == null || et_category.getText().toString().trim().length() == 0 ){
            correct = false;
        } else if (et_market.getText() == null || et_market.getText().toString().trim().length() == 0){
            correct = false;
        } else if (et_totalPrice.getText() == null || Double.parseDouble(et_totalPrice.getText().toString()) == 0.0){
            correct = false;
        } else if (et_name.getText() == null || et_name.getText().toString().trim().length() == 0){
            correct = false;
        }

        return correct;
    }
}
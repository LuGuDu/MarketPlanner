package com.lugudu.marketplanner.ui.tickets;

import com.lugudu.marketplanner.entity.Ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lugudu.marketplanner.MainActivity;
import com.lugudu.marketplanner.R;
import com.lugudu.marketplanner.persistence.Items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class TicketsForm extends AppCompatActivity {

    private EditText et_location;
    private EditText et_market;
    private EditText et_totalPrice;
    private EditText et_name;
    private EditText et_date;
    private Button btnGuardar, btnEscanear;

    String ticketId, ticketName, ticketMarket, ticketDate, ticketLocation, modificar;
    double ticketPrice;
    int position;

    private static final int CAMERA_PERMISSION_REQUEST = 1001;
    private static final int CAMERA_REQUEST = 1002;

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
        setContentView(R.layout.activity_tickets_form);

        et_location = findViewById(R.id.et_location);
        et_market = findViewById(R.id.et_market);
        et_totalPrice = findViewById(R.id.et_totalPrice);
        et_name = findViewById(R.id.et_name);
        et_date = findViewById(R.id.et_date);

        btnGuardar = findViewById(R.id.button_save);
        btnEscanear = findViewById(R.id.bt_foto);

        Intent intent = getIntent();
        ticketId = intent.getStringExtra("id");
        ticketName = intent.getStringExtra("name");
        ticketMarket = intent.getStringExtra("market");
        ticketLocation = intent.getStringExtra("location");
        ticketDate = intent.getStringExtra("date");
        ticketPrice = intent.getDoubleExtra("price", 0.0);
        position = intent.getIntExtra("position", 0);
        modificar = intent.getStringExtra("modificar");

        if(modificar == null){
            modificar = "false";
        }

        if(modificar.equals("true")){
            //MODIFICAR TICKET
            btnGuardar.setText("Modificar");
            et_location.setText(ticketLocation);
            et_market.setText(ticketMarket);
            et_totalPrice.setText(Double.toString(ticketPrice));
            et_name.setText(ticketName);
            if(ticketDate!=null){
                et_date.setText(ticketDate);
            }

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crea un objeto Ticket con los valores ingresados por el usuario
                    String location = et_location.getText().toString();
                    String market = et_market.getText().toString();
                    Double totalPrice = Double.parseDouble(et_totalPrice.getText().toString());
                    String name = et_name.getText().toString();
                    LocalDate date = null;
/*                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date = LocalDate.parse(et_date.getText().toString());
                }*/
                    Ticket ticket = new Ticket(location, market, totalPrice, name, date);
                    ticket.setId(ticketId);

                    //guardar ticket en base de datos
                    System.out.println(position);
                    Items.remove(position);
                    Items.addTicket(ticket);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                    // Muestra un mensaje o realiza alguna acción después de guardar el ticket
                    Toast.makeText(TicketsForm.this, "Ticket actualizado", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //CREAR TICKET
            btnGuardar.setText("Crear");

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crea un objeto Ticket con los valores ingresados por el usuario
                    String location = et_location.getText().toString();
                    String market = et_market.getText().toString();
                    Double totalPrice = Double.parseDouble(et_totalPrice.getText().toString());
                    String name = et_name.getText().toString();
                    LocalDate date = null;
/*                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date = LocalDate.parse(et_date.getText().toString());
                }*/
                    Ticket ticket = new Ticket(location, market, totalPrice, name, date);

                    //guardar ticket en base de datos
                    Items.addTicket(ticket);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                    // Muestra un mensaje o realiza alguna acción después de guardar el ticket
                    Toast.makeText(TicketsForm.this, "Ticket guardado", Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //permisos de camara
                // Verificar si se tienen los permisos necesarios
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Si los permisos están concedidos, abrir la cámara
                    abrirCamara();
                } else {
                    // Si los permisos no están concedidos, solicitarlos al usuario
                    ActivityCompat.requestPermissions(TicketsForm.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
                }
            }
        });
    }

    // Método para abrir la cámara
    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    // Manejar la respuesta de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Manejar el resultado de abrir la cámara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Obtener la foto capturada
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Guardar la foto en el dispositivo (puedes cambiar el directorio y nombre de archivo según tus necesidades)
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "IMG_" + timeStamp + ".jpg";

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, imageFileName);

            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Toast.makeText(this, "Foto guardada en: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
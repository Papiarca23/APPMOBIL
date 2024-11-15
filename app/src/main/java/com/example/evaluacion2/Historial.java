package com.example.evaluacion2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class Historial extends AppCompatActivity {
    private ListView lvAcciones;
    private ArrayList<String> historialAcciones = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        lvAcciones = findViewById(R.id.lvAcciones);

        historialAcciones.add("Dispositivo 'Puerta Bodega' abierto el 15/10/2024 10:00 AM");
        historialAcciones.add("Dispositivo 'Puerta Bodega' cerrado el 15/10/2024 10:05 AM");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historialAcciones);
        lvAcciones.setAdapter(adapter);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
package co.edu.unipiloto.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private boolean running;
    private int segundos = 0;
    private ArrayList<String> vueltas;
    private ArrayAdapter<String> adapter;
    private int vueltasCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.lista_vueltas);
        vueltas = new ArrayList<>();

        if (savedInstanceState != null) {
            segundos = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            vueltas = savedInstanceState.getStringArrayList("vueltas");
            vueltasCount = savedInstanceState.getInt("vueltasCount");
            actualizarAdaptador();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vueltas);
        listView.setAdapter(adapter);
        runTimer();
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int horas = segundos / 3600;
                int minutos = (segundos % 3600) / 60;
                int secs = segundos % 60;
                String tiempo = String.format(Locale.getDefault(), "%d:%02d:%02d", horas, minutos, secs);
                timeView.setText(tiempo);
                if (running) {
                    segundos++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onClickPause(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        segundos = 0;
        vueltas.clear();
        vueltasCount = 0;
        adapter.notifyDataSetChanged();
    }

    public void onClickStart(View view) {
        if (vueltasCount < 5) {
            running = true;
        }
    }

    public void onClickLap(View view) {
        if (running && vueltasCount < 5) {
            vueltasCount++;
            String tiempoActual = ((TextView) findViewById(R.id.time_view)).getText().toString();
            vueltas.add(tiempoActual);
            adapter.notifyDataSetChanged();
            if (vueltasCount == 5) {
                running = false;
            }
        }
    }

    private void actualizarAdaptador() {
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vueltas);
            ListView listView = findViewById(R.id.lista_vueltas);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", segundos);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putStringArrayList("vueltas", vueltas);
        savedInstanceState.putInt("vueltasCount", vueltasCount);
    }

}
package co.edu.unipiloto.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private boolean running;
    private int segundos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            segundos = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", segundos);
        savedInstanceState.putBoolean("running", running);
    }


    private void runTimer() {
        TextView timeview=(TextView) findViewById(R.id.time_view);
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int horas= segundos/3600;
                int minutos=(segundos%3600)/60;
                int secs = segundos%60;
                String tiempo=String.format(Locale.getDefault(),"%d:%02d:%02d",horas,minutos,secs);
                timeview.setText(tiempo);
                if(running)
                    segundos++;
                handler.postDelayed(this,1000);
            }
        });
    }

    public void onClickStart(View view) {
        running=true;
    }
    public void onClickPause(View view) {
        super.onPause();
        running = false;
    }

    public void OnClickReset(View view) {
        running=false;
        segundos=0;
    }


}
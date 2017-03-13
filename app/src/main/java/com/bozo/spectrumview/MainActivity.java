package com.bozo.spectrumview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Handler handler;

    private SpectrumView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        sv = (SpectrumView) findViewById(R.id.sv);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                handler.post(runnable);
                break;
            case R.id.btn_stop:
                handler.removeCallbacks(runnable);
                break;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sv.addSpectrum(new Random().nextInt(20));
            handler.postDelayed(this, 100);
        }
    };
}

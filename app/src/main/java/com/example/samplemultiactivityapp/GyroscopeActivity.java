package com.example.samplemultiactivityapp;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GyroscopeActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_luminosity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TextView textView = (TextView) findViewById(R.id.textView6);
        new MultiSensorAccess(sensorManager, textView, android.hardware.Sensor.TYPE_GYROSCOPE, "ufg/2025/gyroscope");

        TextView title = (TextView) findViewById(R.id.textView5);
        title.setText("Leitura do Sensor Giroscópio");
    }

    public void backButtonClick2(View view) {
        finish();
    }
}

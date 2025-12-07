package com.example.samplemultiactivityapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MultiSensorAccess implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mSensor;
    private TextView sensor_field;

    SensorPublisher sp;

    public MultiSensorAccess(SensorManager sm, TextView tv, int sensorType, String mqttTopic){
        sensorManager = sm;
        sensor_field = tv;
        mSensor = sensorManager.getDefaultSensor(sensorType);
        sp = new SensorPublisher(tv, mqttTopic);
        if (mSensor != null) {
            sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensor_field.setText("Sensor não disponível");
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Opcional: lidar com mudança de precisão
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < event.values.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(event.values[i]);
        }
        String payload = sb.toString();
        // Mostrar leitura no TextView
        sensor_field.setText(payload);
        // Publicar via MQTT
        sp.publishSensor(payload);
    }

    @Override
    protected void finalize() {
        try {
            sensorManager.unregisterListener(this);
        } catch (Exception ignored) {}
    }
}

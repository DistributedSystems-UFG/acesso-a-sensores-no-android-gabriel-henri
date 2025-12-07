package com.example.samplemultiactivityapp;

import android.widget.TextView;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class SensorPublisher {

    // Configurações MQTT
    private final String MQTT_BROKER = "broker.hivemq.com";  // ou seu broker
    private final int MQTT_PORT = 1883;
    private final String DEFAULT_TOPIC = "ufg/2025/lightlevel";

    private TextView sensorField;
    private String mqttTopic;

    public SensorPublisher(TextView tv) {
        sensorField = tv;
        mqttTopic = DEFAULT_TOPIC;
    }

    public SensorPublisher(TextView tv, String topic) {
        sensorField = tv;
        mqttTopic = topic != null ? topic : DEFAULT_TOPIC;
    }

    public SensorPublisher(String topic) {
        mqttTopic = topic != null ? topic : DEFAULT_TOPIC;
    }

    public void publishSensor() {
        if (sensorField == null) return;
        publishSensor(sensorField.getText().toString());
    }

    public void publishSensor(String payload) {
        if (payload == null) payload = "";
        Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(MQTT_BROKER)
                .buildBlocking();
        client.connect();
        client.publishWith()
                .topic(mqttTopic)
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(payload.getBytes(StandardCharsets.UTF_8))
                .send();
        client.disconnect();
    }
}

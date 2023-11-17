package com.example.smarthomeandroid.utils.mqtt;

import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;

public class MqttHandler {
    private String brokerUrl = "tcp://broker.emqx.io:1883";
    private String clientId = "android_client";
    private String username = "chocolee";
    private String password = "123";
    private MqttClient client;

    public void connect() {
        try {
            // Set up the persistence layer
            MemoryPersistence persistence = new MemoryPersistence();

            // Initialize the MQTT client
            client = new MqttClient(brokerUrl, clientId, persistence);

            // Set up the connection options
            MqttConnectOptions connectOptions = new MqttConnectOptions();

            connectOptions.setCleanSession(true);
            connectOptions.setUserName(username);

            connectOptions.setPassword(password.toCharArray());
            client.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                // 連接丟失時的處理
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String receivedMessage = new String(message.getPayload());
                // 接收到新消息時的處理
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // 消息交付完成時的處理
            }
        });

    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, DataRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        try {
            MqttMessage mqttMessage = new MqttMessage(json.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic, 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void unSubscribe(String topic) {
        try {
            client.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
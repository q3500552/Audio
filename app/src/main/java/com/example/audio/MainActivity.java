package com.example.audio;

import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.Socket;
import java.net.URI;


public class MainActivity extends AppCompatActivity {
    public static WebSocketClient webSocketClient;
    public static MediaRecorder recorder;
    public static MOutputStream mOutputStream = null;
    public static Socket mSocket = null;
    public static byte[] bytes;
    public static AudioRecord audioRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, 1, AudioFormat.ENCODING_PCM_16BIT, 1024);

        try {
            webSocketClient = new WebSocketClient(URI.create("ws://192.168.0.5:9001/websocket")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {

                    bytes = new byte[1024];
                    audioRecord.startRecording();
                }

                @Override
                public void onMessage(String message) {
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                }

                @Override
                public void onError(Exception ex) {
                }
            };
            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }





        String[] perms = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this,
                    "Need permissions for camera & microphone",
                    0,
                    perms);
        }

        setContentView(R.layout.activity_main);


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    public void stop(View view) {
        audioRecord.stop();
        audioRecord.release();
        webSocketClient.close();
    }

    public void START(View view) {
        while(true){
            audioRecord.read(bytes, 0, bytes.length);
            String txt = Base64.encodeToString(bytes, Base64.DEFAULT);
//            webSocketClient.send(bytes);
            webSocketClient.send(txt);
        }
    }
}

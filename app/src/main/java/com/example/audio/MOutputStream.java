package com.example.audio;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MOutputStream extends FileOutputStream {
    public MOutputStream(String name) throws FileNotFoundException {
        super(name);
        Log.e("asd", "123");
    }

    @Override
    public void close() throws IOException {
        super.close();
        Log.e("asd", "123");
    }

    @Override
    public FileChannel getChannel() {
        Log.e("asd", "123");
        return super.getChannel();
    }


    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        MainActivity.webSocketClient.send("3210");
        Log.e("asd", "123");
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        MainActivity.webSocketClient.send("3210");
        Log.e("asd", "123");
    }

    @Override
    protected void finalize() throws IOException {
        super.finalize();
        MainActivity.webSocketClient.send("3210");
        Log.e("asd", "123");
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
        MainActivity.webSocketClient.send("3210");
        Log.e("asd", "123");
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        MainActivity.webSocketClient.send("3210");
        Log.e("asd", "123");
    }
}

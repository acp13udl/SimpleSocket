package com.msi.manning.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Adrian on 12/3/15.
 */
public class SocketThread extends Thread {

    private String ip;
    private String port;
    private String inputtext;
    private Handler handler;

    public SocketThread(String ip, String port, String inputtext ,Handler handler){
        this.ip = ip;
        this.port = port;
        this.inputtext = inputtext;
        this.handler = handler;
    }

    @Override
    public void run(){

        Socket socket = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        String output = null;

        try {
            socket = new Socket(ip, Integer.parseInt(port));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send input terminated with \n
            String input = inputtext;
            writer.write(input + "\n", 0, input.length() + 1);
            writer.flush();

            // read back output
            output = reader.readLine();

            // send EXIT and close
            writer.write("EXIT\n", 0, 5);
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                // swallow
            }
            try {
                reader.close();
            } catch (IOException e) {
                // swallow
            }
            try {
                socket.close();
            } catch (IOException e) {
                // swallow
            }
        }
        Message msg = handler.obtainMessage();
        msg.obj = output;
        handler.sendMessage(msg);
    }
}

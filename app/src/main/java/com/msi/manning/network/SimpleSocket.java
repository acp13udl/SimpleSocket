package com.msi.manning.network;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Android direct to Socket example.
 * 
 * For this to work you need a server listening on the IP address and port specified. See the
 * NetworkSocketServer project for an example.
 * 
 * 
 * @author charliecollins
 * 
 */
public class SimpleSocket extends Activity {

    private static final String CLASSTAG = SimpleSocket.class.getSimpleName();

    private EditText ipAddress;
    private EditText port;
    private EditText socketInput;
    private TextView socketOutput;
    private Button socketButton;
    private Handler handler = new Handler();

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.simple_socket);

        this.ipAddress = (EditText) findViewById(R.id.socket_ip);
        this.port = (EditText) findViewById(R.id.socket_port);
        this.socketInput = (EditText) findViewById(R.id.socket_input);
        this.socketOutput = (TextView) findViewById(R.id.socket_output);
        this.socketButton = (Button) findViewById(R.id.socket_button);

        this.socketButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {

                socketOutput.setText("");
                handler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    String output = (String) msg.obj;
                    socketOutput.setText(output);
                }
            };

            SocketThread thread = new SocketThread(ipAddress.getText().toString(), port.getText().toString(), socketInput.getText().toString(), handler);
            thread.start();

            }
        });
    }
}

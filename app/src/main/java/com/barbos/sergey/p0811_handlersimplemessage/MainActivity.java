package com.barbos.sergey.p0811_handlersimplemessage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "Sergey";

    final int STATUS_NONE = 0;
    final int STATUS_CONNECTING = 1;
    final int STATUS_CONNECTED = 2;

    Handler mHandler;
    TextView mStatusTv;
    Button mButtonConnect;
    ProgressBar mProgressBarConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusTv = (TextView) findViewById(R.id.tvStatus);
        mButtonConnect = (Button) findViewById(R.id.btnConnect);
        mProgressBarConnect = (ProgressBar) findViewById(R.id.pbConnect);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        mButtonConnect.setEnabled(true);
                        mStatusTv.setText("Not connected");
                        break;
                    case STATUS_CONNECTING:
                        mButtonConnect.setEnabled(false);
                        mStatusTv.setText("Connecting");
                        mProgressBarConnect.setVisibility(View.VISIBLE);
                        break;
                    case STATUS_CONNECTED:
                        mStatusTv.setText("Connected");
                        mProgressBarConnect.setVisibility(View.GONE);
                        break;
                    default: break;
                }
            }
        };
        mHandler.sendEmptyMessage(STATUS_NONE);
    }

    public void onclick(View view) {
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mHandler.sendEmptyMessage(STATUS_CONNECTING);

                    TimeUnit.SECONDS.sleep(2);

                    mHandler.sendEmptyMessage(STATUS_CONNECTED);

                    TimeUnit.SECONDS.sleep(3);

                    mHandler.sendEmptyMessage(STATUS_NONE);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }
}

package com.example.lab4_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;
    TextView downloadProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);

        //downloadProgressText=new TextView(this);
        downloadProgressText = (TextView) findViewById(R.id.downloadProgressText);
    }

    int finalDownloadProgress;

    public void mockFileDownloader() {
        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress+10) {
            finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downloadProgressText.setText("Download Progress: " + finalDownloadProgress + "%");
                    startButton.setText("DOWNLOADING...");

                }
            });

            if(stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //finalDownloadProgress = finalDownloadProgress1;
                        downloadProgressText.setText("");
                        startButton.setText("Start");

                    }
                });
                return;
            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                downloadProgressText.setText("");
                startButton.setText("Start");
            }
        });
    }

    public void startDownload(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view) {
        stopThread = true;
    }

    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }

}

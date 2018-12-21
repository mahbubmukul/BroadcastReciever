package com.lazycoder.broadcastreciever;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConnectionReceiver receiver;
    IntentFilter intentFilter;
    SMSReaderBroadcastReceiver smsReaderBroadcastReceiver;

    private static final int REQUEST_CODE_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new ConnectionReceiver();
        smsReaderBroadcastReceiver = new SMSReaderBroadcastReceiver();
        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        attemptRequestForPermission();

        smsReaderBroadcastReceiver.setOnTextMessageReceivedListener(new SMSReaderBroadcastReceiver.OnTextMessageReceivedListener() {
            @Override
            public void onTextMessageReceive(String otp) {
//                mOTPEditText.setText(otp);
//                mActivateButton.performClick();

            }
        });

        //enable broadcast receiver to get the text message to get the OTP
//        mEnableDisableSMSBroadcastReceiver = new EnableDisableSMSBroadcastReceiver();
//
//        mEnableDisableSMSBroadcastReceiver.enableBroadcastReceiver(getContext(), new SMSReaderBroadcastReceiver.OnTextMessageReceivedListener() {
//            @Override
//            public void onTextMessageReceive(String otp) {
//                mOTPEditText.setText(otp);
//                mActivateButton.performClick();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void attemptRequestForPermission() {
        String[] requiredPermissions = {Manifest.permission.READ_SMS};

        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                    REQUEST_CODE_PERMISSION);
        }
    }
}

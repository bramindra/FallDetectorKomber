package com.example.dimitris.falldetector.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.dimitris.falldetector.core.ReqPermission;

import com.example.dimitris.falldetector.R;

public class MainActivity extends ReqPermission implements View.OnClickListener {

    private static final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermission(new String[]{Manifest.permission.CALL_PHONE}, R.string.msg, REQUEST_PERMISSION);

        Button btnSet = findViewById(R.id.btn_set);
        btnSet.setOnClickListener(this);

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);

        Button btnHistory = findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(this);
    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_set:
                intent = new Intent(this, SetActivity.class);
                startActivity(intent);
                return;
            case R.id.btn_start:
                intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                return;
            case R.id.btn_history:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
        }
    }

}

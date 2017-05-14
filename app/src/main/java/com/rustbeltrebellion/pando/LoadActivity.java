package com.rustbeltrebellion.pando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
    }

    public void LoadFile(View view) {
        Intent intent = new Intent(this, FileActivity.class);
        startActivity(intent);
    }

    public void LoadUrl(View view) {
        Intent intent = new Intent(this, UrlActivity.class);
        startActivity(intent);
    }

    public void LoadQr(View view) {
        Intent intent = new Intent(this, QrActivity.class);
        startActivity(intent);
    }

}

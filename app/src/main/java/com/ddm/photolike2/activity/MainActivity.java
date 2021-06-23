package com.ddm.photolike2.activity;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ddm.photolike2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleBtnNovoPostClick(View view) {
        Intent i = new Intent(this, NovoPostActivity.class);
        startActivity(i);
    }
}
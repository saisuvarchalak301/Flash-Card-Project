package com.example.flashlearn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class FlashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(FlashScreen.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // 2 seconds delay
    }
}

package com.nightxstudio.tapcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class AboutActivity extends AppCompatActivity {

    ImageButton instagramButton;
    ImageButton twitterButton;
    TextView year;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        instagramButton = findViewById(R.id.instagramButton);
        twitterButton = findViewById(R.id.twitterButton);
        year = findViewById(R.id.year);

        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/saipritampanda/");
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://twitter.com/SaiPritamPanda1");
            }
        });

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        year.setText("© TapCounter" + currentYear + " ");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);

        ColorDrawable actionBackground = new ColorDrawable(Color.parseColor("#FF000000"));
        actionBar.setBackgroundDrawable(actionBackground);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent mainActivityIntent = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mainActivityIntent = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
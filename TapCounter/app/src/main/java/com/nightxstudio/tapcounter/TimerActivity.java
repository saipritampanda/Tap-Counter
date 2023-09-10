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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TimerActivity extends AppCompatActivity {

    TextView countText;
    FloatingActionButton targetButton;
    ImageView pauseImg;
    TextView timerSlot;
    ImageButton setTime;
    TextView tapSpace;
    Button reset;
    Button pause;

//  IN-APP CONSTANTS & LITERALS:
    long i = 0;
    long target = 50;
    Boolean activeState = true;
    long timeSeconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countText = findViewById(R.id.countText);
        targetButton = findViewById(R.id.targetButton);
        pauseImg = findViewById(R.id.pauseImg);
        timerSlot = findViewById(R.id.timerSlot);
        setTime = findViewById(R.id.setTime);
        tapSpace = findViewById(R.id.tapSpace);
        reset = findViewById(R.id.reset);
        pause = findViewById(R.id.pause);

        MediaPlayer resetMedia =MediaPlayer.create(this , R.raw.reset);
        MediaPlayer pauseMedia =MediaPlayer.create(this , R.raw.gamepause);
        MediaPlayer resultMedia =MediaPlayer.create(this , R.raw.gamewin);


//  1. CREATING ALERT-DIALOGUE BOX TO SET TARGET:
        AlertDialog.Builder targetBoxBuilder = new AlertDialog.Builder(this);
        targetBoxBuilder.setTitle("Set Target");
        targetBoxBuilder.setMessage("By default the target is set to 50.");
        targetBoxBuilder.setIcon(R.drawable.tap_counter_icon);

        //SET THE INPUT EDIT-TEXT FOR USER TO INPUT VALUES
        EditText targetInputNumber = new EditText(this);
        targetBoxBuilder.setView(targetInputNumber);
        targetInputNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        targetBoxBuilder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String targetString = targetInputNumber.getText().toString();
                target = Long.parseLong(targetString);
                targetToast();
            }
        });
        targetBoxBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Typeface face = Typeface.createFromAsset(getAssets() , "fonts/quantico");
        //targetinputNumber.setTypeface(face);
        final AlertDialog targetBox = targetBoxBuilder.create();

//  2. CREATING ALERT-DIALOGUE BOX TO SHOW RESULT:
        AlertDialog.Builder resultBuilder = new AlertDialog.Builder(this);
        resultBuilder.setTitle("Congratulation");
        resultBuilder.setMessage("You have successfully achieved your target!");
        resultBuilder.setIcon(R.drawable.tap_counter_icon);
        resultBuilder.setPositiveButton("Re-Try", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        resultBuilder.setNegativeButton("Cancel", null);
        final AlertDialog result = resultBuilder.create();

//  3. CREATING ALERT-DIALOGUE BOX TO ACCEPT TIME FROM USER:
        AlertDialog.Builder timerBoxBuilder = new AlertDialog.Builder(this);
        timerBoxBuilder.setTitle("Set Time");
        timerBoxBuilder.setMessage("How much time (seconds) would you like to continue your session ?");
        timerBoxBuilder.setIcon(R.drawable.tap_counter_icon);

        //SET THE INPUT EDIT-TEXT FOR USER TO INPUT VALUES
        EditText inputTimeBox = new EditText(this);
        timerBoxBuilder.setView(inputTimeBox);
        inputTimeBox.setInputType(InputType.TYPE_CLASS_NUMBER);

        timerBoxBuilder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String timeString = inputTimeBox.getText().toString();
                timeSeconds = Long.parseLong(timeString);
                timerToast();

                //COUNT-DOWN TIMER:
                new CountDownTimer(timeSeconds*1000, 1000) {
                    public void onTick(long millisInFuture) {
                        // Used for formatting digit to be in 2 digits only
                        NumberFormat f = new DecimalFormat("00");
                        long hour = (millisInFuture / 3600000) % 24;
                        long min = (millisInFuture / 60000) % 60;
                        long sec = (millisInFuture / 1000) % 60;
                        timerSlot.setText(String.valueOf(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec)));
                    }

                    //When the task will over it will print 00:00:00
                    @SuppressLint("SetTextI18n")
                    public void onFinish() {
                        timerSlot.setText("00:00:00");
                        countText.setText("Count: 0");
                        result.show();
                        resultMedia.start();
                        i = 0;
                    }

                }.start();

                countText.setText("Count: 0");
                i = 0;

            }
        });
        timerBoxBuilder.setNegativeButton("CANCEL" , null);
        final AlertDialog timerBox = timerBoxBuilder.create();



//  ClickListeners:-

        /*
        targetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetBox.show();
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                pauseMedia.start();
                if(activeState){
                    activeState = false;
                    pause.setText("Resume");
                    pauseImg.setVisibility(View.VISIBLE);
                }
                else{
                    activeState = true;
                    pause.setText("Pause");
                    pauseImg.setVisibility(View.GONE);
                }
            }
        });
         */

        tapSpace.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if(activeState) {
                    i++;
                    countText.setText("Count: " + i);

                    if(i == target){
                        result.show();
                        resultMedia.start();
                        i = 0;
                        countText.setText("Count: " + i);
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                resetMedia.start();
                i = 0;
                countText.setText("Count: 0");
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerBox.show();
            }
        });

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable actionBackground = new ColorDrawable(Color.parseColor("#FF000000"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(actionBackground);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }

    public void targetToast(){
        Toast.makeText(this , "Target activated" , Toast.LENGTH_LONG).show();
    }

    public void timerToast(){
        Toast.makeText(this , "Timer activated" , Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menutimermode , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.about) {
            Intent aboutIntent = new Intent(TimerActivity.this, AboutActivity.class);
            startActivity(aboutIntent);
        }

        if(item.getItemId() == R.id.timerMode){
            item.setChecked(false);
            Intent mainActivityIntent = new Intent(TimerActivity.this , MainActivity.class);
            startActivity(mainActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder backPressedBuilder = new AlertDialog.Builder(this);
        backPressedBuilder.setMessage("Are you sure you want to exit ?");
        backPressedBuilder.setCancelable(false);
        backPressedBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TimerActivity.this.finish();
            }
        });
        backPressedBuilder.setNegativeButton("No" , null);
        final AlertDialog backPressed = backPressedBuilder.create();
        backPressed.show();
    }

}

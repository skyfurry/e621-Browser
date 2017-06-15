package com.example.techjm.myfirstapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Random;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static boolean Safe = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.button);
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (message.isEmpty())
        {
            Random rand = new Random();
            int a = rand.nextInt(1240000) + 13;
            message = ""+a;
        }
        EditText editText2 = (EditText) findViewById(R.id.editText4);
        String BSpecies = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.editText5);
        String BGeneral = editText3.getText().toString();
        editText3 = (EditText) findViewById(R.id.editText3);
        String RatingThresh = editText3.getText().toString();
        if (RatingThresh.isEmpty())
        {
            RatingThresh = "0";
        }
        String myStrings[];
        myStrings = new String[] {message,BSpecies,BGeneral,RatingThresh};
        intent.putExtra(EXTRA_MESSAGE, myStrings);
        CheckBox CheckBox1 = (CheckBox) findViewById(R.id.checkBox);
        Safe = CheckBox1.isChecked();
        startActivity(intent);
    }
}


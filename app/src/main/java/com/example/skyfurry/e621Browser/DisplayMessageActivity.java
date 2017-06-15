package com.example.skyfurry.e621Browser;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techjm.e621Browser.R;

import static java.lang.Integer.parseInt;

public class DisplayMessageActivity extends AppCompatActivity {
    public static int a;
    public String finalString;
    public String[] message;
    private Vibrator myVib;

    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);

        a = parseInt(message[0]);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        int rating = 0;
        rating = Integer.parseInt(message[3]);
        new DownloadImageTask((ImageView) findViewById(R.id.imageView1),(TextView) findViewById(R.id.textView),false,a,message[1],message[2],rating,true)
                .execute();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        View view = findViewById(R.id.button2);
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


        textView.setText(finalString);



    }
    public void downloadImage(View view) {
        int rating = 0;
        rating = Integer.parseInt(message[3]);
        new DownloadImageTask((ImageView) findViewById(R.id.imageView1),(TextView) findViewById(R.id.textView),true,a,message[1],message[2],rating,true)
                .execute();

    }

    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";



        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());

            if (velocityX > 0) {
                String nextURL = "http://e621.net/post/show/";

                StringBuilder stringBuilder2 = new StringBuilder();
                a--;
                stringBuilder2.append(nextURL);
                stringBuilder2.append(a);
                stringBuilder2.append("/");
                finalString = stringBuilder2.toString();
                int rating = 0;
                rating = Integer.parseInt(message[3]);
                new DownloadImageTask((ImageView) findViewById(R.id.imageView1),(TextView) findViewById(R.id.textView),false,a,message[1],message[2],rating,false)
                        .execute();

                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(finalString);

            }
            else
            {
                String nextURL = "http://e621.net/post/show/";

                StringBuilder stringBuilder2 = new StringBuilder();
                a++;
                stringBuilder2.append(nextURL);
                stringBuilder2.append(a);
                stringBuilder2.append("/");
                finalString = stringBuilder2.toString();
                int rating = 0;
                rating = Integer.parseInt(message[3]);
                new DownloadImageTask((ImageView) findViewById(R.id.imageView1),(TextView) findViewById(R.id.textView),false,a,message[1],message[2],rating,true)
                        .execute();
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(finalString);
            }

            return true;
        }
    }
}



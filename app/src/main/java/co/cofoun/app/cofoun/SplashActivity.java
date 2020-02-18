package co.cofoun.app.cofoun;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        // create handler
        Handler handler = new Handler();
        // create runnable for starting activity
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // create intent
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                // start activity
                startActivity(intent);
                // finish splash
                finish();
            }
        };

        // set post delayed to handler
        handler.postDelayed(runnable, 2000);


    }
}

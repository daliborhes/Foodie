package com.daliborhes.foodie.UI;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daliborhes.foodie.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashLogo;
    private ObjectAnimator animSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);

        animSplashLogo = ObjectAnimator.ofFloat(splashLogo, "translationY", -1500, 0f);
        animSplashLogo.setDuration(800).start();


        final Intent intent = new Intent(this, EnteringActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();

    }
}

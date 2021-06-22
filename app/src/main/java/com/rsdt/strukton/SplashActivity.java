package com.rsdt.strukton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 23-6-2016
 * Description...
 */
public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

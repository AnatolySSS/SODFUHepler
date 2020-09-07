package com.scorp.sodfuhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class ChangeValuesOfNte extends AppCompatActivity {

    float x1, x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_values_of_nte);
    }

    public boolean onTouchEvent (MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                if(x1 < x2 + 300){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (x1 > x2 - 300){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }
}

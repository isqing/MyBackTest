package com.liyaqing.mybacktest;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView= findViewById(R.id.text);
        textView.setText("b1");
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager manager = (ActivityManager) getApplicationContext()
                        .getSystemService(Context.ACTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT>=21) {
                    List<ActivityManager.AppTask> appTasks = manager.getAppTasks();
                    Log.i("Main1Activity",appTasks.size()+"");
                }
                startActivity(new Intent(Main1Activity.this,Main11Activity.class));
            }
        });
    }
}

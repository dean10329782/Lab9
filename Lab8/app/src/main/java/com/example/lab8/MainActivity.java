package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv_clock;
    private Button btn_start;

    private Boolean flag = false;

     private BroadcastReceiver receiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {

             Bundle b = intent.getExtras();
             tv_clock.setText(String.format("%02d:%02d:%02d",b.getInt("H"),b.getInt("M"),b.getInt("S")));
         }
     };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_clock = findViewById(R.id.tv_clock);
        btn_start = findViewById(R.id.btn_start);

        registerReceiver(receiver, new IntentFilter("MyMessage"));

        flag = MyService.flag;
        if (flag)
            btn_start.setText("stop");
        else
            btn_start.setText("start");
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = !flag;
                if (flag) {
                    btn_start.setText("stop");
                    Toast.makeText(MainActivity.this, "count start", Toast.LENGTH_SHORT).show();
                }
                else{
                    btn_start.setText("start");
                    Toast.makeText(MainActivity.this, "count stop", Toast.LENGTH_SHORT).show();
                }
                startService(new Intent(MainActivity.this,MyService.class).putExtra("flag",flag));
            }
        });
    }
    public void onDestroy(){
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}
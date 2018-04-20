package com.phirathat.day5_android;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    private TextView tvhello;
    private Button btnsubmit;
    private EditText edt1;
    Intent intent;
    public static final int Activity_CODE = 1;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    NotificationManager nMgr;
    private Button btnremove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


                tvhello = findViewById(R.id.tv_hello);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnremove = findViewById(R.id.btnremove);
        edt1 = findViewById(R.id.edt1);
        intent = new Intent(this,SecondActivity.class);

        tvhello.setText("Counter: "+ counter); // ให้แสดงข้อความอยู่เมื่อกลับหน้าจอ
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;

                tvhello.setText("Counter: "+ counter);


                //startActivityForResult(intent,Activity_CODE);
                //alert(v);
                //alertCustomDialog(v);
                mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
                mNotifyMgr.notify(1, mBuilder.build());
                Toast.makeText(MainActivity.this,"You have new Message",Toast.LENGTH_LONG).show();
            }
        });
        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ns = Context.NOTIFICATION_SERVICE;
                nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(1);
                Toast.makeText(MainActivity.this,"Deleted Message",Toast.LENGTH_LONG).show();
            }
        });

        //Notification Builder
        mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name_noti)
                        .setContentTitle("New notification")
                        .setContentText("Open Google website!");

        Intent resultIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/"));
        //  Define notification action
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,   // context
                        0,      // requestCode (defined PendingIntent id)
                        resultIntent,
                        // if a previous PendingIntent already exists,
                        // then the current one will update it with the latest intent
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                Toast.makeText(this,result,Toast.LENGTH_LONG).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Save State
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Count",counter); //"key",value
        tvhello.setText("Counter: "+ counter);
    }
    // Restore state
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("Count");
        tvhello.setText("Counter: "+ counter);
    }

    public void alert(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("หัวข้อ");
        builder.setMessage("ทดลองสร้าง Dialog");
        builder.show();
    }
    //Custom Dialog
    public void alertCustomDialog(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        dialog.setCancelable(true);

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()
                        , "Close dialog", Toast.LENGTH_SHORT);
                dialog.cancel();
            }
        });

        TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
        textView1.setText("Custom Dialog");
        TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
        textView2.setText("Try it yourself");
        dialog.show();
    }




}

//package com.example.chantingworkingapp;
//
//import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.Scroller;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.res.ResourcesCompat;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class HistoryActivity extends AppCompatActivity {
//    TextView text,ttime,theard;
//    TableLayout table;
//    ScrollView scroll;
//    ImageView back;
//    MainActivity main;
//    LinearLayout header,footer;
//
//    private String current_time, table_tt_time, resultTimeString, total_time = "00:00";
//    long  totaltimemillis = 0,totalSeconds = 0;
//    private Date start_time, totaltime;
//    int k = 0, totalround = 0,round = 0;
//    private void vibrate(long milliseconds) {
//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        if (vibrator != null && vibrator.hasVibrator()) {
//            vibrator.vibrate(milliseconds);
//        }
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.tablehistory);
//
//        main = new MainActivity();
//        text = findViewById(R.id.textView);
//        ttime = findViewById(R.id.timetotal);
//        theard = findViewById(R.id.totalheard);
//        header = findViewById(R.id.headerContainer);
//        footer = findViewById(R.id.footerContainer);
//        table = findViewById(R.id.tableLayout);
//        scroll = findViewById(R.id.scrollView2);
//
//
//    }
//
//
//    public int loadtable(Context context, List<ModelClass> arrayList, int partition) {
//        if(table!= null) {
//            table.removeAllViews();
//        }
//        for (int i = 0; i < arrayList.size(); i++) {
//            if (partition == 1) {
//                addpartition();
//                partition = 0;
//                i--;
//            } else {
//                TableRow tableRowLoad = new TableRow(context);
//                tableRowLoad.setLayoutParams(new TableLayout.LayoutParams(
//                        TableLayout.LayoutParams.MATCH_PARENT,
//                        TableLayout.LayoutParams.WRAP_CONTENT
//                ));
//                tableRowLoad.setBackgroundColor(Color.TRANSPARENT);
//
//                Log.i(TAG, "Data array" + String.valueOf(arrayList.get(i).heard) + "  " + String.valueOf(arrayList.get(i).time));
//                // Create and add three text views to the row
//             TextView textView1 = ((MainActivity) context).createTextView(context);
//                TextView textView2 = ((MainActivity) context).createTextView(context);
//                TextView textView3 = ((MainActivity) context).createTextView(context);
//
//                textView1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//                textView2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//                textView3.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//                textView1.setPadding(2, 2, 2, 2);
//                textView2.setPadding(2, 2, 2, 2);
//                textView3.setPadding(2, 2, 2, 2);
//
//                round++;
//                if (round % 4 == 0) {
//                    partition = 1;
//                }
//                textView1.setText(String.valueOf(round));
//
//                textView2.setText(String.valueOf(arrayList.get(i).heard));
//                textView3.setText(String.valueOf(arrayList.get(i).time));
//                totalround += arrayList.get(i).heard;
//                try {
//                    // Define date format
//
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
//                    Date date = dateFormat.parse(arrayList.get(i).time);
//                    long minutesInSeconds = date.getMinutes() * 60;
//                    long seconds = date.getSeconds();
//
//                    // Add to the total duration
//                    totalSeconds += minutesInSeconds + seconds;
//
//                    // Format the result back to time format
//
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                tableRowLoad.addView(textView1);
//                tableRowLoad.addView(textView2);
//                tableRowLoad.addView(textView3);
//            if(table != null)
//                table.addView(tableRowLoad);
////                Toast.makeText(this, "Data Loaded", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//
//        table_tt_time = MainActivity.secondsToTimeString(totalSeconds);
//        ttime.setText(String.valueOf(table_tt_time));
//        theard.setText(String.valueOf(totalround));
//    return round;
//    }
//    public void addtablerows(Context context ,TextView textView,int k,int round) {
//        // Create a new table row
//        TableRow tableRow = new TableRow(context);
//        tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT
//        ));
//
////        Log.i(TAG, "Thread Name 4: " + Thread.currentThread().getName());
//        // Create and add three text views to the row
//        TextView textView1 = ((MainActivity) context).createTextView(context);
//        TextView textView2 = ((MainActivity) context).createTextView(context);
//        TextView textView3 = ((MainActivity) context).createTextView(context);
//
//        textView1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//        textView2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//        textView3.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//        try {
//            // Define date format
//            SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
//            // Example times
//            current_time = (String) textView.getText();
//            // Parse the times to Date objects
//            start_time = dateFormat.parse(current_time);
//            totaltime = dateFormat.parse(total_time);
//            // Convert times to milliseconds
//            long startTimeInMillis = start_time.getTime() / 1000;
////            long subtractTimeInMillis = totaltime.getTime() / 1000;
//
////            long resultTimeInMillis = 0;
////            // Subtract time
////            if (startTimeInMillis <= subtractTimeInMillis) {
////                resultTimeInMillis = subtractTimeInMillis - startTimeInMillis;
////            } else {
////
////                resultTimeInMillis = startTimeInMillis - subtractTimeInMillis;
////            }
////            Log.i(TAG, "total milli" + totaltimemillis + " start mili" + startTimeInMillis + "total,milli" + subtractTimeInMillis);
//            totaltimemillis = startTimeInMillis + totaltimemillis;
////            Toast.makeText(HistoryActivity.this, "total milli" + totaltimemillis + "result " + resultTimeInMillis, Toast.LENGTH_SHORT).show();
//
//            // Convert result back to Date
////            resultTimeString = MainActivity.secondsToTimeString(resultTimeInMillis);
////
////            SimpleDateFormat dateF = new SimpleDateFormat("mm:ss", Locale.getDefault());
////            Date date = dateF.parse(String.valueOf(table_tt.getText()));
////            long minutesInSeconds = date.getMinutes() * 60;
////            long seconds = date.getSeconds();
////            long totalSeconds = 0;
////            // Add to the total duration
////            totalSeconds += minutesI
//            total_time =  ((MainActivity) context).secondsToTimeString(totaltimemillis);
////            totalSeconds += resultTimeInMillis;
//            table_tt_time =  ((MainActivity) context).secondsToTimeString(totalSeconds);
//            if(table_tt_time!=null) {
//
//                ttime.setText(String.valueOf(table_tt_time));
//            }
//            // Format the result back to time format
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        textView1.setText(String.valueOf(round));
//        if (k > 108) {
//            k = 108;
//        }
//        textView2.setText(String.valueOf(k));
//        textView3.setText(String.valueOf(current_time));
//        totalround += (int) k;
//
//        // Add text views to the row
//        tableRow.addView(textView1);
//        tableRow.addView(textView2);
//        tableRow.addView(textView3);
//        theard.setText(String.valueOf(totalround));
//        long currentTimestamp = System.currentTimeMillis();
//        main = new MainActivity();
//        main.saveData(textView3.getText().toString(), textView2.getText().toString(), currentTimestamp);
//
//        // Add the row to the table layout
//        table.addView(tableRow);
//        k = 0;
//
//
//    }
//    public void addpartition() {
//        // Create a new table row
//        TableRow tableRow = new TableRow(this);
//        tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT
//        ));
//        TextView textView1 = main.createTextView(HistoryActivity.this);
//        TextView textView2 = main.createTextView(HistoryActivity.this);
//        TextView textView3 = main.createTextView(HistoryActivity.this);
//
//        textView1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//        textView2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//        textView3.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
//        textView1.setPadding(2,2,2,2);
//        textView2.setPadding(2,2,2,2);
//        textView3.setPadding(2,2,2,2);
//        float textSizeInSp = 2; // Set your desired text size in scaled pixels
//        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp);
//        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp);
//        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp);
//        tableRow.addView(textView1);
//        tableRow.addView(textView2);
//        tableRow.addView(textView3);
//        tableRow.setBackgroundColor(Color.parseColor("#8A2BE2"));
//
//        // Add the row to the table layout
//        table.addView(tableRow);
//        k = 0;
//
//
//    }
//
//
//}

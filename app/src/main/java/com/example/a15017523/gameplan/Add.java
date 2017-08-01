package com.example.a15017523.gameplan;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add extends AppCompatActivity {
    TextView textViewTime;
    EditText editTextTitle, editTextDescription;
    Button buttonAdd, buttonClock, buttonCalendar;
    ToggleButton toggleButtonReminder;
    Spinner spinner;

    Calendar calendar;
    int years;
    int month;
    int day;
    int hour;
    int mins;

    String message = "";
    String reminder;
    int cat_id;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        databaseHelper = new DatabaseHelper(this);
        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        calendar = Calendar.getInstance();
        years = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        mins = calendar.get(Calendar.MINUTE);

        textViewTime = (TextView)findViewById(R.id.tvReminder);

        editTextDescription = (EditText)findViewById(R.id.etDescription);
        editTextTitle = (EditText)findViewById(R.id.etTitle);

        spinner = (Spinner)findViewById(R.id.spinner);
        String[] spinnerList = databaseHelper.getAllSpinnerContent();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Add.this, android.R.layout.simple_spinner_item, spinnerList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat_id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cat_id = 1;
            }
        });

        buttonAdd = (Button)findViewById(R.id.btnAdd);
        buttonCalendar = (Button)findViewById(R.id.btnCalendar);
        buttonClock = (Button)findViewById(R.id.btnClock);

        toggleButtonReminder = (ToggleButton) findViewById(R.id.toggleButton);

        toggleButtonReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!toggleButtonReminder.isChecked()) {
                    buttonCalendar.setVisibility(View.INVISIBLE);
                    buttonClock.setVisibility(View.INVISIBLE);
                } else if(toggleButtonReminder.isChecked()) {
                    buttonCalendar.setVisibility(View.VISIBLE);
                    buttonClock.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout share =
                        (LinearLayout) inflater.inflate(R.layout.share, null);
                final TextView textViewSMS = (TextView) share
                        .findViewById(R.id.tvSMS);
                final TextView textViewEMail = (TextView) share
                        .findViewById(R.id.tvEmail);
                final TextView textViewWhatsApp = (TextView) share
                        .findViewById(R.id.tvWhatsApp);
                AlertDialog.Builder builder = new AlertDialog.Builder(Add.this);
                builder.setTitle("Share this with someone!")
                        .setView(share);
                textViewEMail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_SUBJECT, editTextTitle.getText().toString().trim());
                        intent.putExtra(Intent.EXTRA_TEXT, editTextDescription.getText().toString().trim());

                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }
                });
                textViewSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("sms_body", editTextDescription.getText().toString().trim());
                        startActivity(smsIntent);

                    }
                });
                textViewWhatsApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, editTextTitle.getText().toString().trim() + "\n\n" + editTextDescription.getText().toString().trim());
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(sendIntent, ""));
                        startActivity(sendIntent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                years = year;
                                month = monthOfYear;
                                day = dayOfMonth;
                                message += day + " " + month + " " + year;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(years, month, day, hour, mins);
                                Toast.makeText(Add.this, "Date selected: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }, years, month, day);

                datePickerDialog.show();
            }
        });

        buttonClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Add.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                message += hourOfDay + ":" + minute + " ";
                                Toast.makeText(Add.this, "Time selected: " + message, Toast.LENGTH_SHORT).show();
                                hour = hourOfDay;
                                mins = minute;
                                reminder = hourOfDay + ":" + minute;
                            }
                        }, hours, minutes, false);
                timePickerDialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, years);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR, hour);
                calendar.set(Calendar.MINUTE, mins);
                String reminder = hour + " : " + mins + " " + day + "/" + month + "/" + years;
                Toast.makeText(Add.this, "Reminder set at: " + reminder, Toast.LENGTH_SHORT).show();

                boolean isInserted = databaseHelper.insertData(cat_id,
                        editTextTitle.getText().toString().trim(),
                        editTextDescription.getText().toString().trim(),
                        currentDateTimeString.trim(),
                        reminder);

                if(isInserted == true) {
                    Toast.makeText(Add.this, "Data successfully inserted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();

//                    Intent intent = new Intent(Add.this, ScheduleNotificationReceiver.class);
//                    intent.putExtra("name", title);
//                    intent.putExtra("desc", description);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(Add.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//                    AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
//                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(Add.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

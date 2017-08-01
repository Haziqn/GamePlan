package com.example.a15017523.gameplan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton floatingActionButton;
    CalendarView calendarView;
    ArrayAdapter arrayAdapter;
    ArrayList<OBJECT> objects;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setVisibility(View.INVISIBLE);

        objects = new ArrayList<>();
        objects.clear();

        Cursor res = databaseHelper.getAllData();
        while (res.moveToNext()) {
            String id = res.getString(0);
            String categories = res.getString(1);
            String title = res.getString(2);
            String description = res.getString(3);
            String datetime = res.getString(4);
            String reminder = res.getString(5);
            objects.add(new OBJECT(id, categories, title, description, datetime, reminder));
        }

        listView = (ListView)findViewById(R.id.lv);
        arrayAdapter = new CustomAdapter(this, R.layout.obj_row, objects);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OBJECT object = objects.get(i);
                final StringBuffer buffer = new StringBuffer();
                buffer.append("Title : " + object.getTitle() + "\n");
                buffer.append("Description : " + object.getDescription() + "\n");
                buffer.append("Datetime : " + object.getDatetime() + "\n");
                buffer.append("Reminder : " + object.getReminder());

                showMessage(object.getId(),
                        buffer.toString(),
                        object.getCategories(),
                        object.getDescription(),
                        object.getTitle(),
                        object.getDatetime(),
                        object.getReminder());
            }
        });

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout search =
                        (LinearLayout) inflater.inflate(R.layout.search, null);
                final EditText editTextCat = (EditText) search
                        .findViewById(R.id.etCategory);
                final EditText editTextKeyWord = (EditText) search
                        .findViewById(R.id.etWord);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Search")
                        .setView(search);
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.action_Add) {
            Intent intent = new Intent(this, Add.class);
            startActivityForResult(intent, 9);
        } else if (item.getItemId() == R.id.action_all) {

        } else if (item.getItemId() == R.id.action_Categories) {

        } else if (item.getItemId() == R.id.action_refresh) {

        } else if (item.getItemId() == R.id.action_ShowCalendar) {
            calendarView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String id, String Message, String cat, String desc, String title, final String datetime, String reminder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String Id = id;
        final String Cat = cat;
        final String Title = title;
        final String Desc = desc;
        final String Datetime = datetime;
        final String Reminder = reminder;
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                OBJECT object = new OBJECT(Id, Cat, Title, Desc, Datetime, Reminder);

                Intent intent = new Intent(MainActivity.this, Add.class);
                intent.putExtra("object", object);
                startActivity(intent);
                startActivityForResult(intent, 9);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Integer deletedRows = databaseHelper.deleteData(Integer.parseInt(Id));
                if(deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Successfully deleted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Delete error.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK && requestCode == 9){
//            objects.clear();
//            Cursor res = databaseHelper.getAllData();
//            while (res.moveToNext()) {
//                String id = res.getString(0);
//                String categories = res.getString(1);
//                String title = res.getString(2);
//                String description = res.getString(3);
//                String datetime = res.getString(4);
//                String reminder = res.getString(5);
//                objects.add(new OBJECT(id, categories, title, description, datetime, reminder));
//            }
//            arrayAdapter = new ArrayAdapter(this, R.layout.obj_row, objects);
//            listView.setAdapter(arrayAdapter);
//        }
    }
}

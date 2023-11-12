package com.example.bmp601osama131433;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class AgentScreen extends AppCompatActivity {
    public static final int Group_ID = 0;
    public static final int Home_ID = 1;
    public static final int Reset_ID = 2;
    EditText et_agentid, et_agentname, et_agentdesc;
    Button bt_agentsave, bt_agentshow;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("app_theme", "DefaultTheme");
        if (theme.equals("Base.Theme.BMP601")) {
            setTheme(R.style.Base_Theme_BMP601);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.defbar)));
        }
        if (theme.equals("theme1")) {
            setTheme(R.style.theme1);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme1bar)));
        }
        if (theme.equals("theme2")) {
            setTheme(R.style.theme2);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme2bar)));
        }
        if (theme.equals("theme3")) {
            setTheme(R.style.theme3);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme3bar)));
        }
        if (theme.equals("theme4")) {
            setTheme(R.style.theme4);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme4bar)));
        }
        if (theme.equals("theme5")) {
            setTheme(R.style.theme5);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme5bar)));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_screen);

        et_agentid = findViewById(R.id.et_agentid);
        et_agentname = findViewById(R.id.et_agentname);
        et_agentdesc = findViewById(R.id.et_agentdesc);
        bt_agentsave = findViewById(R.id.bt_agentsave);
        bt_agentshow = findViewById(R.id.bt_agentshow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS agent (id NUMBER PRIMARY KEY,name VARCHAR,description VARCHAR)");

    }

    public void onClickagent(View v) {
        if (v == bt_agentsave) {
            if (et_agentid.getText().toString().trim().length() == 0) {
                showMessage("error", "enter id");
                et_agentid.requestFocus();
                return;
            }
            if (et_agentname.getText().toString().trim().length() == 0) {
                showMessage("error", "enter name");
                et_agentname.requestFocus();
                return;
            }
            if (et_agentdesc.getText().toString().trim().length() == 0) {
                showMessage("error", "enter description");
                et_agentdesc.requestFocus();
                return;
            }

            Cursor ca = db.rawQuery("SELECT id FROM agent", null);
            StringBuffer buffera = new StringBuffer();
            while (ca.moveToNext()) {
                buffera.append(ca.getString(0));
            }
            if (buffera.toString().contains(et_agentid.getText().toString())) {
                showMessage("Error", "ID Exist Already");
                et_agentid.requestFocus();
                return;
            }

            db.execSQL("INSERT INTO agent VALUES ('" + et_agentid.getText() + "','" + et_agentname.getText() + "','" + et_agentdesc.getText() + "');");
            showMessage("success", "Record added");
            ClearText();
        }
        if (v == bt_agentshow) {
            Cursor c = db.rawQuery("SELECT * FROM agent", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("id: " + Integer.parseInt(c.getString(0)) + "\n");
                buffer.append("name: " + c.getString(1) + "\n");
                buffer.append("description: " + c.getString(2) + "\n\n");
            }
            showMessage("Agent Details", buffer.toString());
        }

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.mipmap.icon_app_round);
        builder.show();
    }

    public void ClearText() {
        et_agentid.setText("");
        et_agentname.setText("");
        et_agentdesc.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Group_ID, Home_ID, 1, "Home");
        menu.add(Group_ID, Reset_ID, 2, "Reset");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case Home_ID:
                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
                break;
            case Reset_ID:
                et_agentid.setText("");
                et_agentname.setText("");
                et_agentdesc.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
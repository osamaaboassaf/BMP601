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
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class InvoiceScreen extends AppCompatActivity {

    public static final int Group_ID = 0;
    public static final int Home_ID = 1;
    public static final int Reset_ID = 2;
    EditText et_invoiceid, et_invoiceagent, et_invoicedate, et_invoicedesc;
    Button bt_saveinvoice, bt_showinvoice;
    CheckBox cb_isbuy;
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
        setContentView(R.layout.activity_invoice_screen);

        et_invoiceid = findViewById(R.id.et_invoiceid);
        et_invoiceagent = findViewById(R.id.et_invoiseagent);
        et_invoicedate = findViewById(R.id.et_invoicedate);
        cb_isbuy = findViewById(R.id.cb_invoicebuy);
        et_invoicedesc = findViewById(R.id.et_invoicedesc);

        bt_saveinvoice = findViewById(R.id.bt_saveinvoice);
        bt_showinvoice = findViewById(R.id.bt_showinvoice);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS invoice (id NUMBER PRIMARY KEY,agent_id NUMBER,date DATE,isbuy VARCHAR,description VARCHAR)");
    }

    public void onClickInvoice(View v) {
        if (v == bt_saveinvoice) {

            if (et_invoiceid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Invoice ID");
                et_invoiceid.requestFocus();
                return;
            }
            if (et_invoiceagent.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Agent ID");
                et_invoiceagent.requestFocus();
                return;
            }
            if (et_invoicedate.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Invoice Date");
                et_invoicedate.requestFocus();
                return;
            }
            if (et_invoicedesc.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Invoice Description");
                et_invoicedesc.requestFocus();
                return;
            }
            Cursor ci = db.rawQuery("SELECT id FROM invoice", null);
            StringBuffer bufferi = new StringBuffer();
            while (ci.moveToNext()) {
                bufferi.append(ci.getString(0));
            }
            if (bufferi.toString().contains(et_invoiceid.getText().toString())) {
                showMessage("Error", "ID Exist Already");
                et_invoiceid.requestFocus();
                return;
            }


            Cursor ca = db.rawQuery("SELECT id FROM agent", null);
            StringBuffer buffera = new StringBuffer();
            while (ca.moveToNext()) {
                buffera.append(ca.getString(0));
            }
            if (buffera.toString().contains(et_invoiceagent.getText().toString())) {
                db.execSQL("INSERT INTO invoice VALUES ('" + et_invoiceid.getText() + "','" + et_invoiceagent.getText() + "','" + et_invoicedate.getText() + "','" + cb_isbuy.isChecked() + "','" + et_invoicedesc.getText() + "');");
                showMessage("success", "Record added");
                ClearText();
            } else {
                showMessage("Error", "No Such Agent ID");
                et_invoiceagent.requestFocus();
                return;
            }
        }

        if (v == bt_showinvoice) {
            Cursor c = db.rawQuery("SELECT * FROM invoice", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No Records Found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("id: " + c.getString(0) + "\n");
                buffer.append("agent: " + c.getString(1) + "\n");
                buffer.append("date: " + c.getString(2) + "\n");
                buffer.append("is buy: " + c.getString(3) + "\n");
                buffer.append("description: " + c.getString(4) + "\n\n");
            }
            showMessage("Invoices Details", buffer.toString());
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
        et_invoiceid.setText("");
        et_invoiceagent.setText("");
        et_invoicedate.setText("");
        et_invoicedesc.setText("");
        cb_isbuy.setChecked(false);
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
                ClearText();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
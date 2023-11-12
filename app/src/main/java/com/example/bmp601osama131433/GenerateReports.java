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

public class GenerateReports extends AppCompatActivity {
    public static final int Group_ID = 0;
    public static final int Home_ID = 1;
    public static final int Reset_ID = 2;
    Button bt_suppliers,bt_buyers,bt_MSales,bt_sumsales,bt_sumpurch;
    EditText et_from,et_to;
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
        setContentView(R.layout.activity_generate_reports);

        bt_suppliers=findViewById(R.id.bt_suppliers);
        bt_buyers=findViewById(R.id.bt_buyers);
        bt_MSales = findViewById(R.id.bt_MSales);
        et_from=findViewById(R.id.et_from);
        et_to=findViewById(R.id.et_to);
        bt_sumsales=findViewById(R.id.bt_Sumsales);
        bt_sumpurch=findViewById(R.id.bt_sumpurch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
    }

    public void onClickgenerate(View v){
        if(v==bt_suppliers)
        {
            Cursor cursor = db.rawQuery("SELECT invoice.id,invoice.agent_id,COUNT(invoice.id) AS count,invoice.isbuy,agent.name,agent.id FROM invoice JOIN agent ON agent.id=invoice.agent_id WHERE isbuy='false' GROUP BY agent_id ORDER BY count DESC LIMIT 1", null);
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("Agent Name: "+cursor.getString(4)+"\n");
                buffer.append("Agent ID: "+cursor.getString(5)+"\n");
                buffer.append("Count: " + cursor.getString(2) + "\n");
            }
            showMessage("Most Suppliers", buffer.toString());
        }


        if(v==bt_buyers){
            Cursor cursor = db.rawQuery("SELECT invoice.id,invoice.agent_id,COUNT(invoice.id) AS count,invoice.isbuy,agent.name,agent.id FROM invoice JOIN agent ON agent.id=invoice.agent_id WHERE isbuy='true' GROUP BY agent_id ORDER BY count DESC LIMIT 1", null);
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("Agent Name: "+cursor.getString(4)+"\n");
                buffer.append("Agent ID: "+cursor.getString(5)+"\n");
                buffer.append("Count: " + cursor.getString(2) + "\n");
            }
            showMessage("Most Buyers", buffer.toString());
        }


        if (v == bt_MSales)
        {
            if(et_from.getText().toString().trim().length()==0 || et_to.getText().toString().trim().length()==0){
                showMessage("Error","Enter Date (from and to)");
                return;
            }
            Cursor c = db.rawQuery("SELECT invoice.id,invoice.date,invoicedetail.invoice_id,invoicedetail.detail_price,invoicedetail.material_id,material.id,material.name,COUNT(invoicedetail.material_id) AS count FROM invoice JOIN invoicedetail ON invoice.id=invoicedetail.invoice_id JOIN material ON material.id=invoicedetail.material_id WHERE date BETWEEN '"+et_from.getText().toString()+"' AND '"+et_to.getText().toString()+"' GROUP BY invoice.id ORDER BY count DESC LIMIT 1", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No Records Found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("invoice id: " + c.getString(0) + "\n");
                buffer.append("invoice date: " + c.getString(1) + "\n");
                buffer.append("material id: " + c.getString(5) + "\n");
                buffer.append("material name: " + c.getString(6) + "\n");
                buffer.append("material count: " + c.getString(7) + "\n\n");
            }
            showMessage("Most Sales", buffer.toString());
        }


        if(v==bt_sumsales)
        {
            if(et_from.getText().toString().trim().length()==0 || et_to.getText().toString().trim().length()==0){
                showMessage("Error","Enter Date (from and to)");
                return;
            }

            Cursor c = db.rawQuery("SELECT invoice.id, invoice.date, invoice.isbuy, invoicedetail.invoice_id,SUM((invoicedetail.detail_price*invoicedetail.detail_num)) AS total FROM invoice JOIN invoicedetail  ON invoice.id=invoicedetail.invoice_id WHERE invoice.isbuy='true' AND invoice.date BETWEEN '"+et_from.getText().toString()+"' AND '"+et_to.getText().toString()+"' GROUP BY invoice.id ORDER BY total", null);

            if (c.getCount() == 0) {
                showMessage("Error", "No Records Found");
                return;
            }
            int totS=0;
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                totS+=Integer.parseInt(c.getString(4));
            }
            showMessage("Sum sales", "total sales: " + String.valueOf(totS));
        }


        if(v==bt_sumpurch)
        {
            if(et_from.getText().toString().trim().length()==0 || et_to.getText().toString().trim().length()==0){
                showMessage("Error","Enter Date (from and to)");
                return;
            }
            Cursor c = db.rawQuery("SELECT invoice.id, invoice.date, invoice.isbuy, invoicedetail.invoice_id,SUM((invoicedetail.detail_price*invoicedetail.detail_num)) AS total FROM invoice JOIN invoicedetail ON invoice.id=invoicedetail.invoice_id WHERE invoice.isbuy='false' AND invoice.date BETWEEN '"+et_from.getText().toString()+"' AND '"+et_to.getText().toString()+"' GROUP BY invoice.id ORDER BY total", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No Records Found");
                return;
            }

            int totP=0;
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                totP+=Integer.parseInt(c.getString(4));
                //buffer.append("total:  " + c.getString(4) + "\n\n");
            }
            showMessage("Sum Purchases", "total Purchases: "+String.valueOf(totP));
        }
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.mipmap.icon_app_round);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Group_ID, Home_ID, 1, "Home");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case Home_ID:
                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
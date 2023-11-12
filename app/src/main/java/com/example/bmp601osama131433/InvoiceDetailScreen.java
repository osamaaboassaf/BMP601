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

public class InvoiceDetailScreen extends AppCompatActivity {

    public static final int Group_ID=0;
    public static final int Home_ID=1;
    public static final int Reset_ID=2;
    EditText et_detailid,et_detailinvoiceid,et_detailmaterialid,et_detailnum,et_detailprice;
    Button bt_savedetail,bt_showdetail;
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
        setContentView(R.layout.activity_invoice_detail_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_detailid=findViewById(R.id.et_detaiid);
        et_detailinvoiceid=findViewById(R.id.et_detailinvoiceid);
        et_detailmaterialid=findViewById(R.id.et_detailmaterialid);
        et_detailnum=findViewById(R.id.et_detailnum);
        et_detailprice=findViewById(R.id.et_detailprice);
        bt_savedetail=findViewById(R.id.bt_savedetail);
        bt_showdetail=findViewById(R.id.bt_showdetail);

        db = openOrCreateDatabase("MyDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS invoicedetail (id NUMBER PRIMARY KEY,invoice_id NUMBER,material_id NUMBER,detail_num NUMBER,detail_price NUMBER)");

    }

    public void onClickDetail(View v){
        if(v==bt_savedetail){
            if (et_detailid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Invoice Detail ID");
                et_detailid.requestFocus();
                return;
            }
            if (et_detailinvoiceid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Invoice ID");
                et_detailinvoiceid.requestFocus();
                return;
            }
            if (et_detailmaterialid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Material ID");
                et_detailmaterialid.requestFocus();
                return;
            }
            if (et_detailnum.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter The Number");
                et_detailnum.requestFocus();
                return;
            }
            if (et_detailprice.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter The Price");
                et_detailprice.requestFocus();
                return;
            }
            Cursor cdet = db.rawQuery("SELECT id FROM invoicedetail", null);
            StringBuffer bufferdet = new StringBuffer();
            while (cdet.moveToNext()) {
                bufferdet.append(cdet.getString(0));
            }
            if (bufferdet.toString().contains(et_detailid.getText().toString())) {
                showMessage("Error", "ID Exist Already");
                et_detailid.requestFocus();
                return;
            }

            Cursor cin = db.rawQuery("SELECT id FROM invoice", null);
            StringBuffer bufferin = new StringBuffer();
            while (cin.moveToNext()) {
                bufferin.append(cin.getString(0));
            }
            if (!bufferin.toString().contains(et_detailinvoiceid.getText().toString())) {
                showMessage("Error", "No Such Invoice ID");
                et_detailinvoiceid.requestFocus();
                return;
            }

            Cursor cmat = db.rawQuery("SELECT id FROM material", null);
            StringBuffer buffermat = new StringBuffer();
            while (cmat.moveToNext()) {
                buffermat.append(cmat.getString(0));
            }
            if (!buffermat.toString().contains(et_detailmaterialid.getText().toString())) {
                showMessage("Error", "No Such Material ID");
                et_detailmaterialid.requestFocus();
                return;
            }

            db.execSQL("INSERT INTO invoicedetail VALUES ('" + et_detailid.getText() + "','" + et_detailinvoiceid.getText() + "','" + et_detailmaterialid.getText() + "','" +et_detailnum.getText() + "','" + et_detailprice.getText() + "');");
            showMessage("success", "Record added");
            ClearText();

        }
        if(v==bt_showdetail)
        {
            Cursor c = db.rawQuery("SELECT * FROM invoicedetail", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No Records Found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("id: " + c.getString(0) + "\n");
                buffer.append("invoice id: " + c.getString(1) + "\n");
                buffer.append("material id: " + c.getString(2) + "\n");
                buffer.append("number: " + c.getString(3) + "\n");
                buffer.append("price: " + c.getString(4) + "\n");
                buffer.append("total: "+Integer.parseInt(c.getString(3))*Integer.parseInt(c.getString(4))+"\n\n");
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
        et_detailid.setText("");
        et_detailinvoiceid.setText("");
        et_detailmaterialid.setText("");
        et_detailnum.setText("");
        et_detailprice.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Group_ID,Home_ID,1,"Home");
        menu.add(Group_ID,Reset_ID,2,"Reset");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()){
            case Home_ID:
                Intent intent = new Intent (this,HomeScreen.class);
                startActivity(intent);
                break;
            case Reset_ID:
                ClearText();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
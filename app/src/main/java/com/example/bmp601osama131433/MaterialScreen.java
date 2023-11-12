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

public class MaterialScreen extends AppCompatActivity {
    EditText et_materId,et_matername,et_Typeid,et_materdesc;
    Button bt_Savemater,bt_showmater;
    public static final int Group_ID=0;
    public static final int Home_ID=1;
    public static final int Reset_ID=2;
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
        setContentView(R.layout.activity_material_screen);
        et_materId=findViewById(R.id.et_materID);
        et_matername=findViewById(R.id.et_materName);
        et_Typeid=findViewById(R.id.et_matertypeID);
        et_materdesc=findViewById(R.id.et_materDesc);
        bt_Savemater=findViewById(R.id.bt_savemater);
        bt_showmater=findViewById(R.id.bt_showmater);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db=openOrCreateDatabase("MyDB", Context.MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS material (id NUMBER PRIMARY KEY,name VARCHAR,typeId VARCHAR,description VARCHAR,FOREIGN KEY(typeId) REFERENCES type(id))");
    }
    public  void  onClickmater(View v){
        if(v==bt_Savemater)
        {
            if(et_materId.getText().toString().trim().length()==0)
            {
                showMessage("error","enter id");
                et_materId.requestFocus();
                return;
            }
            if(et_matername.getText().toString().trim().length()==0)
            {
                showMessage("error","enter name");
                et_matername.requestFocus();
                return;
            }
            if(et_Typeid.getText().toString().trim().length()==0)
            {
                showMessage("error","enter Type ID");
                et_Typeid.requestFocus();
                return;
            }
            if(et_materdesc.getText().toString().trim().length()==0)
            {
                showMessage("error","enter description");
                et_materdesc.requestFocus();
                return;
            }

            Cursor cm=db.rawQuery("SELECT id FROM material",null);
            StringBuffer bufferm=new StringBuffer();
            while (cm.moveToNext()){
                bufferm.append(cm.getString(0));
            }
            if(bufferm.toString().contains(et_materId.getText().toString()))
            {
                showMessage("Error","ID Exist Already");
                et_materId.requestFocus();
                return;
            }

            Cursor ct=db.rawQuery("SELECT id FROM type",null);
            StringBuffer buffert=new StringBuffer();
            while (ct.moveToNext()){
                buffert.append(ct.getString(0));
            }
            if(buffert.toString().contains(et_Typeid.getText().toString()))
            {
                db.execSQL("INSERT INTO material VALUES ('" +et_materId.getText()+"','"+et_matername.getText()+ "','" +et_Typeid.getText()+"','"+et_materdesc.getText()+"');" );
                showMessage("success","Record added");
                ClearText();
            }else{
              showMessage("Error","No Such TypeID");
              et_Typeid.requestFocus();
              return;
            }

        }
        if(v==bt_showmater)
        {
            Cursor c=db.rawQuery("SELECT * FROM material",null);
            if(c.getCount()==0){
                showMessage("Error","No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while (c.moveToNext()){
                buffer.append("id: "+c.getString(0)+"\n");
                buffer.append("name: "+c.getString(1)+"\n");
                buffer.append("type id: "+c.getString(2)+"\n");
                buffer.append("description: "+c.getString(3)+"\n\n");
            }
            showMessage("Material Details",buffer.toString());
        }
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.mipmap.icon_app_round);
        builder.show();
    }
    public void ClearText(){
        et_materId.setText("");
        et_matername.setText("");
        et_Typeid.setText("");
        et_materdesc.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Group_ID,Home_ID,1,"Home");
        menu.add(Group_ID,Reset_ID,2,"Reset");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case Home_ID:
                Intent intent = new Intent (this,HomeScreen.class);
                startActivity(intent);
                break;
            case Reset_ID:
                et_materId.setText("");
                et_matername.setText("");
                et_Typeid.setText("");
                et_materdesc.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
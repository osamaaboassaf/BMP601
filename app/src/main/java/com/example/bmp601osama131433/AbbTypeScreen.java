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

public class AbbTypeScreen extends AppCompatActivity {

    public static final int Group_ID=0;
    public static final int Home_ID=1;
    public static final int Reset_ID=2;

    EditText et_tid,et_tname,et_tdes;
    Button bt_save,bt_show;
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
        setContentView(R.layout.activity_abb_type_screen);

        et_tid = findViewById(R.id.et_typeid);
        et_tname = findViewById(R.id.et_typename);
        et_tdes=findViewById(R.id.et_typedesc);
        bt_save=findViewById(R.id.bt_typesave);
        bt_show=findViewById(R.id.bt_typeshow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db=openOrCreateDatabase("MyDB", Context.MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS type (id NUMBER PRIMARY KEY,name VARCHAR,description VARCHAR)");

    }
    public  void  onClick(View v){
        if(v==bt_save){
            if(et_tid.getText().toString().length()==0)
            {
                showMessage("error","enter id");
                et_tid.requestFocus();
                return;
            }
            if(et_tname.getText().toString().trim().length()==0)
            {
                showMessage("error","enter name");
                et_tname.requestFocus();
                return;
            }
            if(et_tdes.getText().toString().trim().length()==0)
            {
                showMessage("error","enter description");
                et_tdes.requestFocus();
                return;
            }

            Cursor cu= db.rawQuery("SELECT id FROM type",null);
            StringBuffer buffer=new StringBuffer();
            while (cu.moveToNext()){
                buffer.append(cu.getString(0));
            }
            if(buffer.toString().contains(et_tid.getText().toString()))
            {
                showMessage("Error","ID Exist Already");
                et_tid.requestFocus();
                return;
            }

            db.execSQL("INSERT INTO type VALUES ('" +et_tid.getText()+"','"+et_tname.getText()+ "','" +et_tdes.getText()+"');" );
            showMessage("success","Record added");
            ClearText();
        }

        if(v==bt_show)
        {
            Cursor c=db.rawQuery("SELECT * FROM type",null);
            if(c.getCount()==0){
                showMessage("Error","No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while (c.moveToNext()){
                buffer.append("id: "+c.getString(0)+"\n");
                buffer.append("name: "+c.getString(1)+"\n");
                buffer.append("description: "+c.getString(2)+"\n\n");
            }
            showMessage("Type Details",buffer.toString());
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
        et_tid.setText("");
        et_tname.setText("");
        et_tdes.setText("");
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
                et_tid.setText("");
                et_tname.setText("");
                et_tdes.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
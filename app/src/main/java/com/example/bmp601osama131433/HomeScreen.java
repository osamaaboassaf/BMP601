package com.example.bmp601osama131433;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class HomeScreen extends AppCompatActivity {
    public static final int Group_ID=0;
    public static final int Settings_ID=1;
Button bt_addtype,bt_addmaterial,bt_addagent,bt_addinvoice,bt_addinvoicedet,bt_generate;
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
        setContentView(R.layout.activity_home_screen);

        bt_addtype=findViewById(R.id.bt_addtype);
        bt_addmaterial=findViewById(R.id.bt_addmaterial);
        bt_addagent=findViewById(R.id.bt_addagent);
        bt_addinvoice=findViewById(R.id.bt_addinvoic);
        bt_addinvoicedet=findViewById(R.id.bt_addinvoicedet);
        bt_generate=findViewById(R.id.bt_generate);
    }

    public void onClickHome(View v)
{
    if(v==bt_addtype)
    {
        Intent intent = new Intent (this,AbbTypeScreen.class);
        startActivity(intent);
    }
    if(v==bt_addmaterial)
    {
        Intent intent = new Intent (this,MaterialScreen.class);
        startActivity(intent);
    }
    if(v==bt_addagent)
    {
        Intent intent = new Intent (this,AgentScreen.class);
        startActivity(intent);
    }
    if(v==bt_addinvoice)
    {
        Intent intent =new Intent(this,InvoiceScreen.class);
        startActivity(intent);
    }
    if(v==bt_addinvoicedet)
    {
        Intent intent=new Intent(this,InvoiceDetailScreen.class);
        startActivity(intent);
    }
    if(v==bt_generate)
    {
        Intent intent=new Intent(this,GenerateReports.class);
        startActivity(intent);
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Group_ID,Settings_ID,1,"Settings");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()){
            case Settings_ID:
                Intent intent = new Intent (this, ThemeSoundST.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                  {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
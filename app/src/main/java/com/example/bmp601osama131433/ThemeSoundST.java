package com.example.bmp601osama131433;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class ThemeSoundST extends AppCompatActivity {

    RadioButton rb_themedef,rb_theme1,rb_theme2,rb_theme3,rb_theme4,rb_theme5;
    Button bt_apply;
    Switch sw_sound;

SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("app_theme", "Base.Theme.BMP601");
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
        setContentView(R.layout.activity_teme_sound_st);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bt_apply=findViewById(R.id.bt_apply);
        rb_themedef=findViewById(R.id.rb_themedef);
        rb_theme1=findViewById(R.id.rb_theme1);
        rb_theme2=findViewById(R.id.rb_theme2);
        rb_theme3=findViewById(R.id.rb_theme3);
        rb_theme4=findViewById(R.id.rb_theme4);
        rb_theme5=findViewById(R.id.rb_theme5);
        sw_sound=findViewById(R.id.sw_sound);

    }

    public void onclick(View v){
        if(rb_themedef.isChecked())
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app_theme", "Base.Theme.BMP601");
            editor.apply();
            recreate();
        }
        if(rb_theme1.isChecked())
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app_theme", "theme1");
            editor.apply();
            recreate();
        }
        if(rb_theme2.isChecked())
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app_theme", "theme2");
            editor.apply();
            recreate();
        }
        if(rb_theme3.isChecked())
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app_theme", "theme3");
            editor.apply();
            recreate();
        }
        if(rb_theme4.isChecked())
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app_theme", "theme4");
            editor.apply();
            recreate();
        }
        if(rb_theme5.isChecked())
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app_theme", "theme5");
            editor.apply();
            recreate();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}
package com.homework.vaisakh.congressapi;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homework.vaisakh.congressapi.models.LegisModel;
import com.squareup.picasso.Picasso;

import java.util.HashSet;

/**
 * Created by vaisakh on 11/14/16.
 */

public class Aboutme extends AppCompatActivity {



    private ImageView icon;
    private ImageView fav;
    private ImageView facebook;
    private ImageView twitter;
    private ImageView earth;
    private ImageView partyicon;
    private TextView party;
    private TextView state;
    private TextView name;
    private TextView email;
    private TextView chamber;
    private TextView contact;
    private TextView startterm;
    private TextView endterm;
    private TextView office;
    private TextView fax;
    private TextView birthday;
    private ProgressBar term;
    public LegisModel legismodelFav;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("About me");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray));
        final FavItems favItems = new FavItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

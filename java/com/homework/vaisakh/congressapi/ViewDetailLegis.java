package com.homework.vaisakh.congressapi;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by vaisakh on 11/14/16.
 */

public class ViewDetailLegis extends ActionBarActivity {



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
    private TextView bartext;
    private ProgressBar term;
    public LegisModel legismodelFav;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Legislator Info");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray));
        final FavItems favItems = new FavItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetailslegis);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setUpUIViews();


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            final FavItems appState = ((FavItems) getApplicationContext());
            String json = bundle.getString("legisModel");
            final LegisModel legisModelList = new Gson().fromJson(json, LegisModel.class);
            String url = "https://theunitedstates.io/images/congress/original/" + legisModelList.getBioguideid().toString() + ".jpg";
            Picasso.with(getApplicationContext()).load(url).resize(200,200).into(icon);
            if(appState.getLegidIdSet().contains(legisModelList.getBioguideid())) {

                fav.setImageResource(R.drawable.staron);
            }

            try {
                legismodelFav = legisModelList;
                fav.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        if(appState.getLegidIdSet().contains(legisModelList.getBioguideid())) {
                            appState.delLegidId(legisModelList.getBioguideid());
                            fav.setImageResource(R.drawable.staroff);
                        }
                        else {
                            appState.setLegidIdSet(legisModelList.getBioguideid());
                            fav.setImageResource(R.drawable.staron);
                        }
                    }
                });
            } catch (Exception e) {


            }


            try {
                final String fb = "http://www.facebook.com/"+legisModelList.getFacebook_id().toString();
                facebook.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        FavItems favItems = new FavItems();
                        HashSet<String> legidIdSet= favItems.getLegidIdSet();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(fb));
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                facebook.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(ViewDetailLegis.this,
                                "No facebook link", Toast.LENGTH_SHORT).show();
                    }
                });

            }


            try {
                final String tw = "http://www.twitter.com/"+legisModelList.getTwitter_id().toString();
                twitter.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(tw));
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {

                twitter.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(ViewDetailLegis.this,
                                "No twitter link", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            try {
                final String ea = legisModelList.getWebsite().toString();
                earth.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(ea));
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                earth.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(ViewDetailLegis.this,
                                "No website link", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            try {
                if(legisModelList.getParty().toString().equalsIgnoreCase("R")) {
                    party.setText("Republican");
                    Picasso.with(getApplicationContext()).load(R.drawable.r).resize(50,50).into(partyicon);

                }
                else {
                    party.setText("Democrat");
                    Picasso.with(getApplicationContext()).load(R.drawable.d).resize(50,50).into(partyicon);
                }

            } catch (Exception e) {
                party.setText("N.A.");
            }

            try {
                name.setText(legisModelList.getLastname().toString()+","+legisModelList.getFirstname().toString());
            } catch (Exception e) {
                name.setText("N.A.");
            }
            try {
                email.setText(legisModelList.getOc_email().toString());
            } catch (Exception e) {
                email.setText("N.A.");
            }
            try {
                chamber.setText(legisModelList.getChamber().toString());
            } catch (Exception e) {
                chamber.setText("N.A.");
            }
            try {
                contact.setText(legisModelList.getContact_form().toString());
            } catch (Exception e) {
                contact.setText("N.A.");
            }
            try {
                startterm.setText(legisModelList.getTerm_start().toString());
            } catch (Exception e) {
                startterm.setText("N.A.");
            }
            try {
                endterm.setText(legisModelList.getTerm_end().toString());
            } catch (Exception e) {
                endterm.setText("N.A.");
            }
            try {
                office.setText(legisModelList.getOffice().toString());
            } catch (Exception e) {
                office.setText("N.A.");
            }
            try {
                state.setText(legisModelList.getState_name().toString());
            } catch (Exception e) {
                state.setText("N.A.");
            }
            try {
                fax.setText(legisModelList.getFax().toString());
            } catch (Exception e) {
                fax.setText("N.A.");
            }
            try {
                birthday.setText(legisModelList.getBirthday().toString());
            } catch (Exception e) {
                birthday.setText("N.A.");
            }
            term.setMax(100);
            Long start =0L;
            Long diff = 69L;
            Long end = 0L;
            Date now = new Date();
            try {
                 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                  start=df.parse(legisModelList.getTerm_start()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                 end=df.parse(legisModelList.getTerm_end()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int  n1=0;
            try {
    Long n = now.getTime()-start;
    Long d = end - start;
     diff=n/d;
    Random rand = new Random();

      n1 = rand.nextInt(56) + 40;
     //diff = Math.abs((now.getTime() - start)/(end-start));
}
catch (Exception e) {

}
bartext.setText(n1+"%");

            term.setProgress(n1);


        }

    }

    private void setUpUIViews() {
        partyicon = (ImageView)findViewById(R.id.partyicon);
        facebook = (ImageView)findViewById(R.id.facebook);
        twitter = (ImageView)findViewById(R.id.twitter);
        earth = (ImageView)findViewById(R.id.earth);
        fav = (ImageView)findViewById(R.id.fav);
        icon = (ImageView)findViewById(R.id.icon);
        party = (TextView)findViewById(R.id.fullparty);
        name = (TextView)findViewById(R.id.fullname);
        email = (TextView)findViewById(R.id.email);
        chamber = (TextView)findViewById(R.id.chamber);
        contact = (TextView)findViewById(R.id.contact);
        startterm = (TextView)findViewById(R.id.startterm);
        endterm = (TextView)findViewById(R.id.endterm);
        office = (TextView)findViewById(R.id.office);
        state = (TextView)findViewById(R.id.state);
        fax = (TextView)findViewById(R.id.fax);
        birthday = (TextView)findViewById(R.id.birthday);
        term =(ProgressBar)findViewById(R.id.term);
        bartext = (TextView)findViewById(R.id.myTextProgress);
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

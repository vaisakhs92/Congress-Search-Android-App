package com.homework.vaisakh.congressapi;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.homework.vaisakh.congressapi.models.BillModel;
import com.homework.vaisakh.congressapi.models.CommModel;

/**
 * Created by vaisakh on 11/14/16.
 */

public class ViewDetailComm extends ActionBarActivity {


    private TextView id;
    private TextView name;
    private TextView chamber;
    private TextView parent;
    private TextView contact;
    private TextView office;
    private ImageView fav;
    public CommModel commModelFav;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Committees Info");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetailscomm);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setUpUIViews();


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            final FavItems appState = ((FavItems) getApplicationContext());

            String json = bundle.getString("commmodel");
            CommModel commModel = new Gson().fromJson(json, CommModel.class);

            if(appState.getLegidIdSet().contains(commModel.getCommittee_id())) {

                fav.setImageResource(R.drawable.staron);
            }

            try {
                commModelFav = commModel;
                fav.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        if(appState.getLegidIdSet().contains(commModelFav.getCommittee_id())) {
                            appState.delLegidId(commModelFav.getCommittee_id());
                            fav.setImageResource(R.drawable.staroff);
                        }
                        else {
                            appState.setLegidIdSet(commModelFav.getCommittee_id());
                            fav.setImageResource(R.drawable.staron);
                        }
                    }
                });
            } catch (Exception e) {


            }
            String active;

            id.setText(commModel.getCommittee_id().toString());
            name.setText(commModel.getName().toString());



            try {
                if(commModel.getChamber()!=null)
                    chamber.setText(commModel.getChamber());
            }
            catch (Exception e) {
                chamber.setText("N.A.");
            }

            try {
                if(commModel.getParent_committee_id()!=null)
                    parent.setText(commModel.getParent_committee_id());
            }
            catch (Exception e) {
                  parent.setText("N.A.");
            }

            try {
                if(commModel.getOffice()!=null)
                    office.setText(commModel.getOffice().toString());
            }
            catch (Exception e) {
                office.setText("N.A.");
            }

            try {
                if(commModel.getPhone()!=null)
                    contact.setText(commModel.getPhone());
            }
            catch (Exception e) {
                contact.setText("N.A.");
            }


        }

    }

    private void setUpUIViews() {
        fav = (ImageView)findViewById(R.id.fav);
        id = (TextView)findViewById(R.id.id);
        name = (TextView)findViewById(R.id.name);
        chamber = (TextView)findViewById(R.id.chamber);
        parent = (TextView)findViewById(R.id.parent);
        contact = (TextView)findViewById(R.id.contact);
        office = (TextView)findViewById(R.id.office);


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

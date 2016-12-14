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

/**
 * Created by vaisakh on 11/14/16.
 */

public class ViewDetailBills extends ActionBarActivity {


    private TextView billid;
    private TextView title;
    private TextView billtype;
    private TextView sponsor;
    private TextView chamber;
    private TextView status;
    private TextView introducedon;
    private TextView congressurl;
    private TextView versionname;
    private TextView billurl;
    private ImageView fav;
    public BillModel billmodelFav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Bills Info");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetailsbills);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setUpUIViews();


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            final FavItems appState = ((FavItems) getApplicationContext());

            String json = bundle.getString("billmodel");
            BillModel billModel = new Gson().fromJson(json, BillModel.class);
            if(appState.getLegidIdSet().contains(billModel.getBill_id())) {

                fav.setImageResource(R.drawable.staron);
            }

            try {
                billmodelFav = billModel;
                fav.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        if(appState.getLegidIdSet().contains(billmodelFav.getBill_id())) {
                            appState.delLegidId(billmodelFav.getBill_id());
                            fav.setImageResource(R.drawable.staroff);
                        }
                        else {
                            appState.setLegidIdSet(billmodelFav.getBill_id());
                            fav.setImageResource(R.drawable.staron);
                        }
                    }
                });
            } catch (Exception e) {


            }
            String active;
            if(billModel.getHistory())
                active = "active";
            else
                active="new";
            billid.setText(billModel.getBill_id().toString());
            if(billModel.getShort_title()!=null)
                title.setText(billModel.getShort_title().toString());
            else
                title.setText("N.A.");

             if(billModel.getBill_type().toString()!=null)
              billtype.setText(billModel.getBill_type().toString());
             else
              billtype.setText("N.A.");

            if(billModel.getSponsor().toString()!=null)
             sponsor.setText(billModel.getSponsor().toString());
            else
             sponsor.setText("N.A.");
            if(billModel.getChamber().toString()!=null)
            chamber.setText(billModel.getChamber().toString());
            else
             chamber.setText("N.A.");
            status.setText(active);

            if(billModel.getIntroduced_on().toString()!=null)
            introducedon.setText(billModel.getIntroduced_on().toString());
            else
            introducedon.setText("N.A.");



            if(billModel.getUrls().toString()!=null)
             congressurl.setText(billModel.getUrls().toString());
            else
             congressurl.setText("N.A.");




            try {
                if(billModel.getLastversion().toString()!=null)
                    versionname.setText(billModel.getLastversion().toString());
                else
                    versionname.setText("N.A.");
            }
            catch (Exception e) {
                versionname.setText("N.A.");
            }

            try {
                if(billModel.getpdfUrl().toString()!=null)
                    billurl.setText(billModel.getpdfUrl().toString());
            }
            catch (Exception e) {
                billurl.setText("N.A.");
            }





        }

    }

    private void setUpUIViews() {
        fav = (ImageView)findViewById(R.id.fav);
        billid = (TextView)findViewById(R.id.billid);
        title = (TextView)findViewById(R.id.title);
        billtype = (TextView)findViewById(R.id.billtype);
        sponsor = (TextView)findViewById(R.id.sponsor);
        chamber = (TextView)findViewById(R.id.chamber);
        status = (TextView)findViewById(R.id.status);
        introducedon = (TextView)findViewById(R.id.introducedon);
        congressurl = (TextView)findViewById(R.id.congressurl);
        versionname = (TextView)findViewById(R.id.versionstatus);
        billurl = (TextView)findViewById(R.id.billurl);

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

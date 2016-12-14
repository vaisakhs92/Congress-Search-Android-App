package com.homework.vaisakh.congressapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homework.vaisakh.congressapi.models.BillModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vaisakh on 11/13/16.
 */

public class FavoriteTwo extends Fragment {

    View view;
    SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy  hh:mm");
    ListView listView;
    private final String URL_TO_HIT = "http://responsivespa.us-west-2.elasticbeanstalk.com/compute.php?appdate=bills";
    private ProgressDialog dialog;
    FavItems favItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bill_one, container,false);



        listView = (ListView)view.findViewById(R.id.mainMenubill1);


        new JSONTaskBills().execute(URL_TO_HIT);
        favItems = (FavItems) getActivity().getApplicationContext();
        return view;
    }

    public class JSONTaskBills extends AsyncTask<String,String, List<BillModel> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // dialog.show();
        }

        @Override
        protected List<BillModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                List<BillModel> billmodellist = new ArrayList<>();

                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    BillModel billModel = gson.fromJson(finalObject.toString(), BillModel.class);
                    if(favItems.getLegidIdSet().contains(billModel.getBill_id()))
                    billmodellist.add(billModel);
                }


                return billmodellist;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }

        @Override
        protected void onPostExecute(final List<BillModel> result) {
            super.onPostExecute(result);

            if(result != null) {


                LegisAdapter adapter = new LegisAdapter(getActivity(), R.layout.billsrowlayout, result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         BillModel billModel = result.get(position);
                         Intent intent = new Intent(getActivity(), ViewDetailBills.class);
                         intent.putExtra("billmodel", new Gson().toJson(billModel));
                         startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }

    }

        public class LegisAdapter extends ArrayAdapter{

            private List<BillModel> billModelList;
            private int resource;
            private LayoutInflater inflater;
            public LegisAdapter(Context context, int resource, List<BillModel> objects) {
                super(context, resource, objects);
                billModelList = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder = null;

                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = inflater.inflate(resource, null);
                    holder.billid = (TextView)convertView.findViewById(R.id.billid);
                    holder.shorttitle = (TextView)convertView.findViewById(R.id.shorttitle);
                    holder.introducedon = (TextView)convertView.findViewById(R.id.introducedon);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if(billModelList.get(position).getShort_title()==null)
                    billModelList.get(position).setShort_title("Null");
                holder.billid.setText(billModelList.get(position).getBill_id().toString());
                holder.shorttitle.setText(billModelList.get(position).getShort_title().toString());
                holder.introducedon.setText(billModelList.get(position).getIntroduced_on().toString());



                return convertView;
            }


            class ViewHolder{

                private TextView billid;
                private TextView shorttitle;
                private TextView introducedon;


            }

        }








}

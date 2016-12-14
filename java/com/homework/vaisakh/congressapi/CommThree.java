package com.homework.vaisakh.congressapi;

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
import com.homework.vaisakh.congressapi.models.CommModel;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by vaisakh on 11/13/16.
 */

public class CommThree extends Fragment {

    View view;
    ArrayList<String> names = new ArrayList<String>();
    ListView listView;
    private final String URL_TO_HIT = "http://responsivespa.us-west-2.elasticbeanstalk.com/compute.php?appdate=committees";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.comm_one, container,false);



        listView = (ListView)view.findViewById(R.id.mainMenucomm1);


        new JSONTask().execute(URL_TO_HIT);

        return view;
    }

    public class JSONTask extends AsyncTask<String,String, List<CommModel> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // dialog.show();

        }

        @Override
        protected List<CommModel> doInBackground(String... params) {
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

                List<CommModel> commModelList = new ArrayList<>();

                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    CommModel commModel = gson.fromJson(finalObject.toString(), CommModel.class);
                    if(commModel.getChamber().equalsIgnoreCase("joint"))
                    commModelList.add(commModel);
                }

                Collections.sort(commModelList, new Comparator<CommModel>() {
                    public int compare(CommModel m1, CommModel m2) {
                        return m1.getName().compareTo(m2.getName());
                    }
                });
                return commModelList;

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
        protected void onPostExecute(final List<CommModel> result) {
            super.onPostExecute(result);

            if(result != null) {


                CommAdapter adapter = new CommAdapter(getActivity(), R.layout.commrowlayout, result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CommModel commModelList = result.get(position);
                         Intent intent = new Intent(getActivity(), ViewDetailComm.class);
                         intent.putExtra("commmodel", new Gson().toJson(commModelList));

                         startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }

    }

        public class CommAdapter extends ArrayAdapter{

            private List<CommModel> commModelList;
            private int resource;
            private LayoutInflater inflater;
            public CommAdapter(Context context, int resource, List<CommModel> objects) {
                super(context, resource, objects);
                commModelList = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder = null;

                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = inflater.inflate(resource, null);
                    holder.commid = (TextView) convertView.findViewById(R.id.commid);
                    holder.commname = (TextView)convertView.findViewById(R.id.commname);
                    holder.commchamber = (TextView)convertView.findViewById(R.id.commchamber);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.commid.setText(commModelList.get(position).getCommittee_id().toString());
                holder.commname.setText(commModelList.get(position).getName().toString());
                holder.commchamber.setText(commModelList.get(position).getChamber().toString());

                return convertView;
            }


            class ViewHolder{
                private TextView commid;
                private TextView commname;
                private TextView commchamber;


            }

        }








}

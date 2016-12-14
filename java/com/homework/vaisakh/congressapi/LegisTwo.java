package com.homework.vaisakh.congressapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homework.vaisakh.congressapi.models.LegisModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by vaisakh on 11/13/16.
 */

public class LegisTwo extends Fragment {

    View view;
    ListView listView;
    LegisModel legisModel;
    ArrayList<String> names = new ArrayList<String>();
    private final String URL_TO_HIT = "http://phpsample.us-west-2.elasticbeanstalk.com/compute.php?appdate=legishouse";
    private ProgressDialog dialog;
    LinkedHashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Legis2","got in 2");
        view = inflater.inflate(R.layout.legis_two, container,false);

        listView = (ListView)view.findViewById(R.id.mainMenulegis2);

        new JSONTask().execute(URL_TO_HIT);

        return view;
    }

    public void getIndexList(ArrayList<String> states) {

        for(int i=0;i<states.size();++i) {
            String state = states.get(i);
            String index = state.substring(0,1);

            if(mapIndex.get(index)==null)
                mapIndex.put(index,i);
        }

    }
    public void displayIndex() {
        LinearLayout indexLayout = (LinearLayout)view.findViewById(R.id.side_index);

        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for(String index: indexList) {
            textView = (TextView)getActivity().getLayoutInflater().inflate(R.layout.size_index_item,null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    TextView selectedIndex = (TextView) view;
                    listView.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }


    }

    public class JSONTask extends AsyncTask<String,String, List<LegisModel> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // dialog.show();

        }

        @Override
        protected List<LegisModel> doInBackground(String... params) {
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

                List<LegisModel> legisModelList = new ArrayList<>();

                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    legisModel = gson.fromJson(finalObject.toString(), LegisModel.class);
                    legisModelList.add(legisModel);
                }
                Collections.sort(legisModelList,
                        new Comparator<LegisModel>()
                        {
                            public int compare(LegisModel f1, LegisModel f2)
                            {
                                return f1.getLastname().toString().compareTo(f2.getLastname().toString());
                            }
                        });
                for(int i=0; i<parentArray.length(); i++) {
                    names.add(legisModelList.get(i).getLastname());
                }
                getIndexList(names);
                return legisModelList;

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
        protected void onPostExecute(final List<LegisModel> result) {
            displayIndex();
            super.onPostExecute(result);

            if(result != null) {

                LegisAdapter adapter = new LegisAdapter(getActivity(), R.layout.legisrowlayout, result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         LegisModel legisModel = result.get(position);
                         Intent intent = new Intent(getActivity(), ViewDetailLegis.class);
                         intent.putExtra("legisModel", new Gson().toJson(legisModel));

                         startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }

    }

        public class LegisAdapter extends ArrayAdapter{

            private List<LegisModel> legisModelList;
            private int resource;
            private LayoutInflater inflater;
            public LegisAdapter(Context context, int resource, List<LegisModel> objects) {
                super(context, resource, objects);
                legisModelList = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder = null;

                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = inflater.inflate(resource, null);
                    holder.icon = (ImageView)convertView.findViewById(R.id.icon);
                    holder.info = (TextView)convertView.findViewById(R.id.info);
                    holder.name = (TextView)convertView.findViewById(R.id.name);
                    holder.progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.info.setText("("+legisModelList.get(position).getParty().toString()+")"+" "+legisModelList.get(position).getState_name().toString()+" "+String.valueOf("District:"+legisModelList.get(position).getDistrict()));
                holder.name.setText(legisModelList.get(position).getLastname().toString()+","+legisModelList.get(position).getFirstname().toString());
                final ViewHolder finalHolder = holder;
                String url = "https://theunitedstates.io/images/congress/original/" + legisModelList.get(position).getBioguideid().toString() + ".jpg";
                if(legisModelList.get(position).getBioguideid().equalsIgnoreCase("L000585")) {
                    Picasso.with(getActivity().getApplicationContext()).load(url).into(holder.icon, new Callback() {

                        @Override
                        public void onSuccess() {
                            finalHolder.icon.setVisibility(View.VISIBLE);
                            finalHolder.progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            finalHolder.icon.setVisibility(View.VISIBLE);
                            finalHolder.progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else {
                    Picasso.with(getActivity().getApplicationContext()).load(url).resize(70,70).into(holder.icon, new Callback() {

                        @Override
                        public void onSuccess() {
                            finalHolder.icon.setVisibility(View.VISIBLE);
                            finalHolder.progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            finalHolder.icon.setVisibility(View.VISIBLE);
                            finalHolder.progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                return convertView;
            }


            class ViewHolder{
                private ImageView icon;
                private TextView info;
                private TextView name;
                private ProgressBar progressBar;

            }

        }








}

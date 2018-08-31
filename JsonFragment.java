package in.project.android.indiatouristplaces;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JsonFragment extends Fragment {

    ArrayList<String> cities;
    ArrayList<String> StateArray;
    ImageAdapter adap;
    ExpListAdapter adapter;
    ListView lv;
    ProgressBar pb;
    ExpandableListView elv;
    DrawerLayout drawer;
    private String TAG = JsonHome.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_json_home,container,false);
        getActivity().setTitle(R.string.app_name);
        cities = new ArrayList<>();
        StateArray = new ArrayList<>();
        lv = (ListView)v.findViewById(R.id.states_list);
        elv = (ExpandableListView)getActivity().findViewById(R.id.states_explv);

        drawer =(DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        StateWS callWS = new StateWS();
        callWS.execute();
        return v;
    }

    private class StateWS extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            String url = "http://garima.archiadc.com/ws1/webservice1.asmx/statesList";
            HttpHandler handler = new HttpHandler();
            String jsonStr = handler.makeServiceCall(url);
            Log.e(TAG,"Response from URL:   "+jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray stateList = jsonObj.getJSONArray("StateList");
                    // looping through All States
                    for (int i = 0; i < stateList.length(); i++) {
                        JSONObject c = stateList.getJSONObject(i);
                        String stateName = c.getString("stateName");
                        String stateUrl = c.getString("stateUrl");
                        //HashMap<String, String> state = new HashMap<>();
                        //state.put("stateName",stateName);
                        //state.put("stateUrl",stateUrl);
                        StateArray.add(stateUrl);
                        cities.add(stateName);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getApplicationContext(),
                            //      "Json parsing error: " + e.getMessage(),
                            //    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String filepath = getActivity().getApplicationContext().getExternalCacheDir()+"/StatesCache/";
                        File file = new File(filepath);

                        if(file.list()!=null) {
                            File[] content = file.listFiles();
                            Arrays.sort(content);
                            //Toast.makeText(JsonHome.this, "Array Length"+content.length, Toast.LENGTH_SHORT).show();
                            if (content == null) {

                            } else if (content.length == 0) {
                                //Toast.makeText(JsonHome.this, "No file", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(content.length==36)
                                {
                                    //Load files from cache
                                    for (int i=0;i<content.length;i++)
                                    {
                                        String filename = content[i].getName();
                                        String filePath = getActivity().getApplicationContext().getExternalCacheDir()+"/StatesCache/"+filename;
                                        StateArray.add(filePath);
                                        cities.add(filename);
                                    }
                                    System.out.println("cache files:   "+StateArray+cities);
                                }
                                else {
                                    Toast.makeText(getActivity(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();

            pb = new ProgressBar(getActivity().getApplicationContext());
            //pb.getIndeterminateDrawable().setColorFilter(0x02a0ff, PorterDuff.Mode.SRC_IN);

        }

        @Override
        protected void onPostExecute(Void result)
        {
            adap = new ImageAdapter(getActivity(),StateArray,cities);
            String states = "States";
            HashMap<String,ArrayList<String>> statesList = new HashMap<String, ArrayList<String>>();
            ArrayList<String> Titles = new ArrayList<String>();
            Titles.add(states);
            statesList.put(Titles.get(0),cities);
            adapter = new ExpListAdapter(getActivity(),Titles,statesList);

            lv.setAdapter(adap);
            elv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
                {
                    FragmentsList(position);
                }
            });

            elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    FragmentsList(childPosition);
                    parent.collapseGroup(groupPosition);
                    drawer.closeDrawers();
                    return false;
                }
            });
        }

        private void FragmentsList(int position){
            FragmentManager fm = getFragmentManager();
            switch (position){
                case 0:
                    Fragment andamanNicobar = new AndamanNicobar();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.replace(R.id.activity_json_home,andamanNicobar);
                    ft.addToBackStack(null);
                    ft.commit();
                    break;

                case 1:
                    Fragment andhraPradesh = new AndhraPradesh();
                    FragmentTransaction ft1 = fm.beginTransaction();
                    ft1.replace(R.id.activity_json_home,andhraPradesh);
                    ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft1.addToBackStack(null);
                    ft1.commit();
                    break;

                case 2:
                    Fragment arunachalPradesh = new ArunachalPradesh();
                    FragmentTransaction ft2 = fm.beginTransaction();
                    ft2.replace(R.id.activity_json_home,arunachalPradesh);
                    ft2.addToBackStack(null);
                    ft2.commit();
                    break;

                case 3:
                    Fragment assam = new Assam();
                    FragmentTransaction ft3 = fm.beginTransaction();
                    ft3.replace(R.id.activity_json_home,assam)
                            .addToBackStack(null)
                            .commit();
                    break;
                case 4:
                    Fragment bihar = new Bihar();
                    FragmentTransaction ft4 = fm.beginTransaction();
                    ft4.replace(R.id.activity_json_home,bihar)
                            .addToBackStack(null)
                            .commit();
                    break;

                case 5:
                    Fragment chandigarh = new Chandigarh();
                    FragmentTransaction ft5 = fm.beginTransaction();
                    ft5.replace(R.id.activity_json_home,chandigarh)
                            .addToBackStack(null)
                            .commit();
                    break;

                case 6:
                    Fragment chattisgarh = new Chattisgarh();
                    FragmentTransaction ft6 = fm.beginTransaction();
                    ft6.replace(R.id.activity_json_home,chattisgarh)
                            .addToBackStack(null)
                            .commit();
                    break;

                case 7:
                    Fragment dadraNagarHaveli = new DadraNagarHaveli();
                    FragmentTransaction ft7 = fm.beginTransaction();
                    ft7.replace(R.id.activity_json_home,dadraNagarHaveli)
                            .addToBackStack(null);
                    ft7.commit();
                    break;

                case 8:
                    Fragment damanDiu = new DamanDiu();
                    FragmentTransaction ft8 = fm.beginTransaction();
                    ft8.replace(R.id.activity_json_home,damanDiu)
                            .addToBackStack(null);
                    ft8.commit();
                    break;

                case 9:
                    Fragment goa = new Goa();
                    FragmentTransaction ft9 = fm.beginTransaction();
                    ft9.replace(R.id.activity_json_home,goa)
                            .addToBackStack(null);
                    ft9.commit();
                    break;

                case 10:
                    Fragment gujarat = new Gujarat();
                    FragmentTransaction ft10 = fm.beginTransaction();
                    ft10.replace(R.id.activity_json_home,gujarat).addToBackStack(null);
                    ft10.commit();
                    break;

                case 11:
                    Fragment haryana = new Haryana();
                    FragmentTransaction ft11 = fm.beginTransaction();
                    ft11.replace(R.id.activity_json_home,haryana).addToBackStack(null);
                    ft11.commit();
                    break;

                case 12:
                    Fragment himachalPradesh = new HimachalPradesh();
                    FragmentTransaction ft12 = fm.beginTransaction();
                    ft12.replace(R.id.activity_json_home,himachalPradesh).addToBackStack(null);
                    ft12.commit();
                    break;

                case 13:
                    Fragment jammuKashmir = new JammuKashmir();
                    FragmentTransaction ft13 = fm.beginTransaction();
                    ft13.replace(R.id.activity_json_home,jammuKashmir).addToBackStack(null);
                    ft13.commit();
                    break;

                case 14:
                    Fragment jharkhand = new Jharkhand();
                    FragmentTransaction ft14 = fm.beginTransaction();
                    ft14.replace(R.id.activity_json_home,jharkhand).addToBackStack(null);
                    ft14.commit();
                    break;

                case 15:
                    Fragment karnataka = new Karnataka();
                    FragmentTransaction ft15 = fm.beginTransaction();
                    ft15.replace(R.id.activity_json_home,karnataka).addToBackStack(null);
                    ft15.commit();
                    break;

                case 16:
                    Fragment kerala = new Kerala();
                    FragmentTransaction ft16 = fm.beginTransaction();
                    ft16.replace(R.id.activity_json_home,kerala).addToBackStack(null);
                    ft16.commit();
                    break;

                case 17:
                    Fragment lakshadweep = new Lakshadweep();
                    FragmentTransaction ft17 = fm.beginTransaction();
                    ft17.replace(R.id.activity_json_home,lakshadweep).addToBackStack(null);
                    ft17.commit();
                    break;

                case 18:
                    Fragment madhyaPradesh = new MadhyaPradesh();
                    FragmentTransaction ft18 = fm.beginTransaction();
                    ft18.replace(R.id.activity_json_home,madhyaPradesh).addToBackStack(null);
                    ft18.commit();
                    break;

                case 19:
                    Fragment maharashtra = new Maharashtra();
                    FragmentTransaction ft19 = fm.beginTransaction();
                    ft19.replace(R.id.activity_json_home,maharashtra).addToBackStack(null);
                    ft19.commit();
                    break;

                case 20:
                    Fragment manipur = new Manipur();
                    FragmentTransaction ft20 = fm.beginTransaction();
                    ft20.replace(R.id.activity_json_home,manipur).addToBackStack(null);
                    ft20.commit();
                    break;

                case 21:
                    Fragment meghalaya = new Meghalaya();
                    FragmentTransaction ft21 = fm.beginTransaction();
                    ft21.replace(R.id.activity_json_home,meghalaya).addToBackStack(null);
                    ft21.commit();
                    break;

                case 22:
                    Fragment mizoram = new Mizoram();
                    FragmentTransaction ft22 = fm.beginTransaction();
                    ft22.replace(R.id.activity_json_home,mizoram).addToBackStack(null);
                    ft22.commit();
                    break;

                case 23:
                    Fragment nagaland = new Nagaland();
                    FragmentTransaction ft23 = fm.beginTransaction();
                    ft23.replace(R.id.activity_json_home,nagaland).addToBackStack(null);
                    ft23.commit();
                    break;

                case 24:
                    Fragment newDelhi = new NewDelhi();
                    FragmentTransaction ft24 = fm.beginTransaction();
                    ft24.replace(R.id.activity_json_home,newDelhi).addToBackStack(null);
                    ft24.commit();
                    break;

                case 25:
                    Fragment odisha = new Odisha();
                    FragmentTransaction ft25 = fm.beginTransaction();
                    ft25.replace(R.id.activity_json_home,odisha).addToBackStack(null);
                    ft25.commit();
                    break;

                case 26:
                    Fragment puducherry = new Puducherry();
                    FragmentTransaction ft26 = fm.beginTransaction();
                    ft26.replace(R.id.activity_json_home,puducherry).addToBackStack(null);
                    ft26.commit();
                    break;

                case 27:
                    FragmentTransaction ft27 = fm.beginTransaction();
                    Fragment punjab = new Punjab();
                    ft27.replace(R.id.activity_json_home,punjab)
                            .addToBackStack(null)
                            .commit();
                    break;

                case 28:
                    FragmentTransaction ft28 = fm.beginTransaction();
                    Fragment rajasthan = new Rajasthan();
                    ft28.replace(R.id.activity_json_home,rajasthan)
                            .addToBackStack(null)
                            .commit();
                    break;

                case 29:
                    FragmentTransaction ft29 = fm.beginTransaction();
                    Fragment sikkim = new Sikkim();
                    ft29.replace(R.id.activity_json_home,sikkim).addToBackStack(null);
                    ft29.commit();
                    break;

                case 30:
                    FragmentTransaction ft30 = fm.beginTransaction();
                    Fragment tamilNadu = new TamilNadu();
                    ft30.replace(R.id.activity_json_home,tamilNadu).addToBackStack(null);
                    ft30.commit();
                    break;

                case 31:
                    FragmentTransaction ft31 = fm.beginTransaction();
                    Fragment telangana = new Telangana();
                    ft31.replace(R.id.activity_json_home,telangana).addToBackStack(null);
                    ft31.commit();
                    break;

                case 32:
                    FragmentTransaction ft32 = fm.beginTransaction();
                    Fragment tripura = new Tripura();
                    ft32.replace(R.id.activity_json_home,tripura).addToBackStack(null);
                    ft32.commit();
                    break;

                case 33:
                    Fragment uttarPradesh = new UttarPradesh();
                    FragmentTransaction ft33 = fm.beginTransaction();
                    ft33.replace(R.id.activity_json_home,uttarPradesh).addToBackStack(null);
                    ft33.commit();
                    break;

                case 34:
                    Fragment uttrakhand = new Uttrakhand();
                    FragmentTransaction ft34 = fm.beginTransaction();
                    ft34.replace(R.id.activity_json_home,uttrakhand).addToBackStack(null);
                    ft34.commit();
                    break;

                case 35:
                    Fragment westBengal = new WestBengal();
                    FragmentTransaction ft35 = fm.beginTransaction();
                    ft35.replace(R.id.activity_json_home,westBengal).addToBackStack(null);
                    ft35.commit();
                    break;
                default:
                    break;
            }
        }
    }


}

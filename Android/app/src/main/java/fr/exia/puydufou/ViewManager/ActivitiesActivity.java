package fr.exia.puydufou.ViewManager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.exia.puydufou.R;
import fr.exia.puydufou.RestResponse.GetAllActiviteRest;
import fr.exia.puydufou.ViewModel.activityListViewModel;

/**
 * Created by araguin on 17/06/15.
 */
public class ActivitiesActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_activites);
            List<Map<String,String>> resultList = new ArrayList<Map<String, String>>();

            ListView activityList = (ListView) findViewById(R.id.ActivityList);
            resultList.add(createActivityItem("activite","Les Vickings"));
            resultList.add(createActivityItem("activite","Le signe du Triomphe"));
            resultList.add(createActivityItem("activite","Le Magicien Ménéstrel"));
            resultList.add(createActivityItem("activite","Les Automates Musiciens"));
            resultList.add(createActivityItem("activite","Les Grandes eaux"));
            resultList.add(createActivityItem("activite", "Les îles de Clovis"));

            activityList.setAdapter(new SimpleAdapter(this, resultList, android.R.layout.simple_list_item_1, new String[]{"activite"}, new int[]{android.R.id.text1}));
            activityList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id){
                    Intent intent = new Intent(ActivitiesActivity.this, DetailActiviteActivity.class);
                    ActivitiesActivity.this.startActivity(intent);
                }

            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.home, menu);
            return true;
        }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


    private HashMap<String,String> createActivityItem(String key, String name){
        HashMap<String,String> activity = new HashMap<String,String>();
        activity.put(key,name);
        return activity;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, GetAllActiviteRest>{
        @Override
        protected GetAllActiviteRest doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:8080/api/activites";
                RestTemplate restTemplate = new RestTemplate();
                List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
                messageConverters.add(new MappingJackson2HttpMessageConverter());
                restTemplate.setMessageConverters(messageConverters);
                //List<activityListViewModel> resultList = Arrays.asList(restTemplate.getForObject(url, activityListViewModel[].class));
                GetAllActiviteRest greeting = restTemplate.getForObject(url, GetAllActiviteRest.class);
                //Log.e("Worked!!!!!!!!!!! ", greeting);
                return greeting;
            } catch (Exception e) {
                Log.e("didn't work!!!!!!!!!!", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(GetAllActiviteRest greeting) {

            //Log.e("MainActivity", greeting.toString());

            //TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            //greetingIdText.setText(greeting.getId());
            //greetingContentText.setText(greeting.getContent());


        }

    }
}

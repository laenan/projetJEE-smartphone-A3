package fr.exia.puydufou.ViewManager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import fr.exia.puydufou.R;
import fr.exia.puydufou.RestResponse.GetAllActiviteRest;
import fr.exia.puydufou.ViewModel.detailActiviteViewModel;

/**
 * Created by Asvina on 17/06/2015.
 */

    public class DetailActiviteActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_activite);

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


    private class HttpRequestTask extends AsyncTask<Void, Void, GetAllActiviteRest>{
        @Override
        protected GetAllActiviteRest doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:8080/api/activites/1";
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


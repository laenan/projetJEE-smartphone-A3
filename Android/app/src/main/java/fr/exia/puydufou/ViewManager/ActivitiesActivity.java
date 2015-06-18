package fr.exia.puydufou.ViewManager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import fr.exia.puydufou.R;
import fr.exia.puydufou.ViewModel.activityListViewModel;

/**
 * Created by araguin on 17/06/15.
 */
public class ActivitiesActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_activites);

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

    private class HttpRequestTask extends AsyncTask<Void, Void, activityListViewModel> {
        @Override
        protected activityListViewModel doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:8080/api/parcs";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                activityListViewModel greeting = restTemplate.getForObject(url, activityListViewModel.class);
                Log.e("Workded!!!!!!!!!!! ", greeting.getContent());
                return greeting;
            } catch (Exception e) {
                Log.e("didn't work!!!!!!!!!!", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(activityListViewModel greeting) {
            Log.e("MainActivity", greeting.getContent());
            //TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            //TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            //greetingIdText.setText(greeting.getId());
            //greetingContentText.setText(greeting.getContent());
        }

    }
}

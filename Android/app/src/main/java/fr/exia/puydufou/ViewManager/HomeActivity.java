package fr.exia.puydufou.ViewManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import fr.exia.puydufou.Database.SQLiteDatabaseHandler;
import fr.exia.puydufou.R;

/**
 * Created by araguin on 17/06/15.
 */
public class HomeActivity extends Activity {

    SQLiteDatabaseHandler db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);

        db = new SQLiteDatabaseHandler(this);

        Button buttonActivities = (Button)this.findViewById(R.id.buttonActivities);
        buttonActivities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ActivitiesActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });

        Button buttonParcours = (Button)this.findViewById(R.id.buttonParcours);
        buttonParcours.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ParcoursActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });

        Button buttonPlanning = (Button)this.findViewById(R.id.buttonPlanning);
        buttonPlanning.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PlanningActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.home, menu);
        return true;
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



    }


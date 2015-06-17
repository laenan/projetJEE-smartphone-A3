package fr.exia.puydufou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
<<<<<<< HEAD

    final Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, LoginDisplayActivity.class);
                startActivity(intent);

=======
    final Button button=(Button) findViewById(R.id.button) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        int i;
>>>>>>> 6678ccf6bdb6c1b347496e2e357a8a2b7affdd2c
            }
        });
    }



}


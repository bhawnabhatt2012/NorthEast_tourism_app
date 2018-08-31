package in.project.android.indiatouristplaces;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
ProgressBar PrgBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        PrgBar = (ProgressBar)findViewById(R.id.pg1);
        //PrgBar.getIndeterminateDrawable().setColorFilter(0x02a0ff, PorterDuff.Mode.SRC_IN);

        Thread th = new Thread()
        {
          public void run()
          {
              try{
                    sleep(3000);
              }
              catch (Exception ex)
              {
              }
              finally {
                  Intent i = new Intent(SplashScreen.this,JsonHome.class);
                  startActivity(i);

              }
              //
          }

        };

        th.start();

    }

    @Override
    protected void onPause() {
        //Toast.makeText(getApplicationContext(),"Splash Screen",Toast.LENGTH_SHORT).show();
        super.onPause();
        super.finish();
    }
}

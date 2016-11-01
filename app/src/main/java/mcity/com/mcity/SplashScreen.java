package mcity.com.mcity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Admin on 27-09-2016.
 */
public class SplashScreen extends Activity {
    private static String TAG = SplashScreen.class.getName();
    private static long SLEEP_TIME = 5;	// Sleep for some time
    String check;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);	// Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar
        setContentView(R.layout.splash_activity);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
        //check = sharedPreferences.getString("check", "");
        check = sharedPreferences.getString("login_status", "");
        Log.e("tag","lllll"+check);

        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {

        @Override
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME*1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }


           /* if(check.equals("login_success"))
            {
                Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
            else
            {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }*/



            if(check.equals("true"))
            {
                Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
            else if(check.equals("false"))
            {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
            else
            {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }

        }





        }
    }




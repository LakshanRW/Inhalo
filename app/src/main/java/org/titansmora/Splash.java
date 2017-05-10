package org.titansmora;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private static TextView textView;
    private static String uid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        changeAppNameTextSizeSplash();
        textView = (TextView) findViewById(R.id.textView);
        //finish splash and start Home Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //check user logged into Application, if user is not logged in should be start Account activity for SignUp of Login

               if (TextUtils.isEmpty(uid))
               {


                Intent startAccountActivity = new Intent(Splash.this,AccountActivity.class);
           startActivity(startAccountActivity);
                   finish();
               }
               else {
                   startActivity(new Intent(Splash.this, HomeActivity.class));
                   finish();
               }
            }
        },1250);

        //end
    }

    private void changeAppNameTextSizeSplash() {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               textView.setTextSize(20);
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       textView.setTextSize(22);
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               textView.setTextSize(23);
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       textView.setTextSize(26);
                                       new Handler().postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               textView.setTextSize(28);
                                               new Handler().postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       textView.setTextSize(30);
                                                       new Handler().postDelayed(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               textView.setTextSize(32);
                                                               new Handler().postDelayed(new Runnable() {
                                                                   @Override
                                                                   public void run() {
                                                                       textView.setTextSize(34);
                                                                       new Handler().postDelayed(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               textView.setTextSize(35);
                                                                               new Handler().postDelayed(new Runnable() {
                                                                                   @Override
                                                                                   public void run() {
                                                                                       textView.setTextSize(36);
                                                                                       new Handler().postDelayed(new Runnable() {
                                                                                           @Override
                                                                                           public void run() {
                                                                                               textView.setTextSize(37);
                                                                                               new Handler().postDelayed(new Runnable() {
                                                                                                   @Override
                                                                                                   public void run() {
                                                                                                       textView.setTextSize(38);
                                                                                                   }
                                                                                               },100);
                                                                                           }
                                                                                       },100);
                                                                                   }
                                                                               },100);
                                                                           }
                                                                       },100);
                                                                   }
                                                               },100);
                                                           }
                                                       },100);
                                                   }
                                               },100);
                                           }
                                       },100);
                                   }
                               },100);
                           }
                       },100);
                   }
               },100);
           }
       },100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //get user id from Shared Data
        uid = getSharedPreferences("PREFERENCES",MODE_PRIVATE).getString("uid",null);
        //end
    }
}

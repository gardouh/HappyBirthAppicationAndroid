package com.example.mac.myapplication;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {


    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent;
        intent = new Intent(this,Accueil.class);
        loginButton =(LoginButton)findViewById(R.id.login_button);
        //textView=(TextView)findViewById(R.id.textView) ;
        callbackManager = CallbackManager.Factory.create();
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Log.e("Connecte","co");
            String FirstName= profile.getFirstName();
            String LastName= profile.getLastName();
            String iduserfb= profile.getId();

            Uri UriImage= profile.getProfilePictureUri(100,100);


            Log.e("First Name",FirstName);
            intent.putExtra("firstname", FirstName);
            intent.putExtra("lastname", LastName);
            intent.putExtra("iduserfb", iduserfb);
            intent.putExtra("uri", String.valueOf(UriImage));


            //   Log.e("Uri image",s2.toString());


            startActivity(intent);

        } else {

            Log.e("Non connecte","co");

        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                textView.setText("Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                textView.setText("Error");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}

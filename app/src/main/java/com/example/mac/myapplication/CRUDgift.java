package com.example.mac.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CRUDgift extends Dialog {

    JSONParser jsonParser = new JSONParser();
    private int idPosition;
    public Context context;
    public ListView mListView;
    private String DELETE_URL =
            "http://gardouhkhalilproject.esy.es/gifts/delete.php";
    public CRUDgift(@NonNull Context context, int idPosition, ListView mListView) {
        super(context);
        this.idPosition=idPosition;
        this.context=context;
        this.mListView=mListView;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.crudgift);
        super.onCreate(savedInstanceState);

        final Button btnDelate=(Button) findViewById(R.id.btn_delete);
        btnDelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             new DeletItem(idPosition).execute();
                dismiss();
            }
        });
    }
private class DeletItem extends AsyncTask<Void,Void,Boolean>{
    private ProgressDialog mProgressDialog;

    private JSONObject jsonObjectResult = null;

    private String error;
    private int i;

    public DeletItem(int idPosition) {
        this.i=idPosition;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
          pairs.add(new BasicNameValuePair("id_gift", String.valueOf(i)));
        Log.e("Position",String.valueOf(i));
      //  Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_LONG);
       // toast.show();
        try {
            jsonObjectResult = jsonParser.makeHttpRequest(DELETE_URL,"POST", pairs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonObjectResult == null)
        {
            error = "Error in the connection";
            Log.e("Ereur",error);

            return false;
        }

        try
        {
            if (jsonObjectResult.getInt("success") == 1)
            {

                error = jsonObjectResult.getString("message");

                Log.e("Success",error);

                return true;
            }
            else
                error = jsonObjectResult.getString("message");
                Log.e("Erreur",error);


        }
        catch (Exception ex)
        {
            Log.e("Exception",ex.toString());

        }



        return null;
    }




    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        new GetNewsTask(context,mListView).execute();
    }
}

}

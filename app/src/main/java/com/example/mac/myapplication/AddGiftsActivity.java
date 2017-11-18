package com.example.mac.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class AddGiftsActivity extends Dialog {

    private Activity mActivity;

    private EditText mNewsTitleET;
    private EditText mNewsET;
    private EditText mBoxPrix;

    private Button mPublishBtn;

    JSONParser jsonParser = new JSONParser();
    public ListView mListview;
    private String ADD_URL =
            "http://gardouhkhalilproject.esy.es/gifts/addgifts.php";

    public AddGiftsActivity(Activity mActivity)
    {
        super(mActivity);
        this.mActivity = mActivity;
    }
    public AddGiftsActivity(Activity mActivity,ListView listView)
    {
        super(mActivity);
        this.mActivity = mActivity;
        this.mListview=listView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_gifts);

        mNewsTitleET = (EditText) findViewById(R.id.title_news);
        mNewsET      = (EditText) findViewById(R.id.news_box);
        mPublishBtn  = (Button) findViewById(R.id.publish_btn);
        mBoxPrix  = (EditText) findViewById(R.id.box_prix);

        mPublishBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attempAdding();
            }
        });
    }

    private void attempAdding()
    {
        if (!mNewsTitleET.getText().toString().equals("") &&
                !mNewsET.getText().toString().equals(""))
        {
            new AddNewsTask().execute();
        }
        else
        {
            Toast.makeText(mActivity.getApplicationContext(),
                    "All fields are requires", Toast.LENGTH_LONG).show();
        }
    }

    private class AddNewsTask extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog mProgressDialog;

        private JSONObject jsonObjectResult = null;

        private String error;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(AddGiftsActivity.this.getContext(),
                    "Processing...", "", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
     //       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //        Date date = new Date();


            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
          //  pairs.add(new BasicNameValuePair("news_date", dateFormat.format(date).toString()));
            pairs.add(new BasicNameValuePair("idfacebook",Accueil.getId() ));
            pairs.add(new BasicNameValuePair("nom_gift", mNewsTitleET.getText().toString()));
            pairs.add(new BasicNameValuePair("description_gift", mNewsTitleET.getText().toString()));
            pairs.add(new BasicNameValuePair("prix_gift", mBoxPrix.getText().toString()));

            try {
                jsonObjectResult = jsonParser.makeHttpRequest(ADD_URL,"POST", pairs);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jsonObjectResult == null)
            {
                error = "Error in the connection";
                return false;
            }

            try
            {
                if (jsonObjectResult.getInt("success") == 1)
                {
                    error = jsonObjectResult.getString("message");

                    return true;
                }
                else
                    error = jsonObjectResult.getString("message");

            }
            catch (Exception ex)
            {

            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);

            mProgressDialog.dismiss();
            new GetNewsTask(AddGiftsActivity.this.getContext(),mListview).execute();

            Toast.makeText(mActivity.getApplicationContext(), error, Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

}

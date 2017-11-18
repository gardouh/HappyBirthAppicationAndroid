package com.example.mac.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mac.myapplication.ListGifts.ListViewNewsAdapter;
import com.example.mac.myapplication.ListGifts.ListViewNewsItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 07/09/2017.
 */

 class GetNewsTask extends AsyncTask<Void, Void, Boolean>
{
    private String READNEWS_URL =
            "http://gardouhkhalilproject.esy.es/gifts/readgifts.php";
    private JSONParser jsonParser = new JSONParser();

    private ListView mListView;
    private ListViewNewsAdapter listViewNewsAdapter;
    private JSONObject jsonObjectResult=null;
    private ArrayList<ListViewNewsItem> listViewNewsItems;
    private ProgressDialog mProgressDialog;




    private String error;
    Context context;


    GetNewsTask (Context context,ListView mListview){

        this.context=context;
        this.mListView=mListview;

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        try {

        }
        catch (Exception ex){

        }
        listViewNewsItems = new ArrayList<ListViewNewsItem>();
     mProgressDialog = ProgressDialog.show(context,
             "Processing...", "Get last Gifts", false, false);

    }

    @Override
    protected Boolean doInBackground(Void... params)
    {

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("idfacebook",Accueil.getId() ));

        try {
            jsonObjectResult = jsonParser.makeHttpRequest(READNEWS_URL,"POST",pairs);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try
        {
            Log.e("error",jsonObjectResult.toString());

            if (jsonObjectResult == null)
            {
                error = "Error int the connection";
                Log.e("eroor",error);
                return false;
            }


            if (jsonObjectResult.getInt("success") == 1)
            {
                JSONArray jsonArray = jsonObjectResult.getJSONArray("posts");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject news = jsonArray.getJSONObject(i);

                    ListViewNewsItem listViewNewsItem = new ListViewNewsItem
                            (
                                    news.getInt("id_gift"),
                                    news.getString("nom_gift"),
                                    news.getString("date_gift")
                            );
                    listViewNewsItems.add(listViewNewsItem);
                }
                return true;
            }
            else
                error = jsonObjectResult.getString("message");

        }
        catch (Exception ex)
        {
            error = ex.toString();

        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {

        super.onPostExecute(aBoolean);
        if(mProgressDialog != null)
        mProgressDialog.dismiss();
        if (aBoolean)
        {
            listViewNewsAdapter = new ListViewNewsAdapter(context,
                    listViewNewsItems);
           mListView.setAdapter(listViewNewsAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context,String.valueOf(position), Toast.LENGTH_LONG).show();
                    CRUDgift cruDgift=new CRUDgift(context,listViewNewsItems.get(position).getmNewsID(),mListView);
                    cruDgift.show();



                }
            });
        }
       else
           Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}

package com.example.mac.myapplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

/*    static InputStream is = null;  // <= WHAT static ????????
    static JSONObject jObj = null;
    static String json = "";   tout ce truc peut être viré ... */


    private JSONObject jObj;
    private IOException error;


    // constructor (peut être privé, on ne construit jamais l'objet)
    JSONParser() {

    }


    public static JSONObject makeHttpRequest(HttpUriRequest request) throws IOException {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(entity,"UTF-8"); // <= ça résout pas mal de soucis, y compris la gestion de fermeture correcte (même en cas d'exception) du stream.
            return new JSONObject(jsonStr);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Failed HttpRequest",e);
        }
    }

    public static JSONObject makeHttpRequest(String url, String method,  List<NameValuePair> params) throws IOException {
        HttpUriRequest request;

        // == ne marche pas... il compare des objets !!!
        if ("POST".equals(method)) {
            // request method is POST
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            request = httpPost;
        } else if ("GET".equals(method)) {
            // request method is GET


            if (params!=null){

                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;

            }
            request = new HttpGet(url);
        } else {
            throw new IllegalArgumentException("Only POST and GET are allowed method"); // <= oui on se protege soi-même des bêtises qu'on pourrait faire !
        }

        return makeHttpRequest(request);
    }



    public static JSONParser parseHttpRequest(String url, String method,  List<NameValuePair> params) {
        JSONParser ret = new JSONParser();
        try {
            ret.jObj = makeHttpRequest(url, method, params);
        } catch (IOException e) {
            ret.error = e;
        }
        return ret;
    }


    public boolean isSuccess() {
        return (this.error == null);
    }

    public JSONObject getJSONObject()
    {
        return this.jObj;
    }

    public IOException getError()
    {
        return this.error;
    }
}
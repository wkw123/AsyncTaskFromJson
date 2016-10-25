package com.example.administrator.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private static String Url = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new newsAsyncTask().execute(Url);
    }

    private void initView(){
        mListView = (ListView) findViewById(R.id.lv_main);
    }

    class newsAsyncTask extends AsyncTask<String,Void, List<NewsBeans>>{

        @Override
        protected List<NewsBeans> doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBeans> newsBeanses) {
            super.onPostExecute(newsBeanses);
            ListViewAdapter adpater = new ListViewAdapter(MainActivity.this,newsBeanses);
            mListView.setAdapter(adpater);
        }
    }

    private List<NewsBeans> getJsonData(String url) {
        List<NewsBeans> newsBeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            NewsBeans newsbean;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i ++){
                jsonObject = jsonArray.getJSONObject(i);
                newsbean = new NewsBeans();
                newsbean.setNewsIconUrl(jsonObject.getString("picSmall"));
                newsbean.setNewsTitle(jsonObject.getString("name"));
                newsbean.setNewsContent(jsonObject.getString("description"));
                newsBeanList.add(newsbean);
            }
            Log.i("mains", jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    private String readStream(InputStream is)  {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                result += line;

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

package com.example.murali.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity implements ResultsCallBack{

    MyFragment fragment;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ListView) findViewById(R.id.listView);


        if(savedInstanceState==null){

            fragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(fragment,"myFragment").commit();
        }else {
            getSupportFragmentManager().findFragmentByTag("myFragment");
        }

        fragment.startTask();

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(ArrayList<HashMap<String, String>> results) {

        listView.setAdapter(new MyAdapter(this,results));


    }








    public static class  MyTask extends AsyncTask<Void,Void,ArrayList<HashMap<String,String>>>{

       L l;

        ResultsCallBack callBack;
        public MyTask(ResultsCallBack callBack){

            this.callBack=callBack;

        }



        public void onAttach(ResultsCallBack callBack){

            this.callBack=callBack;

        }

        public void onDetach(){

           callBack=null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (callBack != null) {
                callBack.onPreExecute();
            }
        }

        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(Void... voids) {

            String downloadUrl = "http://feeds.feedburner.com/techcrunch/android?format=xml";
            ArrayList<HashMap<String,String>> results = new ArrayList<>();
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream=httpURLConnection.getInputStream();
               results= downLoadData(inputStream);
            } catch (Exception e) {

            }


            return results;
        }

       @Override
       protected void onPostExecute(ArrayList<HashMap<String, String>> results) {

           if(callBack!=null){
               callBack.onPostExecute(results);
           }

       }

       public ArrayList<HashMap<String,String>> downLoadData(InputStream inputStream) throws Exception{
           DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
           DocumentBuilder builder=documentBuilderFactory.newDocumentBuilder();
           Document xml=builder.parse(inputStream);
           Element rootElement=xml.getDocumentElement();
           //l.logMessage(""+ rootElement.getTagName());
           //Toast.makeText(context,""+rootElement.getTagName(),Toast.LENGTH_LONG).show();
           NodeList listItem=rootElement.getElementsByTagName("item");
           NodeList childName =null;
           Node currentItem=null;
           Node currentChild=null;
           int count=0;
           ArrayList<HashMap<String,String>> results = new ArrayList<>();
           HashMap<String,String> currentHashMap = null;
           for(int i=0;i<listItem.getLength();i++){

               currentItem=listItem.item(i);
               //l.logMessage(""+currentItem.getNodeName());
               childName=currentItem.getChildNodes();
               currentHashMap = new HashMap<>();
               for(int j=0;j<childName.getLength();j++){

                   currentChild=childName.item(j);
                   //l.logMessage(""+currentChild.getNodeName());
                   if(currentChild.getNodeName().equalsIgnoreCase("title")){

                       currentHashMap.put("title",currentChild.getTextContent());

                   }
                   if(currentChild.getNodeName().equalsIgnoreCase("pubDate")){

                       currentHashMap.put("pubDate",currentChild.getTextContent());

                   }
                   if(currentChild.getNodeName().equalsIgnoreCase("description")){

                       currentHashMap.put("description",currentChild.getTextContent());
                   }
                   if(currentChild.getNodeName().equalsIgnoreCase("media:thumbnail")){
                        count++;
                       if(count==2){
                          currentHashMap.put("imageURL", currentChild.getAttributes().item(0).getTextContent());
                       }

                   }


               }

               if(currentHashMap!=null && !currentHashMap.isEmpty()){

                   results.add(currentHashMap);
               }
               count=0;



           }

           return results;
       }

   }



}


interface ResultsCallBack{

    public void onPreExecute();
    public void onPostExecute(ArrayList<HashMap<String,String>> results);
}





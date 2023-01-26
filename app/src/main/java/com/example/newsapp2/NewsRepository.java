package com.example.newsapp2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {



    public void getAllData(ExecutorService srv, Handler uiHandler){

        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject json = new JSONObject(buffer.toString());
                JSONArray items = json.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject current = items.getJSONObject(i);

                    News news = new News(current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            current.getString("categoryName"),
                            (current.getString("date")).substring(0,10),
                            current.getString("image"));
                    data.add(news);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }





    public void getDataById(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject response = new JSONObject(buffer.toString());
                JSONArray items = response.getJSONArray("items");
                JSONObject current = items.getJSONObject(0);


                String title = current.getString("title");
                String text = current.getString("text");
                String date = (current.getString("date")).substring(0,10);
                String categoryName = current.getString("categoryName");
                String img = current.getString("image");

                News news = new News( id,  title,  text,  categoryName,  date,  img);

                Message msg = new Message();
                msg.obj = news;
                uiHandler.sendMessage(msg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
    public void getAllCategories(ExecutorService srv, Handler uiHandler) {

        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }
                Log.d("Repository", buffer.toString());
                JSONObject json = new JSONObject(buffer.toString());
                JSONArray items = json.getJSONArray("items");

                List<Categories> data = new ArrayList<>();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject current = items.getJSONObject(i);

                    Categories categories = new Categories(current.getInt("id"),
                            current.getString("name") );
                    data.add(categories);

                }
                Log.d("Repository", data.get(0).getName());
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });


    }
    public void getDataByCategoryId(ExecutorService srv, Handler uiHandler, int id) {


        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }
                Log.d("Repository", buffer.toString());

                JSONObject response = new JSONObject(buffer.toString());
                JSONArray items = response.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject current = items.getJSONObject(i);

                    News news = new News(current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            current.getString("categoryName"),
                            (current.getString("date")).substring(0,10),
                            current.getString("image"));
                    data.add(news);
                }
                Log.d("Repository", data.get(0).getTitle());

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }
    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }


}


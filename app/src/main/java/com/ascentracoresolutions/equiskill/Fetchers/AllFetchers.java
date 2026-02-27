package com.ascentracoresolutions.equiskill.Fetchers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ascentracoresolutions.equiskill.Adapters.StudentAdapter;
import com.ascentracoresolutions.equiskill.Getters.Student;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllFetchers {

    private final String TAG = "AllFetcher";

    private StudentAdapter studentAdapter;
    private ArrayList<Student> list;

    public AllFetchers(StudentAdapter studentAdapter, ArrayList<Student> list) {

        this.studentAdapter = studentAdapter;
        this.list = list;
    }

    public void fetchData(String url_link) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            String result = fetchDataFromUrl(url_link);

            new Handler(Looper.getMainLooper()).post(() -> onDataFetched(result));
        });

        executorService.shutdown();
    }
    @NonNull
    private String fetchDataFromUrl(String url_link) {

        StringBuilder result =  new StringBuilder();

        try {
            URL url = new URL(url_link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;

                while((line = br.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                httpURLConnection.disconnect();
            }
        } catch(Exception e) {

            Log.e(TAG, "error: " + e.getMessage());
        }

        return result.toString();
    }


    private void onDataFetched(String result) {

        if(result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);

                Log.e(TAG, "onDataFetched: " + jsonObject);

                JSONArray jsonArray = jsonObject.getJSONArray("students");

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject student = jsonArray.getJSONObject(i);

                    String name = student.getString("name");
                    String score = student.getString("score");

                    String student_id = student.getString("student_id");


                    list.add(new Student(name, score, student_id));
                }

                studentAdapter.notifyDataSetChanged();


            } catch (Exception e) {
                Log.e(TAG, "JSON parsing error: " + e);
            }

        } else {
            Log.e(TAG, "null fetch error");
        }

    }


}

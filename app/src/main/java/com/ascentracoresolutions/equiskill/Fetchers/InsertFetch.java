package com.ascentracoresolutions.equiskill.Fetchers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ascentracoresolutions.equiskill.Adapters.StudentAdapter;
import com.ascentracoresolutions.equiskill.Getters.Student;
import com.ascentracoresolutions.equiskill.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertFetch {

    private final String TAG = "AllFetcher", USER_ID_SIGN = "user_id_sign";

    private StudentAdapter studentAdapter;
    private ArrayList<Student> list;
    private Context context;
    private Button button;
    private TextView error;
    private Class<?> activity;
    private ConstraintLayout main;

    SharedPreferences sharedPreferences;



    public InsertFetch(StudentAdapter studentAdapter, ArrayList<Student> list) {

        this.studentAdapter = studentAdapter;
        this.list = list;
    }



    //sign in  fetcher
    public InsertFetch(Context context, TextView error, Class<?> activity, Button button, ConstraintLayout main) {
        this.context = context;
        this.error = error;
        this.activity = activity;
        this.button = button;
        this.main = main;

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


                // sign in system
                if (jsonObject.has("signin") && activity != null && button != null && main != null) {
                    sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);

                    String signIn = jsonObject.getString("signin");

                    if (jsonObject.has("user_type")){

                        sharedPreferences.edit().putString(MainActivity.USER_TYPE, jsonObject.getString("user_type")).apply();
                        sharedPreferences.edit().putString(MainActivity.USER_ID, jsonObject.getString("signin")).apply();

                        context.startActivity(new Intent(context, activity));

                    }

                    if (signIn.equals("exists")) {

                        if (error != null) {
                            error.setText("Already user exists");
                            error.setVisibility(TextView.VISIBLE);
                        }
                    } else if (signIn.equals("no")) {
                        if (error != null) {
                            error.setText("No User Found");
                            error.setVisibility(TextView.VISIBLE);
                        }
                    } else {
                        if (jsonObject.has("otp")) {

                            sharedPreferences.edit().putString("otp", jsonObject.getString("otp")).apply();
                            sharedPreferences.edit().putString(USER_ID_SIGN, jsonObject.getString("signin")).apply();

                            context.startActivity(new Intent(context, activity));

                            Log.e(TAG, "executed");
                        } else {

                            context.startActivity(new Intent(context, activity));
                        }


                    }


                    button.setEnabled(true);
                    main.setAlpha(1);


                    Log.e(TAG, "onDataFetched: sign in block");
                    return;
                }
                // sign in end system





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
